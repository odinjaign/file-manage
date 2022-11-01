package xyz.jaign.filemanage.dto.send;

import java.util.List;

public class FolderPasswordDTOSend {
    public static class FolderPasswordNode{
        private int id;
        private String path;
        private String password;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    private int code;
    private String msg;
    private int count;
    private List<FolderPasswordNode> data;

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

    public List<FolderPasswordNode> getData() {
        return data;
    }

    public void setData(List<FolderPasswordNode> data) {
        this.data = data;
    }
}
