package com.example.demo1.dto.send;

public class LoginDTOSend {
    private int code;
    private String msg;
    private String username;
    private String mainfolder;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMainfolder() {
        return mainfolder;
    }

    public void setMainfolder(String mainfolder) {
        this.mainfolder = mainfolder;
    }
}
