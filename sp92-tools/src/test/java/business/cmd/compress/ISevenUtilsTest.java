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

}
