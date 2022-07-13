package cmd.ffmpeg.constant;

/**
 * @author c.c.
 * @date 2020/12/10
 */
public class ConstantFFmpeg {
    // FFMPEG  常用的命令

    // m3u8 转码mp4 1.第一个参数 m3u8 的位置， 2.第二个新文件的位置
    public static final String m3u8ToVideoCOMMAND = "ffmpeg -allowed_extensions ALL -protocol_whitelist \"file,http,crypto,tcp\" -i \"%s\" -c copy \"%s\"";

    // 1.清单的路径 2.目标文件的路径
    public static final String txtFileListCOMMAND = "ffmpeg -f concat -safe 0 -i \"%s\" -c copy \"%s\"";

    // ffmpeg 的安装路径
    public static final String FFMPEG_PATH = "D:\\environment\\cmd\\ffmpeg\\bin\\ffmpeg.exe"; // ffmpeg 程序迷路

    // 1.ffmpeg的位置，配置全局变量后，应该不需要了  2.文件路径video  3.图片路径  4.输出合并后文件路径
    public static final String replaceCoverCOMMAND = "%s -i %s -i %s -map 1 -map 0 -c copy -disposition:0 attached_pic -y %s"; // ffmpeg 替换封面的命令

    // 第一个参数，合并文件名 1.mp4|2.mp4
    public static final String concatVideoCOMMAND = "ffmpeg -i \"concat:%s\" -c copy \"%s\"";

    // 第0秒的第一帧 1.文件 2.图片
    public static final String screenImgCOMMAND = "ffmpeg -i \"%s\" -ss 0 -vframes 1 \"%s\"\n";

    // 1.文件名 2.开始时间 3.结束时间 4.生成文件 默认分辨率无需设置
    public static final String screenGIFCOMMAND = "ffmpeg -i \"%s\" -ss %s -to %s  -r 15 \"%s\"";

}
