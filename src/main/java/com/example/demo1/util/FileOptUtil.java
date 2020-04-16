package com.example.demo1.util;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;

public class FileOptUtil {

    public static boolean fileInFolder(File file, File folder) {
        String folderpath = folder.getPath() + "\\";
        if (file.equals(folder)) {
            return true;
        }
        return file.getPath().startsWith(folderpath);
    }

    public static boolean fileInFolder(String filepath, String folderpath) {
        return fileInFolder(new File(filepath), new File(folderpath));
    }

    public static boolean fileInFolder(String filepath, File folderpath) {
        return fileInFolder(new File(filepath), folderpath);
    }

    public static boolean fileInFolder(File filepath, String folderpath) {
        return fileInFolder(filepath, new File(folderpath));
    }

    public static boolean createFile(File file){
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createFolder(File folder){
        return FileUtil.mkdir(folder).exists();
    }
}
