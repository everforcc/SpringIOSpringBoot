package cn.cc.sp31usercraw.busi;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author everforcc
 * @Date 2024-05-09 21:14
 * Copyright
 */
public class mdiyibanzhu {

    private static final String root = "";
    private static String urlBD = "https://www.baidu.com/";
    private static String url = "https://m.diyibanzhu.buzz/5/5318/all_1/";
    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";


    @Test
    public void flow() {
        try {
            Document document = Jsoup.connect(url).userAgent(userAgent)
                    .timeout(10000).get();
            Elements elements = document.getElementsByClass("chapters");

            for (Element element : elements) {
                Elements elements_a = element.getElementsByTag("a");
                for (Element element_a : elements_a) {
                    String href = element_a.attr("href");
                    String chapter = element_a.text();
                    System.out.println("href: " + href);
                    System.out.println("chapter: " + chapter);
                }
            }

            //System.out.println(document.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File file = new File("D:\\java\\temp\\3.txt");

    public static void down(String content) {
        try {
            System.out.println("下载一章");
            FileUtils.write(file, content, "UTF-8", true);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
