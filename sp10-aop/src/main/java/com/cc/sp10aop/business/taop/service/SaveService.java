package com.cc.sp10aop.business.taop.service;

import com.alibaba.fastjson.JSONObject;
import com.cc.sp10aop.common.dto.CommonFiledDto;
import com.cc.sp10aop.userinterface.ServiceAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ServiceAspect
public class SaveService {

    /**
     * 强制，所有save方法的第一个参数必须为要save的对象
     * @return
     */
    public String save(CommonFiledDto commonFiledDto){
      String str = JSONObject.toJSONString(commonFiledDto);
        log.info(str);
        return str;
    }

}
