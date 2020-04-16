package com.example.demo1.enums;

public enum SysConfigKey {
    文件隐藏("filehdie"),
    分类浏览("typeview"),
    收藏功能("favorite"),
    操作日志("optlogs"),
    上传功能("upload"),
    系统隐藏("syshide");

    private final String value;
    SysConfigKey(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
