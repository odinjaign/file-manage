package xyz.jaign.filemanage.util;

import cn.hutool.core.util.URLUtil;

import java.util.Arrays;

public class StringOptUtil {
    public static String fileExt(String filename) {
        String[] strings = filename.split("\\.");
        return strings[strings.length - 1].toLowerCase();
    }

    public static boolean extInExts(String ext, String exts) {
        return Arrays.asList(exts.split("\\|")).contains(ext);
    }

    public static String valueOfInZero(int num, int z) {
        int length = String.valueOf(num).length();
        int z_num = z - length;
        if (z_num < 0) return String.valueOf(num);
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < z_num; i++) {
            stringBuffer.append("0");
        }
        return stringBuffer.toString() + String.valueOf(num);
    }

    public static String imgToURL(String path){
        String imgEncodePath = URLUtil.encode(path);
        return "http://localhost:8080/image/img?path="+imgEncodePath;
    }
}
