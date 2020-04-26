package com.example.demo1.util;

import cn.hutool.crypto.digest.DigestUtil;

public class PasswordUtils {
    //密码混淆
    public static String passwords(String password){
        return DigestUtil.md5Hex(password);
    }
}
