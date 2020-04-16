package com.example.demo1.dao;

import com.example.demo1.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    void insertUser(User user);
    User selectByUsername(String username);
}
