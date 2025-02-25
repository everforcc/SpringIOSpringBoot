package cn.cc.algo;

public class Comp {

    public static void main(String[] args) {
        System.out.println("计算机基础知识");
        String code = "765432100054";
        String a = code.substring(0, 7);
        System.out.println(a + "-" );
        String b = code.substring(7, 12);
        System.out.println("-" + b);
    }

}
