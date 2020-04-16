package com.example.demo1.service.impl;

import com.example.demo1.dao.UserConfigMapper;
import com.example.demo1.dao.UserMapper;
import com.example.demo1.entity.User;
import com.example.demo1.entity.UserConfig;
import com.example.demo1.enums.Passwordtype;
import com.example.demo1.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Value("${jaign.config.free-space-root}")
    private String freeSpaceRoot;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserConfigMapper userConfigMapper;

    @Override
    public void registerUser(User user) {
        //用户注册
        userMapper.insertUser(user);

        //获取user
        User savedUser = userMapper.selectByUsername(user.getUsername());
        int userid = savedUser.getId();
        String username = savedUser.getUsername();
        String mainfolder = freeSpaceRoot + username;
        int passwordtype = Passwordtype.关闭.value(); //关闭
        UserConfig userConfig = new UserConfig();
        userConfig.setUserid(userid);
        userConfig.setMainfolder(mainfolder);
        userConfig.setUsername(username);
        userConfig.setPasswordtype(passwordtype);
        //初始化配置
        userConfigMapper.insertUserConfig(userConfig);

    }

    /**
     * 判断用户是否已经注册
     *
     * @param username 用户名
     * @return 是否已经注册
     */
    @Override
    public boolean userIsRegistered(String username) {
        User user = userMapper.selectByUsername(username);
        return user != null;
    }
}
