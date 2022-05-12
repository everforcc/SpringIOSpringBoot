package cmd.invoke;

import com.cc.sp90utils.runtime.CmdUtils;

public class SystemTools {

    public static void openBrowser(String url){
        CmdUtils.execRuntime(CmdUtils.openBrowser + url,null,null);
    }

    public static String runShell(String path,String shell){
        return CmdUtils.execRuntime(path,null,shell);
    }

}
