package cn.cc.jdk;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Jdk {

    public static void main(String[] args) {

        try {
            System.out.println("jdk------");
//            Desktop.getDesktop().open(new File("ftp:127.0.0.1"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
