package com.example.demo1.entity;

public class UserConfig {
    private int id;
    private int userid;
    private String username;
    private String mainfolder;
    private int passwordtype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public int getPasswordtype() {
        return passwordtype;
    }

    public void setPasswordtype(int passwordtype) {
        this.passwordtype = passwordtype;
    }
}
