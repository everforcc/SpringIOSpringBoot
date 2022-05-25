package com.cc.sp90utils.jsoup;

import com.cc.sp90utils.enums.CharsetsEnum;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author c.c.
 * @date 2020/12/3
 */
public class JsoupUtils {

    /**
     * Jsoup 还是用来解析数据好用点,发请求的话有很多限制
     * 也不知道是我没找好api
     */

    public static void main(String[] args) {
        String url = "";
        connect(url, CharsetsEnum.UTF_8.charset.toString(),null);
    }

    /**
     *  请求地址，请求头，编码
     * @param targetUrl url
     * @param charset 编码
     * @param map 请求头
     */
    public static void connect(String targetUrl, String charset, Map<String,String> map){
        connect(targetUrl,charset,map,null);
    }

    public static void connect(String targetUrl, String charset, Map<String,String> map,Map<String,String> data){
        // 带字符编码
        try {
            Connection connection = Jsoup.connect(targetUrl);
            //connection.timeout()
            /*.userAgent(userAgent).cookie("cookie",cookie);*/// .data("","");
            // 下面这两个一样，还有疑问
            // 封装请求头
            if(map!=null&&map.size()>0){
                for(Map.Entry entry : map.entrySet()){
                    connection = connection.data((String) entry.getKey(), (String) entry.getValue());
                }
            }
            // 封装请求数据
            if(data!=null&&data.size()>0){
                for(Map.Entry entry : data.entrySet()){
                    connection = connection.data((String) entry.getKey(), (String) entry.getValue());
                }
            }

            Connection.Response response = connection.execute().charset(charset);
            //Connection.Response response = connection.response().charset("");

            // 获取相应
            //String response = connection.execute().body(); // 上面的没法设置编码
            System.out.println(response.body());
            /*String str = new String(response.getBytes(charset));
            System.out.println(str);*/
            // 拿到返回数据后，需要自己去取出数据

        } catch (Exception e) {
            // 如果异常，就再次发起请求
            //sentToUrlRequestConnection(urlwww,charset,set);
            e.printStackTrace();
        }
    }

    /*private static void connect(Connection connection,String charset) throws IOException {
        Connection.Response response = connection.execute().charset(charset);
        //Connection.Response response = connection.response().charset("");

        // 获取相应
        //String response = connection.execute().body(); // 上面的没法设置编码
        System.out.println(response.body());
            *//*String str = new String(response.getBytes(charset));
            System.out.println(str);*//*
        // 拿到返回数据后，需要自己去取出数据
    }*/

    // ERROR
    private static void sentToUrlRequestHttpURLConnection(String urlwww, String charset, Map<String,String> map){
        // 带字符编码
        try {
            // 好像不能强转HttpURLConnection
            Connection connection = Jsoup.connect(urlwww);
                                         /*.userAgent(userAgent)
                                         .cookie("cookie",cookie);
                                         */// .data("","");
            /*String response = HttpURLConnectionUtil.sendToUrlRequest(urlwww,"GET",charset,map);
            // 获取相应
            System.out.println(response);*/
//            String str = new String(response.getBytes(charset));
            // 拿到返回数据后，需要自己去取出数据

        } catch (Exception e) {
            //sentToUrlRequestHttpURLConnection(urlwww,charset,map);
            e.printStackTrace();
        }

    }

    // 解析数据方法总结 但是再细一点的话还是需要正则表达式
    private static void method(String urlwww){
        try {

            Connection connection = Jsoup.connect(urlwww);
            connection = connection.userAgent("").cookie("cookie","").data("","");
            String response = connection.execute().body();

            // 用正则处理数据，或者把返回放到下面，用Document来处理数据

            /********************************/
            // 一共有八个构造，需要的话在看具体要哪个
            Document document = Jsoup.parse("html");

            // class , id ,
            Elements elements = document.getElementsByClass(".class"); // getElements
            document.getElementsMatchingText("regex");
            document.getElementsMatchingText(Pattern.compile("regex"));

            Element element = elements.get(0);
            element.text(); // 可以取出中间的所有文字，但是没有换行，没有
            //

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
