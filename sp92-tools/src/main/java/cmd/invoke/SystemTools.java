package cmd.invoke;

import cn.cc.utils.cmd.util.CmdUtils;

public class SystemTools {

    public static String openBrowser(String url){
        return CmdUtils.execRuntime(CmdUtils.openBrowser + url);
    }

    public static String runShell(String path,String shell){
        return CmdUtils.execRuntime(path,null,shell);
    }

}
