package cn.cc.sp50feign.feign;

import cn.cc.sp50feign.dto.ListDemo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "keytop", url = "localhost:8042/")
public interface IFeignDemo {

    @GetMapping(value = "/feign/string")
    public String feignString();

    @GetMapping(value = "/feign/dto")
    public JSONObject feignJson();

    @GetMapping(value = "/feign/dto")
    public ListDemo feignDto();

}
