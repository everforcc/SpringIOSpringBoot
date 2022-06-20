/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 17:29
 * Copyright
 */

package cn.cc.sp04mybatisplus.crud;

import cn.cc.sp04mybatisplus.dto.User;
import cn.cc.sp04mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
public class SelectWrapperTests {

    @Resource
    private UserMapper userMapper;

    /**
     * Wrapper
     * 判断条件
     * 判断大于小于等条件
     */
    @Test
    public void selectWrapperEQ(){
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("id",2L).eq("name","Jack");
        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 大于
     */
    @Test
    public void selectWrapperGt(){
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.gt("id",1L);
        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 区间
     */
    @Test
    public void selectWrapperBetween(){
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.between("id", 1L, 5L);
        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    /**
     * like
     * not like
     * likeLeft
     * likeRight
     */
    @Test
    public void selectWrapperLike(){
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.like("name","o");
        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    /**
     * isNull
     * isNotNull
     */
    @Test
    public void selectWrapperNull(){
        QueryWrapper<User> wrapper = new QueryWrapper();
        //wrapper.isNull("");
        wrapper.isNotNull("name");
        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 结果排序
     */
    @Test
    public void selectOrder(){
        Page<User> page = new Page<>(1L,5L);
        QueryWrapper<User> wrapper = new QueryWrapper();
        //wrapper.groupBy("id");
        wrapper.orderByDesc("id");
        userMapper.selectPage(page,wrapper);
        page.getRecords().forEach(System.out::println);
    }

    /**
     * Having
     */
    @Test
    public void selectHaving(){
        Page<User> page = new Page<>(1L,5L);
        QueryWrapper<User> wrapper = new QueryWrapper();
        //wrapper.groupBy("id");
        wrapper.select("age", "COUNT(age)").groupBy("age").having("count(age) > {0}",1);
        userMapper.selectPage(page,wrapper);
        page.getRecords().forEach(System.out::println);
    }

}
