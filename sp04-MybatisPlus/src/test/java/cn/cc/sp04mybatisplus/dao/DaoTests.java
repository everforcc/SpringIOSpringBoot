/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-30 10:52
 * Copyright
 */

package cn.cc.sp04mybatisplus.dao;

import cn.cc.sp04mybatisplus.dto.User;
import cn.cc.sp04mybatisplus.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
public class DaoTests {

//    @Autowired
//    UserDao userDao;

    @Autowired
    IUserService userService;

    @Test
    void select(){
        List<User> userList = userService.listUser("张三");
        log.info("userList, {}",userList.size());
    }

    @Test
    void page(){
        List<User> userList = userService.listUserPages("张三", 1L, 1L);
        log.info("userList, {}",userList.size());
    }

}
