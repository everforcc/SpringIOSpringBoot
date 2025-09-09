package cn.cc.lkl.auth;

import cn.cc.lkl.sdk.config.LKLConfig;
import com.lkl.laop.sdk.exception.SDKException;
import org.junit.Before;
import org.junit.Test;

public class WXRequestTest {
    @Before
    public void pre() {
        try {
            LKLConfig.initSDK();
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAliReq(){
        String json = "";
    }

}
