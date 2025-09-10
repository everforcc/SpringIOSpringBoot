package cn.cc.lkl;

import cn.cc.lkl.sdk.config.LKLConfig;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

/**
 * lkl测试基类
 * 其他测试都继承这个
 */
@Slf4j
public class LKLBaseTest {

    @Before
    public void pre() throws SDKException {
        LKLConfig.initSDK();
    }

}
