package cn.cc.codec;

import org.apache.commons.codec.SHAApacheTest;
import org.junit.Test;
import security.SHAJDKTest;
import util.UUIDTest;

/**
 * sha256示例
 */
public class SHATest {

    @Test
    public void sha256Test() {
        String str = "aaa测试";
        SHAJDKTest.sha256(str);
        SHAApacheTest.sha256HexTest(str);
        SHAApacheTest.sha256HexFlowTest(str);
        String salt = UUIDTest.uuid32();
        SHAApacheTest.sha256HexWithAppendSaltTest(str, salt);
        salt = UUIDTest.uuid32();
        SHAApacheTest.sha256HexWithAppendSaltTest(str, salt);
    }

}
