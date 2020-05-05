package com.example.demo1.test;

import cn.hutool.core.util.ZipUtil;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException {
//        System.out.println(PasswordUtils.passwords("admin"));
//        ZipUtil.unzip("C:\\Users\\jaign\\test\\jaign\\imgs.zip","C:\\Users\\jaign\\Desktop");
        ZipUtil.zip("C:\\Users\\jaign\\test\\jaign\\layui2");
    }
}
