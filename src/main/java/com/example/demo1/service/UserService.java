package com.example.demo1.service;

import com.example.demo1.dto.send.UserDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.dto.send.UserListDTOSend;

public interface UserService {
    UserDTOSend nowUserInfo();

    NormalSend update(String key, String value);

    NormalSend updatePassword(String oldpassword, String newpassword);

    NormalSend resetPassword(String username, String password);

    UserListDTOSend getAllRegistUsers();

    NormalSend deleteUser(String username);
}
