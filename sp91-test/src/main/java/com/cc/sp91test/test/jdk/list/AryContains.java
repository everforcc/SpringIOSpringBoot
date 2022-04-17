package com.cc.sp91test.test.jdk.list;

import java.util.ArrayList;
import java.util.List;

public class AryContains {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("a");

        System.out.println(stringList.contains("a"));

        List<Obj> objList = new ArrayList<>();
        objList.add(new Obj("A"));
        System.out.println(objList.contains(new Obj("A")));

    }

    public static class Obj{

        public String str;

        public Obj(String str) {
            this.str = str;
        }

        @Override
        public boolean equals(Object obj) {
            Obj o = (Obj)obj;
            return this.str.equals(o.str);
        }
    }

}
