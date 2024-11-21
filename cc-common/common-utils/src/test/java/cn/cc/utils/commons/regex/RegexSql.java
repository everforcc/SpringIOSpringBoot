package cn.cc.utils.commons.regex;

import org.junit.Test;

public class RegexSql {

    //    Pattern updatePattern = Pattern.compile("UPDATE\\s+(\\w+)\\s+SET");
    // String updatePattern = "UPDATE\\s+(\\w+)\\s+SET";
    // 别名


    @Test
    public void testUpdate() {

        String sql_1 = "update mt_user set xxx".toUpperCase();
        System.out.println(sql_1);
        String sql_1_match = RegexUtils.matcheStr(RegexSqlConstant.updatePattern, sql_1);
        System.out.println("匹配sql_1: " + sql_1_match);
        String sql_1_match_replace = sql_1.replace(sql_1_match, sql_1_match + " up_count = 0 ");
        System.out.println("替换字符: " + sql_1_match_replace);

        String sql_2 = "update mt_user mu set xxx where a=a".toUpperCase();
        System.out.println(sql_2);
        String sql_2_match = RegexUtils.matcheStr(RegexSqlConstant.updatePattern, sql_2);
        System.out.println("匹配sql_2: " + sql_2_match);
        String sql_2_match_replace = sql_2.replace(sql_2_match, sql_2_match + " up_count = 0 ");
        System.out.println("替换字符: " + sql_2_match_replace);
        //System.out.println("未替换字符" + sql_2.substring(sql_2_match.length(), sql_2.length()));
        System.out.println("未替换字符" + sql_2.substring(sql_2_match.length()));
        System.out.println("替换字符+未替换字符: " + sql_2_match + " up_count = 0 " + sql_2.substring(sql_2_match.length(), sql_2.length()));

//        System.out.println(RegexUtils.isMatches("UPDATE\\s+", "UPDATE "));
//        System.out.println(RegexUtils.isMatches("\\w+", "MT_USER"));
//        System.out.println(RegexUtils.isMatches("\\s+SET", " SET XXX"));

    }

}
