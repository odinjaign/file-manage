package com.example.demo1.controller;

import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.dto.send.UserDTOSend;
import com.example.demo1.dto.send.UserListDTOSend;
import com.example.demo1.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("now")
    public UserDTOSend now() {
        return userServiceImpl.nowUserInfo();
    }

    @PostMapping("update")
    public NormalSend update(String key, String value) {
        return userServiceImpl.update(key,value);
    }

    @PostMapping("updatepasswd")
    public NormalSend updatePassword(String oldpassword, String newpassword) {
        return userServiceImpl.updatePassword(oldpassword,newpassword);
    }

    @PostMapping("resetpasswd")
    public NormalSend resetPassword(String username,String password){
        return userServiceImpl.resetPassword(username,password);
    }

    @PostMapping("list")
    public UserListDTOSend list(){return userServiceImpl.getAllRegistUsers();}

    @PostMapping("del")
    public NormalSend delete(String username){
        return userServiceImpl.deleteUser(username);
    }
}
