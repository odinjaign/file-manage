package com.example.demo1.service.impl;

import com.example.demo1.dao.UserMapper;
import com.example.demo1.dto.send.UserDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.dto.send.UserListDTOSend;
import com.example.demo1.entity.User;
import com.example.demo1.service.UserService;
import com.example.demo1.util.CacheUtil;
import com.example.demo1.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CacheUtil cacheUtil;

    private void updateNowUser(String username) {
        cacheUtil.delete("user");
        User user = userMapper.selectByUsername(username);
        cacheUtil.saveCacheO("user", user);
    }

    @Override
    public UserDTOSend nowUserInfo() {
        UserDTOSend send = new UserDTOSend();
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null) {
            send.setCode(-1);
            send.setMsg("用户未登录");
            return send;
        }
        UserDTOSend.UserInfo userInfo = new UserDTOSend.UserInfo();
        userInfo.setId(nowUser.getId());
        userInfo.setEmail(nowUser.getEmail());
        if (nowUser.getGender() == 1) {
            userInfo.setGender("男");
        } else if (nowUser.getGender() == 2) {
            userInfo.setGender("女");
        }
        userInfo.setNickname(nowUser.getNickname());
        userInfo.setUsername(nowUser.getUsername());
        switch (nowUser.getUsertype()) {
            case 1:
                userInfo.setUsertype("注册用户");
                break;
            case 2:
                userInfo.setUsertype("内置用户");
                break;
            case 3:
                userInfo.setUsertype("管理用户");
                break;
        }
        send.setCode(0);
        send.setMsg("获取成功！");
        send.setInfo(userInfo);
        return send;
    }

    @Override
    public NormalSend update(String key, String value) {
        NormalSend send = new NormalSend();
        if ("nickname".equals(key)) {
            //昵称
            User nowUser = loginServiceImpl.getNowUser();
            nowUser.setNickname(value);
            userMapper.updateById(nowUser);
            // 刷新登录缓存
            updateNowUser(nowUser.getUsername());
            send.setCode(0);
            send.setMsg("修改昵称成功！");
            return send;
        }

        if ("gender".equals(key)) {
            //性别
            User nowUser = loginServiceImpl.getNowUser();
            if ("1".equals(value) || "男".equals(value)) {
                nowUser.setGender(1);
            } else if ("2".equals(value) || "女".equals(value)) {
                nowUser.setGender(2);
            } else {
                send.setCode(-1);
                send.setMsg("性别格式不规范！");
                return send;
            }
            userMapper.updateById(nowUser);
            // 刷新登录缓存
            updateNowUser(nowUser.getUsername());
            send.setCode(0);
            send.setMsg("修改性别成功！");
            return send;
        }

        if ("email".equals(key)) {
            //邮箱
            User nowUser = loginServiceImpl.getNowUser();
            nowUser.setEmail(value);
            userMapper.updateById(nowUser);
            // 刷新登录缓存
            updateNowUser(nowUser.getUsername());
            send.setCode(0);
            send.setMsg("修改昵称成功！");
            return send;
        }
        send.setCode(-2);
        send.setMsg("其他错误！");
        return send;
    }

    @Override
    public NormalSend updatePassword(String oldpassword, String newpassword) {
        NormalSend send = new NormalSend();

        User nowUser = loginServiceImpl.getNowUser();
        if (!PasswordUtils.passwords(oldpassword).equals(nowUser.getPasswords())) {
            send.setCode(-1);
            send.setMsg("原密码错误");
            return send;
        }
        nowUser.setPasswords(PasswordUtils.passwords(newpassword));
        userMapper.updateById(nowUser);
        // 刷新登录缓存
        //退出登录
        loginServiceImpl.logout();
        send.setCode(0);
        send.setMsg("密码修改成功");
        return send;
    }

    @Override
    public NormalSend resetPassword(String username, String password) {

        NormalSend send = new NormalSend();
        if(loginServiceImpl.getNowUser()==null|| !"admin".equals(loginServiceImpl.getNowUser().getUsername())){
            send.setCode(-1);
            send.setMsg("未登录管理用户，无法重置用户密码");
            return send;
        }
        User user = userMapper.selectByUsername(username);
        user.setPasswords(PasswordUtils.passwords(password));
        userMapper.updateById(user);
        //密码重置成功
        send.setCode(0);
        send.setMsg("密码重置成功！");
        return send;
    }

    @Override
    public UserListDTOSend getAllRegistUsers() {
        User nowUser = loginServiceImpl.getNowUser();
        UserListDTOSend send = new UserListDTOSend();
        ArrayList<UserListDTOSend.UserInfo> userInfos = new ArrayList<>();
        List<User> users = userMapper.selectByType(1);
        for (User user : users) {
            UserListDTOSend.UserInfo info = new UserListDTOSend.UserInfo();
            info.setEmail(user.getEmail());
            info.setGender(user.getGender());
            info.setNickname(user.getNickname());
            info.setUsername(user.getUsername());
            info.setNowuser(nowUser != null && nowUser.getUsername().equals(info.getUsername()));
            userInfos.add(info);
        }
        send.setCode(0);
        send.setMsg("获取用户列表成功");
        send.setCount(userInfos.size());
        send.setData(userInfos);
        return send;
    }

    @Override
    public NormalSend deleteUser(String username) {
        //判断当前登录用户
        NormalSend send = new NormalSend();

        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser!=null && "admin".equals(nowUser.getUsername())){
            //删除操作
            userMapper.deleteByUsername(username);
            send.setCode(0);
            send.setMsg("未登录管理用户，无法删除用户");
            //用户已删除
            return send;
        }
        send.setCode(-1);
        send.setMsg("未登录管理用户，无法删除用户");
        return send;
    }
}
