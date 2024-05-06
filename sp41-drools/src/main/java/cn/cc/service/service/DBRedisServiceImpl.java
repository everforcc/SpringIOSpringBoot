package cn.cc.service.service;

import cn.cc.mapper.SysUseMapper;
import cn.cc.service.IDBRedisService;
import com.baomidou.dynamic.datasource.annotation.Master;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description : 测试数据库和缓存
 * @Author : GKL
 * @Date: 2024-04-30 11:28
 */
@Service
public class DBRedisServiceImpl implements IDBRedisService {

    @Resource
    SysUseMapper sysUseMapper;

    /**
     * 测试spring接管redis缓存
     *
     * @param id id
     * @return 返回值
     */
    @Master
    @Cacheable(cacheNames = "TEST_CACHE", key = "#id")
    @Override
    public String selectSysUser(int id) {
        return sysUseMapper.selectAdmin(id);
    }

}
