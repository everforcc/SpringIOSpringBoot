package com.cc.sp90utils.utils.httpclient;

import com.alibaba.fastjson.JSONObject;
import com.cc.sp90utils.utils.RandomUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;

import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.Map;

@Getter
@Setter
public class HttpParam {

    /**
     * 入库的话加个执行顺序，
     * 网站的请求流程 seq
     */

    @NotNull
    private String url;
    private String httpTypeEnum;
    private HttpHost proxy;
    private int timeout;
    private Charset charset = Charset.forName("UTF-8");

    private String cookie;
    private String token;

    private Map<String,String> headers;

    private String doinput ;
    private String output;
    private String isFile;

    private String content;

    private String requestParam;

    private int sleep;

    public HttpHost getProxy() {
        return proxy;
    }

    public void setProxy(String ip,int port) {
        //this.proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(ip,port));
        this.proxy = new HttpHost(ip,port);
    }

    public void sleep(){
        try {
            if(sleep==0){
                /**
                 * 如果没设置，最少延迟一秒，最多延迟三秒
                 */
                sleep = RandomUtils.randomInt(1,3) * 1000;
            }
            Thread.sleep(1);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
