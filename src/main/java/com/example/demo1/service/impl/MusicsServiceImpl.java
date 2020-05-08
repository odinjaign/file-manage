package com.example.demo1.service.impl;

import com.example.demo1.dao.ClassListMapper;
import com.example.demo1.dto.send.MusicDTOSend;
import com.example.demo1.entity.ClassList;
import com.example.demo1.enums.ClassType;
import com.example.demo1.service.MusicsService;
import com.example.demo1.util.CacheUtil;
import com.example.demo1.util.MusicOptUtil;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicsServiceImpl implements MusicsService {

    @Autowired
    private ClassListMapper classListMapper;
    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private ClassManageServiceImpl classManageServiceImpl;
    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public MusicDTOSend getAllMusic() {
        MusicDTOSend send = new MusicDTOSend();


        if (cacheUtil.hasKey("music_list")){
            List<MusicDTOSend.MusicNode> list = cacheUtil.getList("music_list", MusicDTOSend.MusicNode.class);
            send.setCode(0);
            send.setMsg("音乐列表获取成功[Cache]");
            send.setCount(list.size());
            send.setData(list);
            return send;
        }

        //得到当前用户id
        int userid = loginServiceImpl.getNowUser().getId();
        List<ClassList> musicClassList = classListMapper.selectByTypeAndUser(ClassType.音乐.value(), userid);
        ArrayList<MusicDTOSend.MusicNode> nodes = new ArrayList<>();
        for (ClassList classList : musicClassList) {
            List<File> musicFiles = classManageServiceImpl.getFile(classList);
            //
            for (File musicFile : musicFiles) {
                MusicDTOSend.MusicNode musicNode = MusicOptUtil.toNode(musicFile);
                musicNode.setId(nodes.size());
                nodes.add(musicNode);
            }
        }
        send.setCode(0);
        send.setMsg("音乐列表获取成功");
        send.setCount(nodes.size());
        send.setData(nodes);
        cacheUtil.setList("music_list",nodes);
        return send;
    }

    @Override
    public void playMusic(HttpServletRequest request, HttpServletResponse response, String path) {
            File music = new File(path);
            if (!music.exists()){
                System.out.println("文件不存在");
            }
            String range=request.getHeader("Range");
            //开始下载位置
            long startByte = 0;
            //结束下载位置
            long endByte = music.length() - 1;
            //有range的话
            if (range != null && range.contains("bytes=") && range.contains("-")) {
                range = range.substring(range.lastIndexOf("=") + 1).trim();
                String ranges[] = range.split("-");
                try {
                    //判断range的类型
                    if (ranges.length == 1) {
                        //类型一：bytes=-2343
                        if (range.startsWith("-")) {
                            endByte = Long.parseLong(ranges[0]);
                        }
                        //类型二：bytes=2343-
                        else if (range.endsWith("-")) {
                            startByte = Long.parseLong(ranges[0]);
                        }
                    }
                    //类型三：bytes=22-2343
                    else if (ranges.length == 2) {
                        startByte = Long.parseLong(ranges[0]);
                        endByte = Long.parseLong(ranges[1]);
                    }
                } catch (NumberFormatException e) {
                    startByte = 0;
                    endByte = music.length() - 1;
                }
            }
            //要下载的长度
            long contentLength = endByte - startByte + 1;
            //文件名
            String fileName = music.getName();
            //文件类型
            String contentType = request.getServletContext().getMimeType(fileName);
            //各种响应头设置
            //参考资料：https://www.ibm.com/developerworks/cn/java/joy-down/index.html
            //坑爹地方一：看代码
            response.setHeader("Accept-Ranges", "bytes");
            //坑爹地方二：http状态码要为206
            response.setStatus(206);
            response.setContentType(contentType);
            response.setHeader("Content-Type", contentType);
            //这里文件名换你想要的，inline表示浏览器直接实用（我方便测试用的）
            //参考资料：http://hw1287789687.iteye.com/blog/2188500
            // response.setHeader("Content-Disposition", "inline;filename=test.mp3");
            response.setHeader("Content-Length", String.valueOf(contentLength));
            //坑爹地方三：Content-Range，格式为
            // [要下载的开始位置]-[结束位置]/[文件总大小]
            response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + music.length());
            BufferedOutputStream outputStream = null;
            RandomAccessFile randomAccessFile = null;
            //已传送数据大小
            long transmitted = 0;
            try {
                randomAccessFile = new RandomAccessFile(music, "r");
                outputStream = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[4096];
                int len = 0;
                randomAccessFile.seek(startByte);
                //坑爹地方四：判断是否到了最后不足4096（buff的length）个byte这个逻辑（(transmitted + len) <= contentLength）要放前面！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
                //不然会会先读取randomAccessFile，造成后面读取位置出错，找了一天才发现问题所在
                while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                    outputStream.write(buff, 0, len);
                    transmitted += len;
                    //停一下，方便测试，用的时候删了就行了
                    Thread.sleep(10);
                }
                //处理不足buff.length部分
                if (transmitted < contentLength) {
                    len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                    outputStream.write(buff, 0, len);
                    transmitted += len;
                }
                outputStream.flush();
                response.flushBuffer();
                randomAccessFile.close();
                System.out.println("下载完毕：" + startByte + "-" + endByte + "：" + transmitted);
            } catch (ClientAbortException e) {
                System.out.println("用户停止下载：" + startByte + "-" + endByte + "：" + transmitted);
                //捕获此异常表示拥护停止下载
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
}
