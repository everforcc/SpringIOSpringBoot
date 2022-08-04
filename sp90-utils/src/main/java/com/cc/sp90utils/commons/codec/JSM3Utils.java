/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-04 15:37
 * Copyright
 */

package com.cc.sp90utils.commons.codec;

import com.cc.sp90utils.constant.CharsetsConstant;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.SM3Digest;

import java.io.UnsupportedEncodingException;

/**
 * 国产 加密工具
 * org.bouncycastle
 */
public class JSM3Utils {

    /**
     * SM3 摘要
     *
     * @param data 数据byte
     * @return 摘要信息
     */
    private static byte[] sm3(byte[] data) {
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(data, 0, data.length);
        byte[] result = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(result, 0);
        return result;
    }

    /**
     * SM3 摘要
     *
     * @param data     数据
     * @param encoding 字符集
     * @return 摘要信息
     */
    private static byte[] sm3(String data, String encoding) {
        try {
            return sm3(data.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的字符编码");
        }
    }

    /**
     * SM3 摘要进行 Hex转码
     *
     * @param data     数据
     * @param encoding 字符集
     * @return Hex 格式的 SM3 摘要
     */
    public static String sm3Hex(String data, String encoding) {
        return Hex.encodeHexString(sm3(data, encoding), false);
    }

    /**
     * SM3 摘要进行 Hex转码
     *
     * @param data 数据
     * @return Hex 格式的 SM3 摘要
     */
    public static String sm3Hex(String data) {
        return Hex.encodeHexString(sm3(data, CharsetsConstant.UTF_8.toString()), false);
    }

    /**
     * SM3 摘要进行 Base64 转码
     *
     * @param data     数据
     * @param encoding 编码
     * @return Base64 格式的 SM3 摘要
     */
    public static String sm3Base64(String data, String encoding) {
        return Base64.encodeBase64String(sm3(data, encoding));
    }

    /**
     * SM3 摘要进行 Base64 转码
     *
     * @param data 数据
     * @return Base64 格式的 SM3 摘要
     */
    public static String sm3Base64(String data) {
        return Base64.encodeBase64String(sm3(data, CharsetsConstant.UTF_8.toString()));
    }

}
