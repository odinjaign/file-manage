package xyz.jaign.filemanage.entity;

import java.util.Date;

public class FileOptLogs {
    private int id;
    private int userid;
    private int logindex;
    private String logtype;
    private String logpath;
    private String logpathext;
    private Date logtime;
    private String lognote;

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

    public int getLogindex() {
        return logindex;
    }

    public void setLogindex(int logindex) {
        this.logindex = logindex;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public String getLogpathext() {
        return logpathext;
    }

    public void setLogpathext(String logpathext) {
        this.logpathext = logpathext;
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    public String getLognote() {
        return lognote;
    }

    public void setLognote(String lognote) {
        this.lognote = lognote;
    }
}
