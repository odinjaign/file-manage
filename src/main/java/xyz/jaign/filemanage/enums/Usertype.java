package xyz.jaign.filemanage.enums;

public enum Usertype {
    注册用户(1),
    内置用户(2),
    管理用户(3);

    private final int value;
    Usertype(int value){
        this.value = value;
    }

    public int value(){
        return this.value;
    }

}
