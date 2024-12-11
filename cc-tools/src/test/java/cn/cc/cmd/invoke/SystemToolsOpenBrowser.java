package cn.cc.cmd.invoke;

import cn.cc.cmd.invoke.SystemTools;
import org.junit.Test;

import java.io.IOException;

public class SystemToolsOpenBrowser {

    @Test
    public void openChrome(){
        String result = SystemTools.openBrowser("www.baidu.com");
        System.out.println(result);
    }

}
