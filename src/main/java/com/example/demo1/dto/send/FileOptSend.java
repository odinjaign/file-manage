package com.example.demo1.dto.send;

public class FileOptSend {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void apendMsg(String msg){
        this.msg += msg;
    }
}
