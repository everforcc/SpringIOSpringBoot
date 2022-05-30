package business.cmd.compress;

import cmd.compress.constant.CompressEnum;
import cmd.compress.constant.FileTypeCMDEnum;
import cmd.compress.ISevenUtils;
import cmd.compress.impl.SevenUtilsCMD;
import org.junit.Test;

public class ISevenUtilsTest {

    ISevenUtils iSevenUtils = new SevenUtilsCMD();
    private static String password = "123qwe";
    private static String path = "E:\\filesystem\\test\\7z\\测试文件";
    private static String newPathFILE = "E:\\filesystem\\test\\7z\\newfile";
    private static String newPathDIR = "E:\\filesystem\\test\\7z\\newdir";

    /**
     * 压缩指定目录的所有文件
     */
    @Test
    public void compress(){

        /**
         * 默认路径
         * 7z
         * file
         */
        //iSevenUtils.compress(password,path, FileTypeCMDEnum.z7, CompressEnum.FILE);
        //iSevenUtils.compress(password,path, FileTypeCMDEnum.z7, CompressEnum.DIR);

        /**
         * 执行路径
         */
        iSevenUtils.compress(password,path,newPathFILE, FileTypeCMDEnum.z7, CompressEnum.FILE);
        iSevenUtils.compress(password,path,newPathDIR, FileTypeCMDEnum.z7, CompressEnum.DIR);
    }

    /**
     * 压缩单个文件
     */
    @Test
    public void compressOneFile(){
        iSevenUtils.compress(password,path,newPathFILE, FileTypeCMDEnum.z7, CompressEnum.FILE);
        iSevenUtils.compress(password,path,newPathDIR, FileTypeCMDEnum.z7, CompressEnum.DIR);
    }

    @Test
    public void deCompressOneFile(){
        String pas = "99";
        String oldPath = "F:\\Cache\\java\\巧克力与香子兰番外.zip";
        String newPath = "F:\\Cache\\java\\解压";
        iSevenUtils.deCompression(pas, oldPath, newPath);

    }

}
