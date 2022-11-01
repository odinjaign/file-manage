package xyz.jaign.filemanage.enums;

public enum TaskOptType {
    复制(1),
    移动(2),
    重命名(3),
    删除(4);

    private final int value;
    TaskOptType(int value){
        this.value = value;
    }

    public int value(){
        return this.value;
    }

}
