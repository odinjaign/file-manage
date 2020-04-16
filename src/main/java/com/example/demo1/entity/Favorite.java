package com.example.demo1.entity;

import java.util.Date;

public class Favorite {
    private int id;
    private int userid;
    private String favoritepath;
    private int favoritetype;
    private Date favoritetime;

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

    public String getFavoritepath() {
        return favoritepath;
    }

    public void setFavoritepath(String favoritepath) {
        this.favoritepath = favoritepath;
    }

    public int getFavoritetype() {
        return favoritetype;
    }

    public void setFavoritetype(int favoritetype) {
        this.favoritetype = favoritetype;
    }

    public Date getFavoritetime() {
        return favoritetime;
    }

    public void setFavoritetime(Date favoritetime) {
        this.favoritetime = favoritetime;
    }
}
