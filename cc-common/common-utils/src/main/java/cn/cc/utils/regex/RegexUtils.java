package cn.cc.utils.regex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author c.c.
 * @date 2020/12/10
 */
public class RegexUtils {

    /**
     * true 符合
     * false 不符合
     *
     * @param regex  正则
     * @param string 要检查的字符串
     * @return 检查整个输入字符串是否完全符合指定的正则表达式模式
     */
    public static boolean isMatches(String regex, String string) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        // 尝试根据模式匹配整个区域。
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 匹配str
     * 不带组
     *
     * @param regex
     * @param content
     * @return
     */
    public static String matcheStr(String regex, String content) {
        return matcheStr(regex, content, 0);
    }

    /**
     * 匹配str
     * 普通捕获组
     *
     * @param regex
     * @param content
     * @param group
     * @return
     */
    public static String matcheStr(String regex, String content, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        //是否匹配到了
        if (matcher.find()) {// 进入后可以全匹配
            return matcher.group(group);
        }
        return null;
    }

    /**
     * 命名捕获组
     * 正则编写好，可以直接匹配到需要的内容，不用多处理
     *
     * @param regex
     * @param content
     * @param group
     * @return
     */
    public static String matcheStr(String regex, String content, String group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        //是否匹配到了
        if (matcher.find()) {// 进入后可以全匹配
            return matcher.group(group);
        }
        return null;
    }

    /**
     * 匹配List
     *
     * @param regex
     * @param content
     * @return
     */
    public static List<String> matcheList(String regex, String content) {
        return matcheList(regex, content, 0);
    }

    /**
     * @param regex
     * @param content
     * @param group
     * @return
     */
    public static List<String> matcheList(String regex, String content, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        //是否匹配到了
        List<String> stringSet = new ArrayList<>();
        while (matcher.find()) {// 匹配出所有符合的
            stringSet.add(matcher.group(group));
            //System.out.println(matcher.group(group));
        }
        return stringSet;
    }

    public static Set<String> matcheList(String regex, String content, String group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        //是否匹配到了
        Set<String> stringSet = new HashSet<>();
        while (matcher.find()) {// 匹配出所有符合的
            stringSet.add(matcher.group(group));
            System.out.println(matcher.group(group));
        }
        return stringSet;
    }

    public static void main(String[] args) {
        String p = "^(?!正文).*?$";
        String p_2 = "^(正文)(.*?)$";
        String msg = "正文第一章";
        String msg_2 = "第一章";
        System.out.println(matcheStr(p, msg));
        System.out.println(matcheStr(p_2, msg, 0));
        System.out.println(matcheStr(p_2, msg, 1));
        System.out.println(matcheStr(p_2, msg, 2));
        System.out.println(matcheStr(p, msg_2));
    }

}

