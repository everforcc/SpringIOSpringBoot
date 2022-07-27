package business.cmd.compress;

import cmd.compress.ISevenUtils;
import cmd.compress.constant.CompressEnum;
import cmd.compress.constant.FileTypeCMDEnum;
import cmd.compress.impl.SevenUtilsCMD;
import org.junit.Test;

public class ISevenUtilsTest {

    ISevenUtils iSevenUtils = new SevenUtilsCMD();
    // 压缩密码
    private static final String password = "123qwe";
    // 待压缩文件/目录 路径
    private static final String path = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp92-tools\\compress";
    // 目标文件路径
    private static final String newPathFILE = "F:\\everforcc\\移动硬盘\\正在上传\\东周列国·春秋篇7z";
    // 目标目录路径
    private static final String newPathDIR = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp92-tools\\compress7z";

    /**
     * 压缩指定目录的所有文件
     */
    @Test
    public void compress() {
        //iSevenUtils.compress(password, path, FileTypeCMDEnum.z7, CompressEnum.DIR);
        iSevenUtils.compress(password, path, newPathDIR, FileTypeCMDEnum.z7, CompressEnum.DIR);
    }

    /**
     * 压缩单个文件
     */
    @Test
    public void compressOneFile() {
        //iSevenUtils.compress(password, path, FileTypeCMDEnum.z7, CompressEnum.FILE);
        iSevenUtils.compress(password, path, newPathFILE, FileTypeCMDEnum.z7, CompressEnum.FILE);
    }

    /**
     * 解压,不知道密码的时候测试密码
     */
    @Test
    public void deCompressOneFile() {
        String pas = "99";
        String oldPath = "F:\\Cache\\java\\巧克力与香子兰番外.zip";
        String newPath = "F:\\Cache\\java\\解压";
        iSevenUtils.deCompression(pas, oldPath, newPath);

    }

}
