package com.cc.sp90utils.utils.charcode;

import com.cc.sp90utils.constant.CharsetsConstant;
import com.cc.sp90utils.utils.istream.InputStreamUtils;
import com.cc.sp90utils.utils.istream.impl.InputStreamUtilsImpl;

import java.nio.charset.Charset;

public class CharURLUtils {

    private static InputStreamUtils inputStreamUtils = new InputStreamUtilsImpl();

    /**
     * 处理url相关的编码问题
     *
     */

    /**
     *
     * @param str
     * @param charset {@link CharsetsConstant}
     * @return
     */
    public static String charToHexString(String str, Charset charset){
        String[] strings = inputStreamUtils.strToHexCodeWithCharsets(str,charset);

        StringBuffer stringBuffer = new StringBuffer();
        for(String s: strings){
            stringBuffer.append("%" + s);
        }

        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(charToHexString("+", CharsetsConstant.GB2312).toUpperCase());
        System.out.println(charToHexString(" ", CharsetsConstant.GB2312).toUpperCase());
        System.out.println(charToHexString("/", CharsetsConstant.GB2312).toUpperCase());
        System.out.println(charToHexString("?", CharsetsConstant.GB2312).toUpperCase());
        System.out.println(charToHexString("%", CharsetsConstant.GB2312).toUpperCase());
        System.out.println(0xff);
        System.out.println(Integer.toBinaryString(0xff));
    }

}
