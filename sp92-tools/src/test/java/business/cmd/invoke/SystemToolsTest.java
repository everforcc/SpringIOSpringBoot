package business.cmd.invoke;

import cmd.invoke.SystemTools;
import org.junit.Test;

public class SystemToolsTest {


    @Test
    public void openChrome(){
        SystemTools.openBrowser("www.baidu.com");
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
