/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 15:26
 * Copyright
 */

package cn.cc.sp04mybatisplus.crud;

import cn.cc.sp04mybatisplus.dto.MybatisPlusUser;
import cn.cc.sp04mybatisplus.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 乐观锁配置
 */
@Slf4j
@SpringBootTest
public class OptimisticLockerTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testOptimisticLocker(){
        // User user = userMapper.selectById(7L);
        MybatisPlusUser user = new MybatisPlusUser();
        user.setId(7L);
        user.setVersion(2);
        user.setEmail("1527-email");
        int insert = userMapper.updateById(user);
        log.info("更新结果 {}",insert);
        log.info("更新对象 {}",user);
    }

    @Test
    public void testOptimisticLocker_2(){
        MybatisPlusUser user = userMapper.selectById(7L);
        user.setId(7L);
        user.setEmail("1527-1-email");

        MybatisPlusUser user2 = userMapper.selectById(7L);
        user2.setId(7L);
        user2.setEmail("1527-2-email");
        int insert2 = userMapper.updateById(user2);
        log.info("更新结果 {}",insert2);
        log.info("更新对象 {}",user2);


        int insert = userMapper.updateById(user);
        log.info("更新结果 {}",insert);
        log.info("更新对象 {}",user);
    }

}
