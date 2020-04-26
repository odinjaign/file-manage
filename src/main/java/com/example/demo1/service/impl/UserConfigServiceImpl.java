package com.example.demo1.service.impl;

import com.example.demo1.dao.UserConfigMapper;
import com.example.demo1.entity.User;
import com.example.demo1.entity.UserConfig;
import com.example.demo1.enums.FolderPasswordType;
import com.example.demo1.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConfigServiceImpl implements UserConfigService {


    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private UserConfigMapper userConfigMapper;

    @Override
    public UserConfig getUserConfig() {
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null) {
            return null;//用户未登录
        }
        return userConfigMapper.selectUserConfigByUserid(nowUser.getId());
    }

    @Override
    public FolderPasswordType folderPasswordStatus() {
        UserConfig config = getUserConfig();
        if (config == null) {
            return null;
        } else {
            switch (config.getPasswordtype()){
                case 0:
                    return FolderPasswordType.关闭;
                case 1:
                    return FolderPasswordType.文本密码;
                case 2:
                    return FolderPasswordType.数据库密码;
                default:
                    return null;
            }
        }
    }
}
