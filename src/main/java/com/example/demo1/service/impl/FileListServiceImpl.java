package com.example.demo1.service.impl;

import cn.hutool.core.io.FileUtil;
import com.example.demo1.dto.send.MainListDTOSend;
import com.example.demo1.service.FileListService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileListServiceImpl implements FileListService {
    @Override
    public List<MainListDTOSend.FileNode> getFileList(String folder) {
        //取得文件列表
        File[] files = FileUtil.ls(folder);
        ArrayList<MainListDTOSend.FileNode> fileNodes = new ArrayList<>();
        for (File file : files) {
            //todo:过滤文件
            MainListDTOSend.FileNode fileNode = new MainListDTOSend.FileNode();
            fileNode.setUpdatetime(new Date(file.lastModified()));
            fileNode.setType(file.isFile()?"文件":"文件夹");
            fileNode.setSize(file.isFile()?file.length()/1024:0);
            fileNode.setFolder(!file.isFile());
            fileNode.setFavorite(false);
            fileNode.setPath(file.getPath());
            fileNode.setName(file.getName());
            fileNodes.add(fileNode);
        }
        return fileNodes;
    }
}
