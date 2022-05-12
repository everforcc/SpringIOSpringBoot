package cmd.compress;

public enum CompressEnum {

    /**
     * 压缩目录下的所有文件
     */
    FILE(true),
    /**
     * 只压缩当前目录下的目录
     * 并在同级目录后加个7z后缀
     */
    DIR(false),
    ;

    public boolean ISCD;

    CompressEnum(boolean ISCD) {
        this.ISCD = ISCD;
    }

    public static void main(String[] args) {

    }

}
