package business.cmd.invoke;

import cmd.invoke.SystemTools;
import org.junit.Test;

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
        String result = SystemTools.runShell("E:\\filesystem\\test\\shell\\","shell.bat");
        System.out.println(result);
    }

}
