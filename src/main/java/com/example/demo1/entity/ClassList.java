package com.example.demo1.entity;

import java.util.Date;

public class ClassList {
    private int id;
    private int userid;
    private int classtype;
    private String checkfolder;
    private int checklength;
    private String checkexts;
    private Date checktime;

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

    public int getClasstype() {
        return classtype;
    }

    public void setClasstype(int classtype) {
        this.classtype = classtype;
    }

    public String getCheckfolder() {
        return checkfolder;
    }

    public void setCheckfolder(String checkfolder) {
        this.checkfolder = checkfolder;
    }

    public int getChecklength() {
        return checklength;
    }

    public void setChecklength(int checklength) {
        this.checklength = checklength;
    }

    public String getCheckexts() {
        return checkexts;
    }

    public void setCheckexts(String checkexts) {
        this.checkexts = checkexts;
    }

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }
}
