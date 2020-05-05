package com.example.demo1.dto.send;

public class SysConfigDTOSend {
    public static class SysConfigData{
        private boolean filehide;
        private boolean typeview;
        private boolean favorite;
        private boolean optlogs;
        private boolean upload;
        private boolean syshide;

        public boolean isFilehide() {
            return filehide;
        }

        public void setFilehide(boolean filehide) {
            this.filehide = filehide;
        }

        public boolean isTypeview() {
            return typeview;
        }

        public void setTypeview(boolean typeview) {
            this.typeview = typeview;
        }

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
        }

        public boolean isOptlogs() {
            return optlogs;
        }

        public void setOptlogs(boolean optlogs) {
            this.optlogs = optlogs;
        }

        public boolean isUpload() {
            return upload;
        }

        public void setUpload(boolean upload) {
            this.upload = upload;
        }

        public boolean isSyshide() {
            return syshide;
        }

        public void setSyshide(boolean syshide) {
            this.syshide = syshide;
        }
    }

    private int code;
    private String msg;
    private SysConfigData data;

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

    public SysConfigData getData() {
        return data;
    }

    public void setData(SysConfigData data) {
        this.data = data;
    }
}
