package xyz.jaign.filemanage.dto.send;

import java.util.List;

public class UserListDTOSend {

    public UserListDTOSend() {
    }

    public List<UserInfo> getData() {
        return data;
    }

    public void setData(List<UserInfo> data) {
        this.data = data;
    }

    public static class UserInfo{
        private String username;
        private String nickname;
        private int gender;
        private String email;
        private boolean nowuser;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isNowuser() {
            return nowuser;
        }

        public void setNowuser(boolean nowuser) {
            this.nowuser = nowuser;
        }
    }

    private int code;
    private String msg;
    private int count;
    private List<UserInfo> data;

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

}
