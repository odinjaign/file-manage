package com.example.demo1.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileOptUtil {

    public static String getFolderTextPassword(String folder) {
        File file = new File(folder, "password.txt");
        if (file.exists()) {
            return new FileReader(file).readString();
        } else {
            return null;
        }
    }

    public static boolean fileInFolder(File file, File folder) {

        if (folder.getParent() == null){
            //顶层目录
            return file.getPath().startsWith(folder.getPath());
        }

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

    public static boolean createFile(File file) {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createFolder(File folder) {
        return FileUtil.mkdir(folder).exists();
    }

    public static List<File> lsFile2Num(List<File> rel, File folder, int num) {
        if (num == 0) return rel;
        // 已处理
        // if (!FileUtil.exist(folder)) return rel;
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                rel = lsFile2Num(rel, file, num - 1);
            } else {
                rel.add(file);
            }
        }
        return rel;
    }

}
