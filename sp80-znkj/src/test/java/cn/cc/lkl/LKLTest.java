package cn.cc.lkl;

import org.junit.Test;

public class LKLTest {

    /**
     * 原先请求失败的时候解析字符串用的，现在不需要了
     */
    @Test
    public void t1() {

        String str = "请求响应失败：{\"code\":\"OP90001\",\"msg\":\"请求服务失败【无效请求(请求角色【AGENT】无访问权限，联系相关人员开通)】\"}";
        // 将str截取为json字符串
        String json = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
        System.out.println(json);
    }

}
