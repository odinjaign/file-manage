package com.example.demo1.dto.send;


import java.util.List;

public class MusicDTOSend {

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class MusicNode{
        private int id;
        private long length;//second
        private String name;
        private String title;
        private String ext;
        private String path;
        private String singer;
        private String sheet;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getLength() {
            return length;
        }

        public void setLength(long length) {
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getSheet() {
            return sheet;
        }

        public void setSheet(String sheet) {
            this.sheet = sheet;
        }
    }

    private int code;
    private String msg;
    private int count;
    private List<MusicNode> data;

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

    public List<MusicNode> getData() {
        return data;
    }

    public void setData(List<MusicNode> data) {
        this.data = data;
    }
}
