package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.FolderPasswordDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.service.impl.FolderPasswordDBServiceImpl;
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
