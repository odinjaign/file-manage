package com.example.demo1.service.impl;

import com.example.demo1.dao.ClassListMapper;
import com.example.demo1.dto.send.MovieDTOSend;
import com.example.demo1.entity.ClassList;
import com.example.demo1.enums.ClassType;
import com.example.demo1.service.MoviesService;
import com.example.demo1.util.MovieOptUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoviesServiceImpl implements MoviesService {

    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private ClassManageServiceImpl classManageServiceImpl;
    @Autowired
    private ClassListMapper classListMapper;



    @Override
    public MovieDTOSend getAllMovie() {
        MovieDTOSend send = new MovieDTOSend();
        //得到当前用户id
        int userid = loginServiceImpl.getNowUser().getId();
        List<ClassList> movieClassList = classListMapper.selectByTypeAndUser(ClassType.视频.value(), userid);
        List<MovieDTOSend.MovieNode> nodes = new ArrayList<>();
        for (ClassList classList : movieClassList) {
            List<File> movieFiles = classManageServiceImpl.getFile(classList);
            //
            for (File movieFile : movieFiles) {
//                MusicDTOSend.MusicNode musicNode = new MusicDTOSend.MusicNode();
//                //音乐节点生成
//                musicNode.setExt(StringOptUtil.fileExt(musicFile.getName()));
//                musicNode.setId(nodes.size());
//                musicNode.setPath(musicFile.getPath());
//                musicNode.setName(musicFile.getName());
//
//                // TODO: 2020/4/19  需要修改
//                musicNode.setLength(0);
//                musicNode.setSheet("未知");
//                musicNode.setSinger("未知");
//                musicNode.setTitle("未知");
//                //
                MovieDTOSend.MovieNode movieNode = MovieOptUtil.toNode(movieFile);
                movieNode.setId(nodes.size());
                nodes.add(movieNode);
            }
        }
        send.setCode(0);
        send.setMsg("视频列表获取成功");
        send.setCount(nodes.size());
        send.setData(nodes);
        return send;
    }

    //https://blog.csdn.net/qq_34455577/java/article/details/99414271
    @Override
    public void playMovie(HttpServletRequest request, HttpServletResponse response, String path)  {
        BufferedInputStream bis = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                long p = 0L;
                long toLength = 0L;
                long contentLength = 0L;
                int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
                long fileLength;
                String rangBytes = "";
                fileLength = file.length();

                // get file content
                InputStream ins = new FileInputStream(file);
                bis = new BufferedInputStream(ins);

                // tell the client to allow accept-ranges
                response.reset();
                response.setHeader("Accept-Ranges", "bytes");

                // client requests a file block download start byte
                String range = request.getHeader("Range");
                if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                    response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
                    rangBytes = range.replaceAll("bytes=", "");
                    if (rangBytes.endsWith("-")) { // bytes=270000-
                        rangeSwitch = 1;
                        p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                        contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
                    } else { // bytes=270000-320000
                        rangeSwitch = 2;
                        String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
                        String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
                        p = Long.parseLong(temp1);
                        toLength = Long.parseLong(temp2);
                        contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
                    }
                } else {
                    contentLength = fileLength;
                }

                // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
                // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
                response.setHeader("Content-Length", new Long(contentLength).toString());

                // 断点开始
                // 响应的格式是:
                // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
                if (rangeSwitch == 1) {
                    String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
                            .append(new Long(fileLength - 1).toString()).append("/")
                            .append(new Long(fileLength).toString()).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else if (rangeSwitch == 2) {
                    String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else {
                    String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")
                            .append(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                }

                //防止访问URL文件被直接下载
//                String fileName = file.getName();
//                response.setContentType("application/octet-stream");
//                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

                response.addHeader("Content-Type","video/mp4;charset=UTF-8");

                OutputStream out = response.getOutputStream();
                int n = 0;
                long readLength = 0;
                int bsize = 1024;
                byte[] bytes = new byte[bsize];
                if (rangeSwitch == 2) {
                    // 针对 bytes=27000-39000 的请求，从27000开始写数据
                    while (readLength <= contentLength - bsize) {
                        n = bis.read(bytes);
                        readLength += n;
                        out.write(bytes, 0, n);
                    }
                    if (readLength <= contentLength) {
                        n = bis.read(bytes, 0, (int) (contentLength - readLength));
                        out.write(bytes, 0, n);
                    }
                } else {
                    while ((n = bis.read(bytes)) != -1) {
                        out.write(bytes, 0, n);
                    }
                }
                out.flush();
                out.close();
                bis.close();
            }
        } catch (IOException ie) {
            // 忽略 ClientAbortException 之类的异常
        } catch (Exception e) {
            e.printStackTrace();
        }


//        File file = new File(path);
//        try {
//            InputStream inputStream = new FileInputStream(file);
//            response.addHeader("Content-Type","video/mp4;charset=UTF-8");
//            IOUtils.copy(inputStream,response.getOutputStream());
//            response.flushBuffer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
