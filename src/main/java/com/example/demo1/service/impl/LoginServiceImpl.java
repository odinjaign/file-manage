package com.example.demo1.service.impl;

import com.example.demo1.dao.UserConfigMapper;
import com.example.demo1.dao.UserMapper;
import com.example.demo1.dto.recv.LoginDTORecv;
import com.example.demo1.dto.send.CaptchaDTOSend;
import com.example.demo1.dto.send.LoginDTOSend;
import com.example.demo1.entity.User;
import com.example.demo1.entity.UserConfig;
import com.example.demo1.enums.Usertype;
import com.example.demo1.service.LoginService;
import com.example.demo1.util.CacheUtil;
import com.example.demo1.util.CaptchaUtil;
import com.example.demo1.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Value("${jaign.config.free-space-root}")
    private String freeSpaceRoot;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserConfigMapper userConfigMapper;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private ClassManageServiceImpl classManageServiceImpl;


    @Override
    public LoginDTOSend userLogin(LoginDTORecv recv) {
        LoginDTOSend send = new LoginDTOSend();//由于要发送信息，提升到这里

        //用户已经登录
        if (cacheUtil.hasKey("user")) {
            User user = cacheUtil.getCacheO("user", User.class);
            String username = user.getUsername();
            send.setCode(-5);
            send.setMsg("用户" + username + "已登录");
            send.setUsername(username);
            return send;
        }

        //获取用户名和密码
        String username = recv.getUsername();
        String password = recv.getPassword();
        //校验验证码
        String captcha = recv.getCaptcha();
        String code = cacheUtil.get("code");
        if (!captcha.equals(code)) {
            send.setCode(-1);
            send.setMsg("验证码错误");
            return send;
        }
        User user = userMapper.selectByUsername(username);

        if (user == null) {
            send.setCode(-2);
            send.setMsg("用户不存在");
            return send;
        }

        String passwords = user.getPasswords();
        if (!passwords.equals(PasswordUtils.passwords(password))) {
            send.setCode(-3);
            send.setMsg("密码错误");
            return send;
        }

        //是管理用户
        if (Usertype.管理用户.value() == user.getUsertype()){
            send.setCode(1);
            send.setMsg("登录为管理用户");
            send.setUsername(username);
            cacheUtil.saveCacheO("user", user);
            return send;
        }

        //查找主目录
        int userid = user.getId();
        UserConfig userConfig = userConfigMapper.selectUserConfigByUserid(userid);

        if (userConfig == null) {
            send.setCode(-4);
            send.setMsg("用户配置文件不存在");
            return send;
        }

        send.setCode(0);
        send.setMsg("登录成功");
        send.setUsername(user.getUsername());
        send.setMainfolder(userConfig.getMainfolder());

        //将用户信息保存到redis
        cacheUtil.saveCacheO("user", user);

        return send;
    }

    @Override
    public CaptchaDTOSend captchaGenerate() {
        CaptchaDTOSend captchaDTOSend = new CaptchaDTOSend();
        long timeMillis = System.currentTimeMillis();
        captchaDTOSend.setTimestamp(timeMillis);
        captchaDTOSend.setUrl("http://localhost:8080/captcha/img?timeMillis=" + timeMillis);
        Map<String, Object> codeAndPic = CaptchaUtil.generateCodeAndPic();
//        HttpSession session = req.getSession();
//        session.setAttribute("code", codeAndPic.get("code").toString());//将验证码写入缓存
        cacheUtil.setAndExpire("code",codeAndPic.get("code").toString(),30);
        //将验证码图片写入文件
        try {
            String captchaPath = freeSpaceRoot+"captcha/";
            OutputStream out = new FileOutputStream(captchaPath + timeMillis + ".jpg");
            ImageIO.write((RenderedImage) codeAndPic.get("codePic"), "jpeg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return captchaDTOSend;
    }

    @Override
    public byte[] captchaImg(long timeMillis) {
        String captchaPath = freeSpaceRoot+"captcha/";
        File file = new File(captchaPath + timeMillis + ".jpg");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean logout() {
        classManageServiceImpl.delCache();
        cacheUtil.delete("main_list");
        cacheUtil.delete("pwd");
        return cacheUtil.delete("user");
    }

    @Override
    public User getNowUser(){
        if (cacheUtil.hasKey("user")){
            return cacheUtil.getCacheO("user",User.class);
        }else {
            return null;
        }
    }
}
