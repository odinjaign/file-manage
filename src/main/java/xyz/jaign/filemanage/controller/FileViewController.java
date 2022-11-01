package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.StringDTOSend;
import xyz.jaign.filemanage.service.impl.FileViewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file/view")
public class FileViewController {


    @Autowired
    private FileViewServiceImpl fileViewServiceImpl;

    @PostMapping("text/code")
    public StringDTOSend viewCode(String path){
        return fileViewServiceImpl.viewText(path);
    }

    @PostMapping("office/word")
    public StringDTOSend viewWord(String path){
        return fileViewServiceImpl.viewWord(path);
    }

    @PostMapping("office/excel")
    public StringDTOSend viewExcel(String path){
        return fileViewServiceImpl.viewExcel(path);
    }

    @PostMapping("office/ppt")
    public StringDTOSend viewPpt(String path){
        return fileViewServiceImpl.viewPpt(path);
    }

    @PostMapping("text/md")
    public StringDTOSend viewMd(String path){
        return fileViewServiceImpl.viewMd(path);
    }
}
