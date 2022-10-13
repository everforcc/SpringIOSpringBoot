<span  style="font-family: Simsun,serif; font-size: 17px; ">

1. pom
~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>2.2.8.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-openfeign-core</artifactId>
    <version>2.2.8.RELEASE</version>
</dependency>
~~~

2. application
~~~
@EnableFeignClients
public class OneForAllApplication 
~~~

3. 代码
~~~
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "bili",url = "https://api.vc.bilibili.com")
public interface FeignBilibiliApi {

@PostMapping(value = "/link_draw/v1/doc/upload_count?uid=%s", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
JSONObject getAlbumCount(@RequestParam("uid")String uid);
}

~~~

</span>