/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 15:45
 * Copyright
 */

package cn.cc.crud;

import cn.cc.dto.MybatisPlusUser;
import cn.cc.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class InsertTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 新增
     * ID 算法
     * 雪花算法
     */
    @Test
    void t_orderMapper_insert(){
        MybatisPlusUser user = new MybatisPlusUser();
        user.setAge(18);
        user.setEe("163@163.com");
        user.setName("用户名");
        int insert = userMapper.insert(user);
        log.info("插入结果 {}",insert);
        log.info("插入对象 {}",user);
    }

}
