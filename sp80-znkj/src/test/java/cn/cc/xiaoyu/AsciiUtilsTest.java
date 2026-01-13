package cn.cc.xiaoyu;

import cn.cc.xiaoyu.util.charutil.AsciiUtils;
import org.junit.Test;

public class AsciiUtilsTest {

    @Test
    public void test1(){

    }

    @Test
    public void test2(){
        String hexStr = "393836303436313136313937323737343239300B";
        // 1. 十六进制字符串转 byte[]（核心：每两位转一个字节）
        byte[] byteArray = AsciiUtils.hexStringToByteArray(hexStr);
        String asciiResult = AsciiUtils.hexByteAsciiConvert_1(byteArray);
        System.out.println(asciiResult.toString());
        System.out.println("Netty 场景转换结果：" + AsciiUtils.escapeControlChars(asciiResult.toString()));
    }

}
