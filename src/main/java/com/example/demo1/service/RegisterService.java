package com.example.demo1.service;

import com.example.demo1.entity.User;

public interface RegisterService {
    void registerUser(User user);
    boolean userIsRegistered(String username);
}
