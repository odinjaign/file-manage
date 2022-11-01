package xyz.jaign.filemanage.enums;

public enum Favoritetype {
    文件夹(1),
    文件(2);

    private final int value;
    Favoritetype(int value){
        this.value = value;
    }

    public int value(){
        return this.value;
    }

}
