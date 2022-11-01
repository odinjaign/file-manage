package xyz.jaign.filemanage.dto.send;

import java.util.List;

public class HideListDTOSend {
    public static class HidePathNode{
        private int id;
        private String path;
        private String type;//文件/文件夹

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public static class HideRegNode{
        private int id;
        private String reg;
        private String type;//文件/文件夹

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReg() {
            return reg;
        }

        public void setReg(String reg) {
            this.reg = reg;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    private int code;
    private String msg;
    private int count;
    private List<HidePathNode> pathdata;
    private List<HideRegNode> regdata;

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

    public List<HidePathNode> getPathdata() {
        return pathdata;
    }

    public void setPathdata(List<HidePathNode> pathdata) {
        this.pathdata = pathdata;
    }

    public List<HideRegNode> getRegdata() {
        return regdata;
    }

    public void setRegdata(List<HideRegNode> regdata) {
        this.regdata = regdata;
    }
}
