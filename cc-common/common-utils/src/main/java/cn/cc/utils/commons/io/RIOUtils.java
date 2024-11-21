package cn.cc.utils.commons.io;

import cn.cc.utils.constant.CharsetsConstant;
import com.google.common.io.ByteStreams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * commons.io
 */
@Slf4j
public class RIOUtils {

    @SneakyThrows
    public static String toString(final InputStream input, final String encoding) {
        return IOUtils.toString(input, encoding);
    }

    @SneakyThrows
    public static String toString(final InputStream input) {
        return IOUtils.toString(input, CharsetsConstant.UTF_8);
    }

    @SneakyThrows
    public static byte[] toByteArray(InputStream inputStream) {
        return ByteStreams.toByteArray(inputStream);
    }

    @SneakyThrows
    public static String outStreamWithLog(InputStream in, final String encoding) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        // 用一个读输出流类去读
        BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding));
        String line;
        // 逐行读取输出到控制台
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
            log.info(line);
        }
        return stringBuilder.toString();
    }

}
