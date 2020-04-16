package com.example.demo1.test;

import cn.hutool.core.io.FileUtil;
import com.example.demo1.util.FileOptUtil;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        File file = new File("C:/Users/jaign/Desktop/");
        File file1 = new File("C:/Users/jaign/Desktop");
        System.out.println(FileOptUtil.fileInFolder(file, file1));

    }
}
