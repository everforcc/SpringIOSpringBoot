package com.cc.sp90utils.utils.istream.impl;

import com.cc.sp90utils.utils.istream.InputStreamUtils;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class InputStreamUtilsImpl implements InputStreamUtils {

    @Override
    public String[] strToHexCodeWithCharsets(String str, Charset charset) {


        byte[] bytes = str.getBytes(charset);

        int length = bytes.length;
        String[] strings = new String[bytes.length];
        StringBuffer stringBuffer = new StringBuffer();

        for(int i=0; i<length; i++){
            strings[i] = Integer.toHexString(bytes[i] & 0xFF);
        }
        return strings;
//        for(byte b: bytes){
//            /**
//             * 处理为16进制，此处 b & 0xFF 去掉高8位，虽然也没有
//             */
//            stringBuffer.append("%" + Integer.toHexString(b & 0xFF));
//        }

        //return stringBuffer.toString();
    }
}
