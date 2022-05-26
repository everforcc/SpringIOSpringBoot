package com.cc.sp90utils.commons.io;

import com.cc.sp90utils.constant.CharsetsConstant;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 重构 reconsitution
 * 引入的包
 * apache.commons.io
 */
public class RFileUtils {


    /**
     *
     */

    private static void t(){

    }

    /**
     * 读取文件数据为一行一行的
     * @param path
     * @param charset
     * @return
     */
    @SneakyThrows
    public static List<String> readLines(String path, String charset){
        return FileUtils.readLines(new File(path),charset);
    }

    @SneakyThrows
    public static List<String> readLines(String path, Charset charset){
        return FileUtils.readLines(new File(path),charset);
    }

    /**
     * 读取文件数据为一行
     * @param path
     * @param charset
     * @return
     */
    @SneakyThrows
    public static String readFileToString(String path, String charset){
        return FileUtils.readFileToString(new File(path),charset);
    }

    @SneakyThrows
    public static String readFileToString(String path, Charset charset){
        return FileUtils.readFileToString(new File(path),charset);
    }

    @SneakyThrows
    public static void writeStringToFile(String path, String content, Charset charset){
        FileUtils.writeStringToFile(new File(path),content,charset);
    }
    @SneakyThrows
    public static void writeStringToFile(String path, String content){
        FileUtils.writeStringToFile(new File(path),content, CharsetsConstant.UTF_8);
    }

    @SneakyThrows
    public static String byteCountToDisplaySize(final BigInteger size){
        return FileUtils.byteCountToDisplaySize(size);
    }

}
