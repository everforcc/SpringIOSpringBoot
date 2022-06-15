package com.cc.sp90utils.enums;

import lombok.SneakyThrows;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符编码
 *
 */
public enum CharsetsEnum {
    /**
     * 如果有新的必须在下面追加否则会影响已有数据
     */
    UTF_8(StandardCharsets.UTF_8),
    GBK(Charset.forName("GBK")),
    ISO_8859_1(StandardCharsets.ISO_8859_1),
    UTF_16BE(StandardCharsets.UTF_16BE),
    UTF_16LE(StandardCharsets.UTF_16LE),
    UTF_16(StandardCharsets.UTF_16),
    big5(Charset.forName("big5")),
    gb2312(Charset.forName("gb2312")),
    /**
     * 如果有新的必须在下面追加否则会影响已有数据
     */
    ;
    public final Charset charset;

    public String displayName() {
        return charset.displayName();
    }

    /**
     * url 字符串编码
     *
     * @param value {@link String}
     * @return {@link String}
     */
    @SneakyThrows
    public String encode(final String value) {
        return URLEncoder.encode(value, charset.displayName());
    }

    /**
     * url 字符串解码
     *
     * @param value {@link String}
     * @return {@link String}
     */
    @SneakyThrows
    public String decode(final String value) {
        return URLDecoder.decode(value, charset.displayName());
    }

    CharsetsEnum(final Charset charset) {
        this.charset = charset;
    }

}
