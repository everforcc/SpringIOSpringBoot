package business.cmd.compress;

import cmd.compress.ISevenUtils;
import cmd.compress.constant.CompressEnum;
import cmd.compress.constant.FileTypeCMDEnum;
import cmd.compress.impl.SevenUtilsCMD;
import org.junit.Test;

public class ISevenUtilsTest {

    ISevenUtils iSevenUtils = new SevenUtilsCMD();
    // 压缩密码
    private static final String password = "";
    // 待压缩文件/目录 路径
    private static final String path = "fielPath";
    // 目标 文件路径
    //private static final String newPathFILE = "F:\\everforcc\\移动硬盘\\正在上传\\东周列国·春秋篇7z";
    // 目标 目录路径
    private static final String newPathDIR = "targetPath";

    /**
     * 压缩指定目录的所有文件
     */
    @Test
    public void compressFILE() {
        iSevenUtils.compress(password, path, newPathDIR, FileTypeCMDEnum.z7, CompressEnum.FILE);
    }

    /**
     * 压缩指定目录 到7z
     */
    @Test
    public void compressDIR() {
        iSevenUtils.compress(password, path, newPathDIR, FileTypeCMDEnum.z7, CompressEnum.DIR);
    }

    /**
     * 压缩单个文件
     */
    @Test
    public void compressOneFile() {
        //iSevenUtils.compress(password, path, FileTypeCMDEnum.z7, CompressEnum.FILE);
        //iSevenUtils.compress(password, path, newPathFILE, FileTypeCMDEnum.z7, CompressEnum.FILE);
    }

    /**
     * 解压,不知道密码的时候测试密码
     */
    @Test
    public void deCompressOneFile() {
        String pas = "coser01.com";
//        String oldPath = "F:\\everforcc\\移动硬盘\\待处理数据\\Vol.013-蠢沫沫 [更新至 194 期]\\No.001\\No.001-艾米莉亚的睡衣 [40P].7z";
        String newPath = "F:\\everforcc\\移动硬盘\\待处理数据\\蠢沫沫\\";
//        iSevenUtils.deCompression(pas, oldPath, newPath);

        String fileRoot = "F:\\everforcc\\移动硬盘\\待处理数据\\Vol.013-蠢沫沫 [更新至 194 期]";
//        File file = new File(fileRoot);
//        if (file.isDirectory()) {
//            File[] files = file.listFiles();
//            for (File tempDir : files) {
//                if (tempDir.isDirectory()) {
//                    File[] files7z = tempDir.listFiles();
//                    System.out.println("files7z.length:" + files7z.length);
//                    for (File z7 : files7z) {
//                        System.out.println(z7.getAbsolutePath());
//                        iSevenUtils.deCompression(pas, z7.getAbsolutePath(), newPath);
//                    }
//                }
//            }
//        }
    }

}
