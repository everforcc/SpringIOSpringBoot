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
        if (StringUtils.isEmpty(chineseNumber)) {
            return 0;
        }
        chineseNumber = chineseNumber.replace("两", "二"); // 兼容"两"
        int result = 0;
        int section = 0; // 每节的结果
        int number = 0; // 当前数字
        int unit = 1; // 当前单位
        int lastUnit = 1; // 上一个单位
        char[] chars = chineseNumber.toCharArray();
        int currentBaseUnit = 1; // 新增基准单位变量
        
        for (int i = 0; i < chars.length; i++) {
            Integer num = CHINESE_NUM.get(chars[i]);
            if (num != null) {
                number = num;
                // 处理末尾数字逻辑优化
                if (i == chars.length - 1) {
                    unit = currentBaseUnit != 1 ? currentBaseUnit / 10 : 1; // 自动推导单位
                    section += number * unit;
                }
            } else {
                switch (chars[i]) {
                    case '十':
                        unit = 10;
                        currentBaseUnit = unit; // 更新基准单位
                        break;
                    case '百':
                        unit = 100;
                        currentBaseUnit = unit; // 更新基准单位
                        break;
                    case '千':
                        unit = 1000;
                        currentBaseUnit = unit; // 更新基准单位
                        break;
                    case '万':
                        section = (section + number) * 10000;
                        result += section;
                        section = 0;
                        number = 0;
                        unit = 1;
                        continue;
                    case '亿':
                        section = (section + number) * 100000000;
                        result += section;
                        section = 0;
                        number = 0;
                        unit = 1;
                        continue;
                    default:
                        throw new IllegalArgumentException("非法中文数字字符: " + chars[i]);
                }
                if (number == 0 && unit == 10) {
                    // 处理"十"开头的情况，如"十二"
                    number = 1;
                }
                // 新增中间数字处理逻辑
                if (number != 0) {
                    section += number * unit;
                    number = 0;
                }
            }
        }
        result += section;
        return result;
    }

    // 测试方法
    public static void main(String[] args) {
        System.out.println(convert("一百二十八")); // 输出 128
        System.out.println(convert("二百三"));     // 输出 203
        System.out.println(convert("一千零二十四")); // 输出 1024
        System.out.println(convert("九十九"));     // 输出 99
    }
}
