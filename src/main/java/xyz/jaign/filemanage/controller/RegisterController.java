package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.recv.RegisterDTORecv;
import xyz.jaign.filemanage.dto.send.RegisterDTOSend;
import xyz.jaign.filemanage.entity.User;
import xyz.jaign.filemanage.enums.Usertype;
import xyz.jaign.filemanage.service.impl.RegisterServiceImpl;
import xyz.jaign.filemanage.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RegisterController {


    @Autowired
    private RegisterServiceImpl registerServiceImpl;

    @PostMapping("register")
    public RegisterDTOSend register(RegisterDTORecv dto){
        RegisterDTOSend send = new RegisterDTOSend();

        //判断用户是否注册
        if (registerServiceImpl.userIsRegistered(dto.getUsername())){
            send.setCode(-1);
            send.setMsg("用户已经被注册了");
            return send;
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setGender(dto.getGender());
        user.setNickname(dto.getNickname());
        user.setPasswords(PasswordUtils.passwords(dto.getPassword()));//混淆密码
        user.setUsertype(Usertype.注册用户.value());
        user.setUsername(dto.getUsername());
        //类型转换

        //用户注册
        registerServiceImpl.registerUser(user);
        send.setCode(0);
        send.setMsg("注册成功");
        return send;
    }
}
