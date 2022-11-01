package xyz.jaign.filemanage.dto.send;

import java.util.List;

public class TaskListDTOSend {

    public static class TaskNode{
        private String taskid;
        private String taskname;
        private int tasktype;
        private String folder;
        private String regfile;
        private String target;
        private boolean enabled;
        private String cron;

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

        public int getTasktype() {
            return tasktype;
        }

        public void setTasktype(int tasktype) {
            this.tasktype = tasktype;
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

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }
    }

    private int code;
    private String msg;
    private int count;
    private List<TaskNode> data;

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

    public List<TaskNode> getData() {
        return data;
    }

    public void setData(List<TaskNode> data) {
        this.data = data;
    }
}
