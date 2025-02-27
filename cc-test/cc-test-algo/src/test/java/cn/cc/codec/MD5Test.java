package cn.cc.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.MD5ApacheTest;
import org.junit.Test;

import security.MD5JDKTest;

@Slf4j
public class MD5Test {

    // 测试aaa
    private static final String str = "测";

    @Test
    public void md5Test() {
        MD5JDKTest.getMD5(str);
        MD5ApacheTest.getMD5(str);
        // DigestUtilsTest spring工具类
    }

}
