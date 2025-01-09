package cn.cc.busi.lock.service;

import cn.cc.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class LockService {

    @Resource
    StockMapper stockMapper;

    public int updateIncrease(int id){
        int result = stockMapper.updateByIncrease(id);
        log.info("更新结果: {}", result);
        return result;
    }

}
