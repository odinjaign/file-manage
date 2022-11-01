package xyz.jaign.filemanage.enums;

public enum FolderPasswordType {
    关闭(0),
    文本密码(1),
    数据库密码(2);

    private final int value;
    FolderPasswordType(int value){
        this.value = value;
    }

    public int value(){
        return this.value;
    }

}
