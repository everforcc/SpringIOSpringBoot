package com.cc.sp90utils.utils.check;


import com.cc.sp90utils.utils.constant.LogConstant;

public class StringUtils {


    public static String urlSubFileName(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 空 不能有任何字符
     * @param val
     * @return
     */
    public static boolean isEmpty(String val){
       return org.apache.commons.lang3.StringUtils.isEmpty(val);
    }

    /**
     * 空字符串也能过
     * @param val
     * @return
     */
    public static boolean isBlank(String val){
        return org.apache.commons.lang3.StringUtils.isBlank(val);
    }

    /**
     * 如果 val 为null，替换为 target
     * @param val
     * @param target
     * @return
     */
    public static String defaultIfEmpty(String val,String target){
        return org.apache.commons.lang3.StringUtils.defaultString(val,target);
    }

    /**
     * 首字母大写
     * @param val
     * @return
     */
    public static String capitalize(String val){
        return org.apache.commons.lang3.StringUtils.capitalize(val);
    }


    /**
     * 移除指定的字符串，全部
     * @param val
     * @param remove
     * @return
     */
    public static String remove(String val,String remove){
        return org.apache.commons.lang3.StringUtils.remove(val,remove);
    }
    public static String removeStart(String val,String remove){
        return org.apache.commons.lang3.StringUtils.removeStart(val,remove);
    }
    public static String removeEnd(String val,String remove){
        return org.apache.commons.lang3.StringUtils.removeEnd(val,remove);
    }


    public static int countMatches(String val,String str){
        return org.apache.commons.lang3.StringUtils.countMatches(val,str);
    }

    public static void main(String[] args) {
        String a = null;
        String b = "";
        String c = " ";
        String d = "  ";
        String e = "  \r";
        String f = "  \n";
        String g = "  \t";
        String h = "  \t";
        String j = "aaa";
        String k = "abc";
        String l = "a";

        int i = 0;
        // true
        System.out.println(LogConstant.format(i++,!isEmpty(a)));
        // true
        System.out.println(LogConstant.format(i++,!isEmpty(b)));
        // false
        System.out.println(LogConstant.format(i++,!isEmpty(c)));
        System.out.println(LogConstant.SPLIT);
        System.out.println(LogConstant.format(i++,isBlank(d)));
        System.out.println(LogConstant.format(i++,isBlank(e)));
        System.out.println(LogConstant.format(i++,isBlank(f)));
        System.out.println(LogConstant.format(i++,isBlank(g)));
        System.out.println(LogConstant.format(i++,isBlank(h)));

        System.out.println(LogConstant.SPLIT);
        System.out.println(LogConstant.format(i++,defaultIfEmpty(a,"abc")));
        System.out.println(LogConstant.format(i++,defaultIfEmpty(b,"abc")));
        System.out.println(LogConstant.format(i++,defaultIfEmpty(c,"abc")));

        System.out.println(LogConstant.SPLIT);
        System.out.println(LogConstant.format(i++,capitalize(j)));
        System.out.println(LogConstant.format(i++,capitalize(k)));

        System.out.println(LogConstant.SPLIT);
        System.out.println(LogConstant.format(i++,remove(j,l)));
        System.out.println(LogConstant.format(i++,remove(k,l)));

        System.out.println(LogConstant.SPLIT);
        System.out.println(LogConstant.format(i++,removeStart(j,l)));
        System.out.println(LogConstant.format(i++,removeStart(k,l)));

        System.out.println(LogConstant.SPLIT);
        System.out.println(LogConstant.format(i++,removeEnd(j,l)));
        System.out.println(LogConstant.format(i++,removeEnd(k,l)));

        System.out.println(LogConstant.SPLIT);
        System.out.println(LogConstant.format(i++,countMatches(j,l)));

    }
}
