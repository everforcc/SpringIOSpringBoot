package com.cc.sp90utils.constant;

import com.google.common.base.Strings;

public class LogConstant {

    public static String START = Strings.repeat("┬", 200);
    public static String END = Strings.repeat("┴", 200);
    public static String SPLIT = Strings.repeat("-", 10);

    public static String formatStr = "[%s]";

    public static String format(String str){
        return String.format(formatStr,str);
    }

    public static String format(Object str){
        return String.format(formatStr,str);
    }

    public static String format(Object... str){
        StringBuilder sb = new StringBuilder();
        for(Object obj:str){
            sb.append(format(obj));
        }
        return sb.toString();
    }

    public static String formatThread(Object... str){
        StringBuffer sb = new StringBuffer();
        for(Object obj:str){
            sb.append(format(obj));
        }
        return sb.toString();
    }

}
