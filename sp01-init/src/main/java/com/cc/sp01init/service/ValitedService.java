package com.cc.sp01init.service;


import com.cc.sp01init.i.ISave;
import com.cc.sp01init.pojo.Dog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Slf4j
@Service
@Validated
public class ValitedService {



    @Validated({ISave.class})
    public String valitDto(@Valid Dog dog){
        log.info("校验成功: " + dog.toString());
        return "check";
    }
}
