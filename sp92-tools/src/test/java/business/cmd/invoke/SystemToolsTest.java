package business.cmd.invoke;

import cmd.invoke.SystemTools;
import org.junit.Test;

import java.io.IOException;

public class SystemToolsTest {


    @Test
    public void openChrome(){
        String result = SystemTools.openBrowser("www.baidu.com");
        System.out.println(result);
    }

    /**
     rem 脚本内容
     @echo off
     echo "run shell"
     rem pause
     */
    @Test
    public void runShell(){
//        String result = SystemTools.runShell("E:\\filesystem\\test\\shell\\","shell.bat");
//        System.out.println(result);
        try {
            Runtime.getRuntime().exec("explorer.exe /select," + "ftp://everforcc:c.c.5664@180.76.156.43/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
