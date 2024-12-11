package cn.cc;

import org.apache.commons.lang3.StringUtils;

public class TestUtils {

    public static void main(String[] args) {
        String s_null = null;
        String s_not_null = "not null";
        System.out.println(StringUtils.isNotEmpty(s_null));
        System.out.println(StringUtils.isNotEmpty(s_not_null));
    }

}
