package com.example.demo1.dao;

import com.example.demo1.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    void insertUser(User user);
    User selectByUsername(String username);
    void updateById(User user);
    List<User> selectByType(int type);
    void deleteByUsername(String username);
}
