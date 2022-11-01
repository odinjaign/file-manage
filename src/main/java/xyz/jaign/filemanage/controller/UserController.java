package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.dto.send.UserDTOSend;
import xyz.jaign.filemanage.dto.send.UserListDTOSend;
import xyz.jaign.filemanage.service.impl.UserServiceImpl;
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
