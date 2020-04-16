package com.example.demo1.enums;

public enum Passwordtype {
    关闭(0),
    文本密码(1),
    数据库密码(2);

    private final int value;
    Passwordtype(int value){
        this.value = value;
    }

    public int value(){
        return this.value;
    }

}
