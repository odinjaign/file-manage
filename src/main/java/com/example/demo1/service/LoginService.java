package com.example.demo1.service;

import com.example.demo1.dto.recv.LoginDTORecv;
import com.example.demo1.dto.send.CaptchaDTOSend;
import com.example.demo1.dto.send.LoginDTOSend;
import com.example.demo1.entity.User;

public interface LoginService {
    LoginDTOSend userLogin(LoginDTORecv recv);
    CaptchaDTOSend captchaGenerate();
    byte[] captchaImg(long timeMillis);

    boolean logout();

    User getNowUser();
}
