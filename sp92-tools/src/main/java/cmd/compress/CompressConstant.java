package cmd.compress;

public class CompressConstant {

    // 7z 源文件 目标文件 密码
    public static final String deCompressionCommand = "start /B %s x %s -o%s -aoa -bse1 %s";
    // 压缩文件 -tzip 参数为压缩格式，可以规定为zip,7z等支持的格式 -t7z -tzip
    public static final String compressionCommand = "7z a -t%s -r \"%s\" \"%s\" %s ";

    public static String all = "/*";

}
