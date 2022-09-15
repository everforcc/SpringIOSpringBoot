package cmd.compress.constant;

public class CompressConstant {

    // 7z 源文件 目标文件 密码
    // start /B 7z x %s -o%s -aoa -bse1 %s
    public static final String deCompressionCommand = "7z x \"%s\" -o\"%s\" -aoa -bse1 -p%s";
    // 压缩文件 -tzip 参数为压缩格式，可以规定为zip,7z等支持的格式 -t7z -tzip
    public static final String compressionCommand = "7z a -t%s -r \"%s\" \"%s\" %s ";

    public static String all = "/*";

}
