package cn.cc.utils.commons.io;

import cn.cc.utils.constant.CharsetsConstant;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
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

    private static void t() {

    }

    /**
     * 读取文件数据为一行一行的
     *
     * @param path
     * @param charset
     * @return
     */
    @SneakyThrows
    public static List<String> readLines(String path, String charset) {
        return FileUtils.readLines(new File(path), charset);
    }

    @SneakyThrows
    public static List<String> readLines(String path, Charset charset) {
        return FileUtils.readLines(new File(path), charset);
    }

    /**
     * 读取文件数据为一行
     *
     * @param path
     * @param charset
     * @return
     */
    @SneakyThrows
    public static String readFileToString(String path, String charset) {
        return FileUtils.readFileToString(new File(path), charset);
    }

    @SneakyThrows
    public static String readFileToString(String path) {
        return FileUtils.readFileToString(new File(path), CharsetsConstant.UTF_8);
    }

    @SneakyThrows
    public static byte[] readFileToBytes(String path) {
        return FileUtils.readFileToByteArray(new File(path));
    }

    @SneakyThrows
    public static String readFileToString(File file) {
        return FileUtils.readFileToString(file, CharsetsConstant.UTF_8);
    }

    @SneakyThrows
    public static void writeStringToFile(String path, String content, Charset charset) {
        FileUtils.writeStringToFile(new File(path), content, charset);
    }

    @SneakyThrows
    public static void writeStringToFile(String path, String content) {
        FileUtils.writeStringToFile(new File(path), content, CharsetsConstant.UTF_8);
    }

    @SneakyThrows
    public static void writeStringToFile(String path, String content, boolean append) {
        FileUtils.writeStringToFile(new File(path), content, CharsetsConstant.UTF_8, append);
    }

    @SneakyThrows
    public static String byteCountToDisplaySize(final BigInteger size) {
        return FileUtils.byteCountToDisplaySize(size);
    }

    @SneakyThrows
    public static void copyURLToFile(String url, String filePath) {
        FileUtils.copyURLToFile(new URL(url), new File(filePath));
    }

    @SneakyThrows
    public static void copyToFile(final InputStream source, final String destination) {
        FileUtils.copyToFile(source, new File(destination));
    }

    /**
     * 检查文件是否存在
     */
    public static Boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

}
