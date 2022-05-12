package cmd.compress;

public enum FileTypeCMDEnum {

    z7(".7z","7z"),
    zip(".zip","zip")
    ;

    public final String type;
    /**
     * -t后面的参数，喂了防止有变，这里配一下
     */
    public final String cmd_7z;

    FileTypeCMDEnum(String type, String cmd_7z) {
        this.type = type;
        this.cmd_7z = cmd_7z;
    }

    public static void main(String[] args) {
        System.out.println(FileTypeCMDEnum.zip.toString());
    }

}
