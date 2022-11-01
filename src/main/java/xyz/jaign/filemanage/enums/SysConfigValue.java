package xyz.jaign.filemanage.enums;

public enum SysConfigValue {
    开启(1),
    关闭(2);

    private final int value;
    SysConfigValue(int value){
        this.value = value;
    }

    public int value(){
        return this.value;
    }

}
