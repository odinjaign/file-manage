package com.example.demo1.controller;

import com.example.demo1.dto.send.FileOptSend;
import com.example.demo1.service.impl.FileSuperOptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("opt/super")
public class FileOptSuperController {


    @Autowired
    private FileSuperOptServiceImpl fileSuperOptServiceImpl;

    @PostMapping("create")
    public FileOptSend createFile(String filename, boolean isfolder) {
        return fileSuperOptServiceImpl.createFileOrFolder(filename, isfolder);
    }

    @PostMapping("upload")
    public FileOptSend uploadFile(@RequestParam("file") MultipartFile file) {
        return fileSuperOptServiceImpl.uploadFile(file);
    }
    @GetMapping("download")
    public FileOptSend downloadFile(String filepath, HttpServletResponse response){
        return fileSuperOptServiceImpl.downloadFile(filepath,response);
    }

}
