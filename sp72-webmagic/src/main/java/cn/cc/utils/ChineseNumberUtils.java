package cn.cc.utils;

import com.alibaba.druid.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ChineseNumberUtils {

    private static final Map<Character, Integer> CHINESE_NUM = new HashMap<>();

    static {
        CHINESE_NUM.put('零', 0);
        CHINESE_NUM.put('一', 1);
        CHINESE_NUM.put('二', 2);
        CHINESE_NUM.put('三', 3);
        CHINESE_NUM.put('四', 4);
        CHINESE_NUM.put('五', 5);
        CHINESE_NUM.put('六', 6);
        CHINESE_NUM.put('七', 7);
        CHINESE_NUM.put('八', 8);
        CHINESE_NUM.put('九', 9);
        CHINESE_NUM.put('十', 10);
        CHINESE_NUM.put('百', 100);
        CHINESE_NUM.put('千', 1000);
    }

    public static int convert(String chineseNumber) {

        if(StringUtils.isEmpty(chineseNumber)){
            return 0;
        }

        int result = 0;
        int temp = 0;

        for (int i = 0; i < chineseNumber.length(); i++) {
            char c = chineseNumber.charAt(i);
            Integer num = CHINESE_NUM.get(c);

            if (num == null) {
                throw new IllegalArgumentException("非法中文数字字符: " + c);
            }

            if (num == 10 || num == 100 || num == 1000) {
                if (temp == 0) {
                    result += num;
                } else {
                    result += temp * num;
                    temp = 0;
                }
            } else {
                temp = num;
            }
        }

        return result + temp;
    }

    // 测试方法
    public static void main(String[] args) {
        System.out.println(convert("一百二十八")); // 输出 128
        System.out.println(convert("二百三"));     // 输出 203
        System.out.println(convert("一千零二十四")); // 输出 1024
        System.out.println(convert("九十九"));     // 输出 99
    }
}
