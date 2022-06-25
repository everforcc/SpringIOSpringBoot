package com.cc.sp90utils.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.util.List;

public class XSoupUtils {

    public static List<String> compileList(String pattern, Document document) {
        return Xsoup.compile(pattern).evaluate(document).list();
    }

    public static String compileStr(String pattern, Document document) {
        return Xsoup.compile(pattern).evaluate(document).get();
    }

    public static Document htmlToDocument(String html) {
        return Jsoup.parse(html);
    }

    public static String htmlToStr(String html, String pattern) {
        return Xsoup.compile(pattern).evaluate(Jsoup.parse(html)).get();
    }

    public static List<String> htmlToList(String html, String pattern) {
        return Xsoup.compile(pattern).evaluate(Jsoup.parse(html)).list();
    }

    public static void main(String[] args) {
        String h1 = "<h1>正文第一章牢狱之灾</h1>";
        String html = "<html><div>" +
                h1 +
                "</div></html>";
        //String jsoupHtml = Jsoup.parse(html).html();
        //System.out.println(jsoupHtml);
        // ### //*[@id=\"wrapper\"]/div[5]/div[1]/div[2]/h1//text()/regex('^(正文)(.*?)$', 2)
        // //div/h1/regex('<h1>(正文)(.*)</h1>', 2)
        String xpath = "//div/h1/regex('(.*?)(正文)(.*?)', 0)";
        System.out.println(htmlToStr(html, xpath));
    }

}
