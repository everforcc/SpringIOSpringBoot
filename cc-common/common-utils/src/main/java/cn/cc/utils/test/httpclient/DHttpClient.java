package cn.cc.utils.test.httpclient;

import cn.cc.utils.constant.CharsetsConstant;
import cn.cc.utils.constant.LogConstant;
import cn.cc.utils.commons.lang.RObjectsUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

/**
 * demo包下的代码都不要用
 */
@Deprecated
public class DHttpClient {

    public static void main(String[] args) {
        //ssl();
        //get();

        // 2.1 发送application/json类型的post请求,一般和其他系统对接也就是用这个了；
        // 2.2 form表单 发送application/x-www-form-urlencoded类型的post请求；
        //post();

        // 3. 发送multipart/form-data类型上传文件的post请求，需要一个依赖httpmime。
        upload();

        // 返回值处理
        //returnEnity();
    }

    public static void ssl() {
        CloseableHttpClient httpclient = null;
        FileInputStream instream = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File("d:\\tomcat.keystore"));

            // 加载keyStore d:\\tomcat.keystore
            trustStore.load(instream, "123456".toCharArray());

            // 相信自己的CA和所有自签名的证书
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            // 只允许使用TLSv1协议
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            // 创建http请求(get方式)
            HttpGet httpget = new HttpGet("https://localhost:81/b/");
            System.out.println("executing request" + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    System.out.println(EntityUtils.toString(entity));
                    EntityUtils.consume(entity);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(instream != null){
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

        }
    }

    /**
     * 简单get请求
     */
    public static void get(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet("https://www.baidu.com/");
        try {
            response = httpClient.execute(httpGet);

            System.out.println(response.getStatusLine());

            HttpEntity entity = response.getEntity();
            System.out.println("请求返回状态: " + response.getStatusLine().getStatusCode());
            if (entity != null) {
                System.out.println(LogConstant.SPLIT);
                System.out.println(LogConstant.SPLIT);
                System.out.println("Response content length: " + entity.getContentLength());
                System.out.println(EntityUtils.toString(entity));



                /**
                 * 释放资源
                 */
                EntityUtils.consume(entity);


                System.out.println(LogConstant.SPLIT);
                System.out.println(LogConstant.SPLIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            /**
             * 释放资源
             */
            if(RObjectsUtils.nonNull(response)){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (RObjectsUtils.nonNull(httpClient)) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 简单psot请求
     */
    public static void post(){

        // 1. 创建默认的httpClient实例.
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2. 创建httppost
        HttpPost httpPost = new HttpPost("http://localhost:81/open/auth/login");

        /**
         * 3.1 参数: form表单
         */
        UrlEncodedFormEntity urlEncodedFormEntity;
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username", "cc"));
        formparams.add(new BasicNameValuePair("password", "asdfasdf"));

        /**
         * 3.2 参数: string格式
         */
        HttpEntity reqEntity;

        CloseableHttpResponse response = null;
        try {
            urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, CharsetsConstant.UTF_8);
            reqEntity = new StringEntity("{\n" +
                    "\"username\": \"cc\",\n" +
                    "\"password\": \"asdfasdf\"\n" +
                    "}");

            // 4. 设置请求参数
            httpPost.setEntity(reqEntity);
            httpPost.addHeader("Content-Type","application/json");

            System.out.println("executing request " + httpPost.getURI());

            /**
             * 5. 拿到返回数据
             */
            response = httpClient.execute(httpPost);

            System.out.println("返回值状态: " + response.getStatusLine().getStatusCode());

            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                System.out.println(LogConstant.SPLIT);
                System.out.println("Response content: " + EntityUtils.toString(resEntity, "UTF-8"));
                System.out.println(LogConstant.SPLIT);

                EntityUtils.consume(resEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            /**
             * 释放资源
             */
            if(RObjectsUtils.nonNull(response)){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(RObjectsUtils.nonNull(httpClient)) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传文件
     */
    public static void upload() {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpPost httppost = new HttpPost("http://localhost:81/c/upload/write");
            httppost.addHeader("token","7a1620347169f7942aa59cda3a61eebd88d610630b9751a8445dfb4e69838707cdc8f7cf3e53cadddaf3014d75ecd5290a640d0167e2750cee4319a64c9a5acb2c21a45eac123acc3dca249efcc52963fdf16e7241593ef06bef4b326a6581904c1b4b85d1caec83b0714b7d9f63fa2bb96830488925f29b1e6953e84d9b217e791a56b96626d383711a1f00c46ee1e5a8da8308f07affca5336c064883ba7bcabce0f854b2adb8b98d34e873eb7ab56920e4aabf2b8051a9cb2e712bf53c0554bb9f6dc8058a895fa7ddcad1a3d671d3800ff1d51848e2230505f40261d5999f4dcee0febf0e2cef32328cf0152bd1ee25fdd2442967108e672b724487ffad508bcebb01ed57c13db95693a104ce896bed4c09307653f63d253537a32806a93975712196f55014b9787a6638aa2cf969efb7e5514ad73a07253676c42e1337747977deb79c77b66f06acd8385b71df03b04f41e0cb4a5e1bb3a628904653941b947bd92724889abc97a070b3203cf9ac56922f23227a995ecb68c567877e6979fab5b300266e4c2f25bc94a51b778d631063e58db88f0d230d5950071f97e33adba019ae0c45f9c5e5868ff613c1a671753b4d7408f1cd36cc473e7369a5578f2bb2941133f87040811f883700be01f25e207254adce9c0f10e6fbef3f90658");

            FileBody file = new FileBody(new File("E:\\test\\水晶蝶 - 情动 - 副本.mp3"));
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", file).build();//.addPart("comment", comment)

            httppost.setEntity(reqEntity);


            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    System.out.println("Response content: " + EntityUtils.toString(resEntity, "UTF-8"));
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void returnEnity(HttpEntity entity){
        try {
            /**
             * 处理字符串
             */
            String str = EntityUtils.toString(entity);
            /**
             * 处理为媒体文件
             */
            byte[] bytes = EntityUtils.toByteArray(entity);

            /**
             * 用完之后 释放资源
             */
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
