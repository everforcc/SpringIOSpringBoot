package com.cc.sp90utils.runtime;

import com.cc.sp90utils.commons.io.RIOUtils;
import com.cc.sp90utils.constant.CharsetsConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;

@Slf4j
public class CmdUtils {

    // private static RIOUtils iioUtilsApache = new RIOUtils();
    // private static IIOUtils iioUtilsStream = new IOUtilsStream();

    private static String EXE = "cmd.exe ";
    /**
     * 这个单机是没问题的，并发看下文档，目前用不到
     */
    private static String DEFAULT_MODULE = "/C ";
    //
    public static String openBrowser="rundll32 url.dll,FileProtocolHandler ";

    public static String execProcess(String command) {
        return execProcess(DEFAULT_MODULE,command);
    }
    @SneakyThrows
    public static String execProcess(String module, String command) {
        log.info("执行命令: {}",command);
        ProcessBuilder builder = new ProcessBuilder(EXE,module, command);
        Process process = builder.redirectErrorStream(true).start();
        InputStream in;
        /**
         * 导致当前线程等待，如有必要，一直要等到由该 Process 对象表示的进程已经终止。
         * 如果已终止该子进程，此方法立即返回。如果没有终止该子进程，调用的线程将被阻塞，直到退出子进程。
         */
        process.waitFor();
        int exitStatus = process.exitValue();
        /**
         * 可以先判断状态，再取数据
         */
//        if(0 == exitStatus){
            in = process.getInputStream();
            String result = RIOUtils.toString(in, CharsetsConstant.GBK.toString());
            log.debug("执行结果 getInputStream: {}",result);
//        }else {
//            in = process.getErrorStream();
//            String result = iioUtilsApache.ioToString(in, CharsetsConstant.GBK.toString());
//            log.debug("执行结果 getErrorStream: {}",result);
//        }
        log.debug("exitStatus: {}",exitStatus);
//        if(){ 0 表示正常终止
//        }

//        String result = iioUtilsApache.ioToString(in);
//        log.debug("执行结果: {}",result);
        process.destroy();
        return result;
    }

    /**
     * 有六个重构，用到的话在看，举几个例子
     */
    @SneakyThrows
    public static String execRuntime(String shell){
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(shell);
        String result = process(process);
        runtime.gc();
        return result;
        // Process p = Runtime.getRuntime().exec("cmd /k startup.bat",null,new File(startFir));
        // Process p = Runtime.getRuntime().exec("cmd /c ipconfig ",null,new File(startFir));
    }

    @SneakyThrows
    public static String execRuntime(String filePath, String[] envp, String shell){
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(EXE + DEFAULT_MODULE + shell,envp,new File(filePath));
        String result = process(process);
        runtime.gc();
        return result;
    }

    private static String process(Process process){
        String result = RIOUtils.toString(process.getInputStream());
        process.destroy();
        return result;
    }


}
