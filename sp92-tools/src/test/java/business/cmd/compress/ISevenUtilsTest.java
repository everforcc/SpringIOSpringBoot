package business.cmd.compress;

import cmd.compress.CompressEnum;
import cmd.compress.FileTypeCMDEnum;
import cmd.compress.ISevenUtils;
import cmd.compress.impl.SevenUtilsCMD;
import org.junit.Test;

public class ISevenUtilsTest {

    ISevenUtils iSevenUtils = new SevenUtilsCMD();

    @Test
    public void compress(){
        String password = "123qwe";
        String path = "E:\\filesystem\\test\\7z - 副本";
        iSevenUtils.compress(password,path, FileTypeCMDEnum.z7, CompressEnum.FILE);
    }

}
