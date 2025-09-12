package cn.cc.lkl.demo;

import cn.cc.lkl.util.LoadFileUtil;
import org.junit.Test;

public class LKLDebugTest {

    /**
     * 原先请求失败的时候解析字符串用的，现在不需要了
     */
    @Test
    public void t1() {

        String str = LoadFileUtil.loadJsonFromResource("调试/参数错误.json");
        System.out.println(str);
        // 将str截取为json字符串
        String json = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
        System.out.println(json);
    }

}
