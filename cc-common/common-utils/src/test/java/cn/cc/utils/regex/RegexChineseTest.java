package cn.cc.utils.regex;

import cn.cc.utils.regex.constant.RegexChineseConstant;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RegexChineseTest {

    /**
     * 校验给定字符串只有
     * 中文，英文，数字
     */
    @Test
    public void t1() {
        String[] strAry = {};
        List<String> stringList = Arrays.asList("a123b啊啊啊啊", "a123b啊啊啊啊_");
        stringList.forEach(e -> {
            boolean result = RegexUtils.isMatches(RegexChineseConstant.CHINESE_AZ_NUMBER, e);
            System.out.println(e);
            System.out.println(result);
        });
    }

}
