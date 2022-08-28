package com.cc.sp90utils.commons.web;

import com.cc.sp90utils.constant.CharsetsConstant;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 处理url常用的方法
 */
public class JUrlUtils {

    public static String urlSubFileName(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 主要是为了 处理URL链接上 的编码问题
     * @param str
     * @param charset
     * @return
     */
    public static String charToUrlCode(String str, Charset charset){
        return charToUrlCode(str,charset,true);
    }

    public static String charToUrlCode(String str, Charset charset,boolean offset){
        byte[] bytes = str.getBytes(charset);
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b:bytes){
            stringBuilder.append((offset?"%":"") + Integer.toHexString(b & 0xFF));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {

//        String[] strings = "coe@BH%*8L95#cjdA#".split("");
//        System.out.println(strings.length);
//        //String[] strings = new String[]{"+"," ","/","?","%"};
//        for(String s:strings){
//            System.out.print(charToUrlCode(s, CharsetsConstant.GB2312).toUpperCase());
//        }
        /**
         * 2b
         * 20
         * 2f
         * 3f
         * 25
         */
        //System.out.println(charToUrlCode("@", CharsetsConstant.GB2312).toUpperCase());
        String str = "coe@BH%*8L95#cjdA#";
        System.out.println(URLEncoder.encode(str));
    }

}
