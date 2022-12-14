package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.service.impl.UserConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/config")
public class UserConfigController {


    @Autowired
    private UserConfigServiceImpl userConfigServiceImpl;

    @PostMapping("islogin")
    public NormalSend isLogin(){
        return userConfigServiceImpl.isLogin();
    }

    @PostMapping("folder/password/status")
    public int passwordStatus(){
        return userConfigServiceImpl.getUserConfig().getPasswordtype();
    }

    @PostMapping("folder/password/changestatus")
    public NormalSend passwordChangeStatus(int status){
        return userConfigServiceImpl.passwordChangeStatus(status);
    }
}
