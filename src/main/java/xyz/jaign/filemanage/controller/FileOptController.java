package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.FileOptSend;
import xyz.jaign.filemanage.service.impl.FileOptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("opt")
public class FileOptController {

    @Autowired
    private FileOptServiceImpl fileOptServiceImpl;

    @PostMapping("copyto")
    public FileOptSend copy(String filepath,String folder){
       return fileOptServiceImpl.copyto(new File(filepath),new File(folder));
    }

    @PostMapping("moveto")
    public FileOptSend move(String filepath,String folder){
        return fileOptServiceImpl.moveto(new File(filepath),new File(folder));
    }

    @PostMapping("rename")
    public FileOptSend rename(String filepath,String newname){
        return fileOptServiceImpl.renameto(new File(filepath),newname);
    }

    @PostMapping("delete")
    public FileOptSend delete(String filepath){
        return fileOptServiceImpl.delete(new File(filepath));
    }

}
