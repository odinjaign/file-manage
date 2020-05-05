package com.example.demo1.service.impl;

import cn.hutool.core.io.FileUtil;
import com.example.demo1.dto.send.MainListDTOSend;
import com.example.demo1.dto.send.SysConfigDTOSend;
import com.example.demo1.enums.FolderPasswordType;
import com.example.demo1.service.FileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileListServiceImpl implements FileListService {


    @Autowired
    private UserConfigServiceImpl userConfigServiceImpl;
    @Autowired
    private HideManageServiceImpl hideManageServiceImpl;
    @Autowired
    private SysConfigServiceImpl sysConfigServiceImpl;


    @Override
    public List<MainListDTOSend.FileNode> getFileList(String folder) {
        SysConfigDTOSend.SysConfigData configData = sysConfigServiceImpl.getAllConfigByList().getData();
        //取得文件列表
        File[] files = FileUtil.ls(folder);
        ArrayList<MainListDTOSend.FileNode> fileNodes = new ArrayList<>();

        FolderPasswordType folderPasswordType = userConfigServiceImpl.folderPasswordStatus();
        boolean passwordFileFlag = false;
        if (folderPasswordType == FolderPasswordType.文本密码) {
            //隐藏password.txt
            passwordFileFlag = true;
        }


        for (File file : files) {
            //todo:过滤文件

            //1. 过去password.txt
            if (passwordFileFlag && file.getName().equals("password.txt")) {
                continue;
            }

            //2. 过滤用户隐藏文件
            if (configData.isFilehide()){
                if (hideManageServiceImpl.isHide(file)) {
                    continue;
                }
            }

            //2. 过滤系统隐藏文件
            if (configData.isSyshide()){
                if(file.isHidden()){
                    continue;
                }
            }
            //
            MainListDTOSend.FileNode fileNode = new MainListDTOSend.FileNode();
            fileNode.setUpdatetime(new Date(file.lastModified()));
            fileNode.setType(file.isFile() ? "文件" : "文件夹");
            fileNode.setSize(file.isFile() ? file.length() / 1024 : 0);
            fileNode.setFolder(!file.isFile());
            fileNode.setFavorite(false);
            fileNode.setPath(file.getPath());
            fileNode.setName(file.getName());
            fileNodes.add(fileNode);
        }
        return fileNodes;
    }
}
