package com.example.demo1.util;

import cn.hutool.core.io.FileUtil;
import com.example.demo1.dto.send.MovieDTOSend;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.text.SimpleDateFormat;

public class MovieOptUtil {
    public static MovieDTOSend.MovieNode toNode(File movieFile) {
        MovieDTOSend.MovieNode movieNode = new MovieDTOSend.MovieNode();
        movieNode.setName(movieFile.getName());
        movieNode.setLength(playLength(movieFile));
        movieNode.setPath(movieFile.getPath());
        movieNode.setSize(movieFile.length() / 1024);
        movieNode.setType(StringOptUtil.fileExt(movieFile.getName()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        movieNode.setTime(format.format(movieFile.lastModified()));
        return movieNode;
    }

    //获取视频文件的时长
    public static String playLength(File file) {
        long second = 0L;
        int min = 0;
        int hr = 0;
        int sec = 0;
        try {
            MultimediaObject instance = new MultimediaObject(file);
            MultimediaInfo result = instance.getInfo();
            second = result.getDuration() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将秒转为 时长格式
        sec = (int) (second % 60);
        second -= sec;
        min = (int) (second / 60);
        if (min > 59) {
            hr = min / 60;
            min = min % 60;
        }
        return StringOptUtil.valueOfInZero(hr, 2) + ":" +
                StringOptUtil.valueOfInZero(min, 2) + ":" +
                StringOptUtil.valueOfInZero(sec, 2);

    }
}
