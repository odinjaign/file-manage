package xyz.jaign.filemanage.entity;

public class FolderPassword {
    private int id;
    private int userid;
    private String folderpath;
    private String folderpassword;
    private int status;

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

    public String getFolderpath() {
        return folderpath;
    }

    public void setFolderpath(String folderpath) {
        this.folderpath = folderpath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFolderpassword() {
        return folderpassword;
    }

    public void setFolderpassword(String folderpassword) {
        this.folderpassword = folderpassword;
    }
}
