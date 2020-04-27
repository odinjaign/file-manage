package com.example.demo1.service.impl;

import com.example.demo1.dao.UserConfigMapper;
import com.example.demo1.dto.send.NormalSend;
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

    @Override
    public NormalSend isLogin() {
        NormalSend normalSend = new NormalSend();
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null){
            normalSend.setCode(-1);
            normalSend.setMsg("用户未登录");
        }else {
            normalSend.setCode(0);
            normalSend.setMsg("用户["+nowUser.getUsername()+"]已登录");
        }
        return normalSend;
    }

    @Override
    public NormalSend passwordChangeStatus(int status) {
        NormalSend normalSend = new NormalSend();
        UserConfig userConfig = getUserConfig();
        userConfig.setPasswordtype(status);
        userConfigMapper.updateUserConfig(userConfig);
        normalSend.setCode(0);
        normalSend.setMsg("修改成功！");
        return normalSend;
    }
}
