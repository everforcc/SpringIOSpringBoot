package cn.cc.config;

import cn.cc.lkl.sdk.config.LKLConfig;
import com.lkl.laop.sdk.LKLSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class LKLResConfig implements HandlerInterceptor {

    public static final String LKL_REQUEST_BODY = "LKL_REQUEST_BODY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取url path
        String path = request.getServletPath();
        log.info("path: {}", path);
        if(path.startsWith("/lkl")){
            messageHandle(request);
        }
        return true;
    }

    //拉卡拉开放平台采用数据流传输
    public ResponseEntity<?> messageHandle(HttpServletRequest request) throws Exception {
        // 1. 配置初始化-全局只需要初始化一次
        // 可以移动到全局初始化的地方
        LKLConfig.initSDK();
        //验签并解析请求
        String body = LKLSDK.notificationHandle(request);
        System.out.println("验签成功,请求body：" + body);
        // {"ecApplyId":1018886178482876416,"ecName":"特约商户支付服务合作协议V3.3","ecNo":"QY20250912522200065","ecStatus":"COMPLETED","orderNo":"d98e8f7928a44073986ca80d37a1232c","orgId":1,"version":"1.0"}
        // 验签成功,请求body：{"ecApplyId":1018887776751775744,"ecName":"特约商户支付服务合作协议V3.3","ecNo":"QY20250912526101505","ecStatus":"COMPLETED","orderNo":"687d065c2d444b56aa522f841cf850ae","orgId":1,"version":"1.0"}
        
        // 将解析后的body数据存储到request attribute中，供controller使用
        request.setAttribute(LKL_REQUEST_BODY, body);

        //spring-boot 3.x 使用jakarta.servlet.http.HttpServletRequest接收,读取内容,LKLSDK提供验签方法
//        LKLSDK.notificationHandle(getBody(request), getAuthorization(request));


        //业务处理

        return ResponseEntity.ok(body);
    }


    private String getAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


    private final String getBody(HttpServletRequest request) {
        try (InputStreamReader in = new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8)) {
            StringBuilder bf = new StringBuilder();
            int len;
            char[] chs = new char[1024];
            while ((len = in.read(chs)) != -1) {
                bf.append(new String(chs, 0, len));
            }
            return bf.toString();
        } catch (Exception e) {
            throw new RuntimeException("读取body失败");
        }


    }

}