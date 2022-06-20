/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 11:33
 * Copyright
 */

package cn.cc.sp04mybatisplus.crud;

import cn.cc.sp04mybatisplus.dto.User;
import cn.cc.sp04mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class SelectTests {

    /**
     * 和sql脚本一样的关键字方法
     */

    @Resource
    private UserMapper userMapper;

    /**
     * 查询
     * 简单查询
     */
    @Test
    void select(){
//        List<User> userList = userMapper.selectList(null);
//        System.out.println(userList.size());
        User user = userMapper.selectById(1L);
        log.info("用户信息, {}", user);
        List<User> userList = userMapper.selectBatchIds(Arrays.asList(1L,2l,3L));
        log.info("用户数量, {}", userList.size());
    }

    /**
     * 通过map条件查询
     */
    @Test
    public void selectByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name", "Jone");
        List<User> userList = userMapper.selectByMap(map);
        userList.forEach(System.out::println);
    }

    /**
     * 分页插件
     * Page查询
     */
    @Test
    public void selectPages(){
        // 当前页和页面大小
        Page<User> page = new Page<>(1L,5L);
        Page<User> result = userMapper.selectPage(page, null);
        Long total = result.getTotal();
        Long size = result.getSize();

        log.info("total {}", total);
        log.info("size {}", size);
        List<User> records = result.getRecords();

        log.info("是否有下一页 {}", result.hasNext());
        log.info("是否有上一页 {}", result.hasPrevious());
        System.out.println("查询结果");
        records.forEach(System.out::println);
    }

    /**
     * Wrapper
     */
    @Test
    public void selectList(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
