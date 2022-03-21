package com.cc.sp90utils.utils;


import com.cc.sp90utils.constant.CharsetsConstant;
import org.apache.commons.lang3.StringUtils;
import java.nio.charset.Charset;
import java.util.Objects;

public class Base64Utils {

    /**
     * 将指定编码的字符串转换为 Base64 编码字符串
     *
     * @param content String
     * @return String 返回Base64编码字符串
     */
    public static String encode(final String content) {
        return encode(content, null);
    }

    /**
     * 将指定编码的字符串转换为 Base64 编码字符串
     *
     * @param content String
     * @param charset Charset
     * @return String 返回Base64编码字符串
     */
    public static String encode(final String content, Charset charset) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        if (Objects.isNull(charset)) {
            charset = CharsetsConstant.UTF_8;
        }
        return org.apache.commons.codec.binary.Base64.encodeBase64String(content.getBytes(charset));
    }

    /**
     * 将 Base64 编码字符串转换为指定编码的字符串
     *
     * @param content String
     * @return String 返回指定编码字符串
     */
    public static String decode(final String content) {
        return decode(content, null);
    }

    /**
     * 将 Base64 编码字符串转换为指定编码的字符串
     *
     * @param content String
     * @param charset Charset
     * @return String 返回指定编码字符串
     */
    public static String decode(final String content, Charset charset) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        if (Objects.isNull(charset)) {
            charset = CharsetsConstant.UTF_8;
        }
        return new String(org.apache.commons.codec.binary.Base64.decodeBase64(content), charset);
    }

    /**
     * 将指定编码的字符串转换为 Base64 编码字符串
     *
     * @param content String
     * @return String 返回Base64编码字符串
     */
    public static String encoder(final byte[] content) {
        if (Objects.isNull(content)) {
            return null;
        }
        return org.apache.commons.codec.binary.Base64.encodeBase64String(content);
    }

    /**
     * 将 Base64 编码字符串转换为 byte[]
     *
     * @param content String
     * @return String 返回指定编码字符串
     */
    public static byte[] decoder(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        return org.apache.commons.codec.binary.Base64.decodeBase64(content);
    }

    public static void main(String[] args) {

    }

}
