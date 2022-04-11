package com.cc.sp90utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@SpringBootTest
class Sp90UtilsApplicationTests {

    @Test
    void contextLoads() {
        String a = "AB CD";
        String b = " ";

        try {
            System.out.println(URLEncoder.encode(a,"UTF-8"));
            System.out.println(URLEncoder.encode(a,"GB2312"));
            System.out.println(URLEncoder.encode(b,"UTF-8"));
            //URLEncoder.decode(a, "UTF-8")

            System.out.println(URLDecoder.decode("AB+CD","UTF-8"));
            System.out.println(URLDecoder.decode("AB%20CD","UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
