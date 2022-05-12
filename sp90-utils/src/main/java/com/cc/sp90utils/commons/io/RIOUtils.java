package com.cc.sp90utils.commons.io;

import com.cc.sp90utils.constant.CharsetsConstant;
import com.google.common.io.ByteStreams;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 *
 * commons.io
 */
public class RIOUtils {

    @SneakyThrows
    public static String toString(final InputStream input, final String encoding){
        return IOUtils.toString(input,encoding);
    }

    @SneakyThrows
    public static String toString(final InputStream input){
        return IOUtils.toString(input, CharsetsConstant.UTF_8);
    }

    @SneakyThrows
    public static byte[] toByteArray(InputStream inputStream) {
        return ByteStreams.toByteArray(inputStream);
    }

}
