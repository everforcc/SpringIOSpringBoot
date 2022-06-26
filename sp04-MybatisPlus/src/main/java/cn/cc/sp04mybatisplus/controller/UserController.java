/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 08:49
 * Copyright
 */

package cn.cc.sp04mybatisplus.controller;

import cn.cc.sp04mybatisplus.dao.UserDao;
import cn.cc.sp04mybatisplus.dto.MybatisPlusUser;
import cn.cc.sp04mybatisplus.mapper.UserMapper;
import cn.cc.sp04mybatisplus.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plus")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserDao userDao;

    @Resource
    private IUserService userService;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @GetMapping("/tbase")
    public void tBase(){
        List<MybatisPlusUser> userList = userMapper.selectList(null);
        log.info("共有 {} 条数据", userList.size());
    }

    /**
     * 测试dao层用@select的查询
     */
    @GetMapping("/tDaoSelect/{name}")
    public void tDaoSelect(@PathVariable String name){
        List<MybatisPlusUser> userList = userDao.listUser(name);
        log.info("共有 {} 条数据", userList.size());
    }

    @GetMapping("/tDaoPage/{name}/{current}/{size}")
    public void tDaoPage(@PathVariable String name, @PathVariable Long current, @PathVariable Long size){
        List<MybatisPlusUser> userList = userService.listUserPages(name, current, size);
        log.info("共有 {} 条数据", userList.size());
    }

    @GetMapping("/tSqlSession")
    public void tSqlSession(){
        log.info("sqlSessionFactory: {}",sqlSessionFactory.getClass());
    }

}