package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.recv.LoginDTORecv;
import xyz.jaign.filemanage.dto.send.CaptchaDTOSend;
import xyz.jaign.filemanage.dto.send.LoginDTOSend;
import xyz.jaign.filemanage.entity.User;

public interface LoginService {
    LoginDTOSend userLogin(LoginDTORecv recv);
    CaptchaDTOSend captchaGenerate();
    byte[] captchaImg(long timeMillis);

    boolean logout();

    User getNowUser();
}
