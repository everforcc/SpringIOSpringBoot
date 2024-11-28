package cn.cc.validuser.controller;

import cn.cc.core.domain.R;
import cn.cc.core.validateuser.annotation.EnumsValid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/validUser")
@Validated
@Slf4j
public class ValidUserController {

    /**
     * 自定义数据校验
     *
     * @param pageSize 页面大小
     * @return 页面大小
     */
    @GetMapping("/valid/{pageSize}")
    public R<Long> Valid(@EnumsValid @PathVariable("pageSize") Long pageSize) {
        log.info("日志打印pageSize: 【{}】", pageSize);
        return R.ok(pageSize);
    }

}
