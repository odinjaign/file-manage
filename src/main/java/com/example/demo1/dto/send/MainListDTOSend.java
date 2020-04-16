package com.example.demo1.dto.send;

import java.util.Date;
import java.util.List;

public class MainListDTOSend {

    public static class FileNode{
        private String name;
        private Date updatetime;
        private String type;
        private long size;
        private boolean isFolder;
        private String path;
        private boolean isFavorite = false;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(Date updatetime) {
            this.updatetime = updatetime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public boolean isFolder() {
            return isFolder;
        }

        public void setFolder(boolean folder) {
            isFolder = folder;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isFavorite() {
            return isFavorite;
        }

        public void setFavorite(boolean favorite) {
            isFavorite = favorite;
        }
    }

    private int code;
    private String msg;
    private int count;
    private List<FileNode> data;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<FileNode> getData() {
        return data;
    }

    public void setData(List<FileNode> data) {
        this.data = data;
    }
}
