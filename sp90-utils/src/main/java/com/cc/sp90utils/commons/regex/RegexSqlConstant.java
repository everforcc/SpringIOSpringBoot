package com.cc.sp90utils.commons.regex;

public class RegexSqlConstant {

    /**
     * 匹配update脚本 SET及之前的部分
     * update table_name set
     * update table_name tn set
     */
    public static String updatePattern = "UPDATE\\s+(\\w+)\\s+(\\w+\\s+)?SET";

}
