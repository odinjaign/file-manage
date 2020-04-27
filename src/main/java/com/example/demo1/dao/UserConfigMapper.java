package com.example.demo1.dao;

import com.example.demo1.entity.UserConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfigMapper {
    void insertUserConfig(UserConfig userConfig);
    UserConfig selectUserConfigByUserid(int userid);
    void updateUserConfig(UserConfig userConfig);
}
