package com.example.demo1.entity;

import java.util.Date;

public class HideList {
    private int id;
    private int userid;
    private int hidetype;
    private String hidecontent;
    private Date hidetime;

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

    public int getHidetype() {
        return hidetype;
    }

    public void setHidetype(int hidetype) {
        this.hidetype = hidetype;
    }

    public String getHidecontent() {
        return hidecontent;
    }

    public void setHidecontent(String hidecontent) {
        this.hidecontent = hidecontent;
    }

    public Date getHidetime() {
        return hidetime;
    }

    public void setHidetime(Date hidetime) {
        this.hidetime = hidetime;
    }
}
