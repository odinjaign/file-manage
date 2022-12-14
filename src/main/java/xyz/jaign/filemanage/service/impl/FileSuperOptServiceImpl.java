package xyz.jaign.filemanage.service.impl;

import cn.hutool.core.util.ZipUtil;
import xyz.jaign.filemanage.dto.send.FileOptSend;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.service.FileSuperOptService;
import xyz.jaign.filemanage.util.CacheUtil;
import xyz.jaign.filemanage.util.FileOptUtil;
import xyz.jaign.filemanage.util.StringOptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件高级操作：
 * 上传文件、
 * 下载文件、
 * 新建文件、
 * 压缩解压
 */
@Service
public class FileSuperOptServiceImpl implements FileSuperOptService {


    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private MainListServiceImpl mainListServiceImpl;


    @Override
    public FileOptSend createFileOrFolder(String filename, boolean isFolder) {
        FileOptSend send = new FileOptSend();
        String pwd = cacheUtil.get("pwd");
        File file = new File(pwd, filename);
        if (file.exists()) {
            send.setCode(-1);
            send.setMsg("文件已存在");

            return send;
        }

        if (isFolder) {
            //创建文件夹
            if (FileOptUtil.createFolder(file)) {
                send.setCode(0);
                send.setMsg("文件夹创建成功！");
                mainListServiceImpl.updateCache();
                return send;
            } else {
                send.setCode(-2);
                send.setMsg("文件夹创建失败！");
                return send;
            }

        } else {
            //创建文件
            //创建文件夹
            if (FileOptUtil.createFile(file)) {
                send.setCode(0);
                send.setMsg("文件创建成功！");
                mainListServiceImpl.updateCache();
                return send;
            } else {
                send.setCode(-3);
                send.setMsg("文件创建失败！");
                return send;
            }
        }
    }

    @Override
    public FileOptSend uploadFile(MultipartFile file) {
        FileOptSend send = new FileOptSend();
        String pwd = cacheUtil.get("pwd");
        String filename = file.getOriginalFilename();
        if (filename == null) {
            send.setCode(-1);
            send.setMsg("未获取到文件名");
            return send;
        }
        File saveFile = new File(pwd, filename);
        try {
            file.transferTo(saveFile);
            send.setCode(0);
            send.setMsg("文件上传成功，路径为：" + saveFile.getPath() + "。");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainListServiceImpl.updateCache();
        return send;
    }

    @Override
    public FileOptSend downloadFile(String filepath, HttpServletResponse response) {
        FileOptSend send = new FileOptSend();
        File file = new File(filepath);
        if (!file.exists()) {
            send.setCode(-1);
            send.setMsg("文件不存在");
            return send;
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
            byte[] buffer = new byte[1024];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream outputStream = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                outputStream.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        send.setCode(0);
        send.setMsg("文件下载成功");
        return send;
    }

    @Override
    public NormalSend unzip(String file, String folder) {
        NormalSend send = new NormalSend();
        File optFile = new File(file);
        if (!"zip".equals(StringOptUtil.fileExt(file).toLowerCase())) {
            send.setCode(-1);
            send.setMsg("解压文件类型错误");
            return send;
        }
        if (!optFile.exists()) {
            send.setCode(-2);
            send.setMsg("文件不存在");
            return send;
        }
        File unzipPath;
        if (folder == null) {
            unzipPath = ZipUtil.unzip(optFile);
        } else {
            unzipPath = ZipUtil.unzip(optFile, new File(folder));
        }
        send.setCode(0);
        send.setMsg("文件已经解压到" + unzipPath.getPath());
        mainListServiceImpl.updateCache();
        return send;
    }

    @Override
    public NormalSend zip(String folder, String dst) {
        NormalSend send = new NormalSend();
        File optFolder = new File(folder);
        if (!optFolder.exists()) {
            send.setCode(-1);
            send.setMsg("需要压缩的目录路径错误");
            return send;
        }

        if (optFolder.isFile()){
            send.setCode(-2);
            send.setMsg("目前只能压缩目录");
            return send;
        }
        File outZipFile;
        if (dst == null){
            outZipFile = ZipUtil.zip(optFolder.getPath());
        }else {
            outZipFile = ZipUtil.zip(optFolder.getPath(),dst,true);
        }
        send.setCode(0);
        send.setMsg("目录压缩为" + outZipFile.getPath());
        mainListServiceImpl.updateCache();
        return send;
    }
}
