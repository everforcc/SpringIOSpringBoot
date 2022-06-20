/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 15:45
 * Copyright
 */

package cn.cc.sp04mybatisplus.crud;

import cn.cc.sp04mybatisplus.dto.User;
import cn.cc.sp04mybatisplus.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class UpdateTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 更新
     */
    @Test
    void t_orderMapper_update(){
        User user = new User();
        user.setId(7L);
        user.setAge(11);

        int insert = userMapper.updateById(user);

        // Wrapper<T> updateWrapper
        // userMapper.update(user);

        log.info("更新结果 {}",insert);
        log.info("更新对象 {}",user);
    }

}
