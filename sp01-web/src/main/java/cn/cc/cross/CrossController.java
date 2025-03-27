/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 17:55
 * Copyright
 */

package cn.cc.cross;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/open/cross")
@Validated
@Slf4j
public class CrossController {

    @CrossOrigin
    @GetMapping("/origin")
    public String origin() {
        log.info("测试跨域");
        return "1abc啊";
    }

}