package com.example.demo1.entity;

public class Task {
    private Integer id;
    private String taskid;
    private String taskname;
    private String cron;
    private String folder;
    private String regfile;
    private int opt;//1复制2移动3重命名4删除
    private String targetpath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getRegfile() {
        return regfile;
    }

    public void setRegfile(String regfile) {
        this.regfile = regfile;
    }

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public String getTargetpath() {
        return targetpath;
    }

    public void setTargetpath(String targetpath) {
        this.targetpath = targetpath;
    }
}
