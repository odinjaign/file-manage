package com.example.demo1.controller;

import com.example.demo1.dto.send.FolderPasswordDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.service.impl.FolderPasswordDBServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fodler/db/password")
public class FolderPasswordDBController {


    @Autowired
    private FolderPasswordDBServiceImpl folderPasswordDBServiceImpl;

    @PostMapping("add")
    public NormalSend addRecord(String folder,String password){
        return folderPasswordDBServiceImpl.addRecord(folder,password);
    }
    @PostMapping("list")
    public FolderPasswordDTOSend list(){
        return folderPasswordDBServiceImpl.getAll();
    }

    @PostMapping("change")
    public NormalSend changeStatus(String path,int code){
        return folderPasswordDBServiceImpl.changeStatus(path,code);
    }

    @PostMapping("modify")
    public NormalSend modifyPassword(String path,String password){
        return folderPasswordDBServiceImpl.modifyPassword(path,password);
    }
    @PostMapping("delete")
    public NormalSend delete(String path){
        return folderPasswordDBServiceImpl.delete(path);
    }
}
