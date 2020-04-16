package com.example.demo1.service.impl;

import cn.hutool.core.io.FileUtil;
import com.example.demo1.dto.send.FileOptSend;
import com.example.demo1.service.FileOptService;
import com.example.demo1.util.FileOptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileOptServiceImpl implements FileOptService {


    @Autowired
    private MainListServiceImpl mainListServiceImpl;


    /**
     * 复制文件和目录到另一文件夹
     * @param src
     * @param folder
     * @return
     */
    @Override
    public FileOptSend copyto(File src, File folder) {
        FileOptSend send = new FileOptSend();
        send.setMsg("");
        // 检查文件夹是否存在
        boolean exist = FileUtil.exist(folder);
        if (!exist) {
            FileUtil.mkdir(folder);
            send.setMsg("文件夹被创建！");
        }
        if (folder.isFile()){
            send.setCode(-1);
            send.setMsg("目标路径不是文件夹!");
            return send;
        }

        String rootFolder = mainListServiceImpl.getNowUserRootFolder();

        if (!FileOptUtil.fileInFolder(folder,rootFolder)){
            send.setCode(-2);
            send.setMsg("目标路径脱离当前主目录!");
            return send;
        }

        FileUtil.copy(src, folder, true);
        send.setCode(0);
        send.apendMsg("复制成功!");
        mainListServiceImpl.updateCache();
        return send;
    }

    @Override
    public FileOptSend moveto(File src, File folder){
        FileOptSend send = new FileOptSend();
        send.setMsg("");
        // 检查文件夹是否存在
        boolean exist = FileUtil.exist(folder);
        if (!exist) {
            FileUtil.mkdir(folder);
            send.setMsg("文件夹被创建！");
        }
        if (folder.isFile()){
            send.setCode(-1);
            send.setMsg("目标路径不是文件夹!");
            return send;
        }

        String rootFolder = mainListServiceImpl.getNowUserRootFolder();

        if (!FileOptUtil.fileInFolder(folder,rootFolder)){
            send.setCode(-2);
            send.setMsg("目标路径脱离当前主目录!");
            return send;
        }
        FileUtil.move(src,folder,true);
        send.setCode(0);
        send.apendMsg("移动成功!");
        mainListServiceImpl.updateCache();
        return send;
    }

    @Override
    public FileOptSend renameto(File src, String newname){
        FileOptSend send = new FileOptSend();
        FileUtil.rename(src,newname,false,true);
        send.setCode(0);
        send.setMsg("重命名成功!");
        mainListServiceImpl.updateCache();
        return send;
    }

    @Override
    public FileOptSend delete(File file) {
        FileOptSend send = new FileOptSend();
        if (!file.exists()){
            send.setCode(-1);
            send.setMsg("发生错误，文件竟然不存在，请刷新页面重新读取试试！");
            return send;
        }
        boolean del = FileUtil.del(file);
        if (!del){
            send.setCode(-2);
            send.setMsg("文件删除是发生错误！");
            return send;
        }
        send.setCode(0);
        send.setMsg("文件删除成功!");
        mainListServiceImpl.updateCache();
        return send;
    }
}
