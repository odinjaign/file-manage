package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.UserDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.dto.send.UserListDTOSend;

public interface UserService {
    UserDTOSend nowUserInfo();

    NormalSend update(String key, String value);

    NormalSend updatePassword(String oldpassword, String newpassword);

    NormalSend resetPassword(String username, String password);

    UserListDTOSend getAllRegistUsers();

    NormalSend deleteUser(String username);
}
