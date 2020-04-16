package com.example.demo1.controller;

import com.example.demo1.dto.recv.LoginDTORecv;
import com.example.demo1.dto.send.CaptchaDTOSend;
import com.example.demo1.dto.send.LoginDTOSend;
import com.example.demo1.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping
public class LoginController {



    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @PostMapping("login")
    public LoginDTOSend login(LoginDTORecv dtoRecv) {
            //用户登录
        return loginServiceImpl.userLogin(dtoRecv);
    }

    @PostMapping("logout")
    public boolean logout() {
        //用户登录
        return loginServiceImpl.logout();
    }

    @GetMapping("captcha/generate")
    public CaptchaDTOSend captchaGenerate() {
        return loginServiceImpl.captchaGenerate();
    }

    @GetMapping(value = "captcha/img", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] captchaImg(Long timeMillis) {
        return loginServiceImpl.captchaImg(timeMillis);
    }


}
