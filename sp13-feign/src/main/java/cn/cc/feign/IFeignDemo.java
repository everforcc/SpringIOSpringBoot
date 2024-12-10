package cn.cc.feign;

import cn.cc.dto.ListDemo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "keytop", url = "localhost:8001/open")
public interface IFeignDemo {

    @GetMapping(value = "/feign/string")
    public String feignString();

    @GetMapping(value = "/feign/dto")
    public JSONObject feignJson();

    @GetMapping(value = "/feign/dto")
    public ListDemo feignDto();

}
