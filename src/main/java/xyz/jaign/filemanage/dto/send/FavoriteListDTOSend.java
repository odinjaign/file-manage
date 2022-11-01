package xyz.jaign.filemanage.dto.send;

import java.util.Date;
import java.util.List;

public class FavoriteListDTOSend {
    public static class FavoriteNode{
        private String path;
        private boolean isFolder;
        private String name;
        private Date time;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isFolder() {
            return isFolder;
        }

        public void setFolder(boolean folder) {
            isFolder = folder;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    private List<FavoriteNode> data;

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

    public List<FavoriteNode> getData() {
        return data;
    }

    public void setData(List<FavoriteNode> data) {
        this.data = data;
    }
}
