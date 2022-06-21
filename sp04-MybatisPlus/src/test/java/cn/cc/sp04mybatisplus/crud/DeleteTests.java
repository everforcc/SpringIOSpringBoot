/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 15:46
 * Copyright
 */

package cn.cc.sp04mybatisplus.crud;

import cn.cc.sp04mybatisplus.dto.MybatisPlusUser;
import cn.cc.sp04mybatisplus.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class DeleteTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 物理删除
     * 根据id，批量id，对象删除
     */
    @Test
    void delete(){
        MybatisPlusUser user = new MybatisPlusUser();
        user.setId(1530027478620573697L);
        user.setAge(19);
        int insert = userMapper.deleteById(user);
        //userMapper.deleteBatchIds()
        //userMapper.deleteByMap()
        log.info("删除结果 {}",insert);
        log.info("删除对象 {}",user);
    }

    /**
     * 配置完之后就是逻辑删除
     */
    @Test
    void deleteLogic(){
        int insert = userMapper.deleteById(1l);
        log.info("删除结果 {}",insert);
    }


}
