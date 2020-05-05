package com.example.demo1.dto.send;

import java.util.Date;
import java.util.List;

public class OptLogsDTOSend {
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class LogNode{
        private int index;
        private String type;
        private String src;
        private String dst;
        private String note;
        private Date time;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }

    private int code;
    private String msg;
    private int count;
    private List<LogNode> data;

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

    public List<LogNode> getData() {
        return data;
    }

    public void setData(List<LogNode> data) {
        this.data = data;
    }
}
