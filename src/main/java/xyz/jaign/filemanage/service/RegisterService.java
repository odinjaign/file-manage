package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.entity.User;

public interface RegisterService {
    void registerUser(User user);
    boolean userIsRegistered(String username);
}
