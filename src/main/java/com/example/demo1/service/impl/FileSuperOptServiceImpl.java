package com.example.demo1.service.impl;

import com.example.demo1.dto.send.FileOptSend;
import com.example.demo1.service.FileSuperOptService;
import com.example.demo1.util.CacheUtil;
import com.example.demo1.util.FileOptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件高级操作：
 * 上传文件、
 * 下载文件、
 * 新建文件
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
        File  file = new File(filepath);
        if (!file.exists()) {
            send.setCode(-1);
            send.setMsg("文件不存在");
            return send;
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName="+java.net.URLEncoder.encode(file.getName(), "UTF-8"));
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
}
