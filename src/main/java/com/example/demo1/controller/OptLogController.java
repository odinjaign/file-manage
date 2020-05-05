package com.example.demo1.controller;

import com.example.demo1.dto.send.OptLogsDTOSend;
import com.example.demo1.service.impl.OptLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("opt/log")
public class OptLogController {


    @Autowired
    private OptLogServiceImpl optLogServiceImpl;

    @PostMapping("list")
    public OptLogsDTOSend list(){
        return optLogServiceImpl.getNowUserLogs();
    }
}
