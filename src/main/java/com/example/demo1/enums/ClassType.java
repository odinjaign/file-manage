package com.example.demo1.enums;

public enum ClassType {
    音乐(1),
    视频(2),
    文档(3),
    图片(4);

    private final int value;
    ClassType(int value){
        this.value = value;
    }

    public int value(){
        return this.value;
    }

}
