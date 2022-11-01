package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.recv.LoginDTORecv;
import xyz.jaign.filemanage.dto.send.CaptchaDTOSend;
import xyz.jaign.filemanage.dto.send.LoginDTOSend;
import xyz.jaign.filemanage.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
