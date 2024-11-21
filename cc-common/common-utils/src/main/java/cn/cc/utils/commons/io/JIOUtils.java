package cn.cc.utils.commons.io;

import cn.cc.utils.constant.CharsetsConstant;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * jdk写的工具类,容易扩展
 */
public class JIOUtils {

    public static String toString(InputStream inputStream, String charset) {
        return commonToString(inputStream,charset);
    }

    public static String toString(InputStream inputStream) {
        return commonToString(inputStream, CharsetsConstant.UTF_8.toString());
    }

    @SneakyThrows
    private static String commonToString(InputStream inputStream, String charset){
        @Cleanup
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,charset));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

}
