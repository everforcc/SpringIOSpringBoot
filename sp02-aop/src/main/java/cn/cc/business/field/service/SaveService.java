package cn.cc.business.field.service;

import cn.cc.aop.annotation.ServiceField;
import cn.cc.business.field.dto.SaveDto;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ServiceField
public class SaveService {

    /**
     * 强制，所有save方法的第一个参数必须为要save的对象
     *
     * @return
     */
    public SaveDto save(SaveDto saveDto) {
        String str = JSONObject.toJSONString(saveDto);
        log.info(str);
        return saveDto;
    }

    public SaveDto save(SaveDto saveDto,int id) {
        String str = JSONObject.toJSONString(saveDto);
        log.info(str);
        return saveDto;
    }

}
