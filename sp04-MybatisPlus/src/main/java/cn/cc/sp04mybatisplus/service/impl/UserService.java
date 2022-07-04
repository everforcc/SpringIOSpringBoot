/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-30 10:56
 * Copyright
 */

package cn.cc.sp04mybatisplus.service.impl;

import cn.cc.sp04mybatisplus.dao.UserDao;
import cn.cc.sp04mybatisplus.dto.MybatisPlusUser;
import cn.cc.sp04mybatisplus.service.IUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    UserDao userDao;

    public List<MybatisPlusUser> listUser(String name){
        return userDao.listUser(name);
    }

    @Override
    public List<MybatisPlusUser> selectCondition(MybatisPlusUser condition) {
        Page<MybatisPlusUser> page = new Page<>(1, 10);
        userDao.selectCondition(condition, page);
        List<MybatisPlusUser> userList = page.getRecords();
        long size = page.getSize();
        long total = page.getTotal();
        long pages = page.getPages();
        long current = page.getCurrent();
        return userList;
    }

    @Override
    public List<MybatisPlusUser> listUserPages(String name, long currentPage, long sizePage) {
        Page<MybatisPlusUser> page = new Page<>(currentPage, sizePage);
        userDao.listUserPages(name, page);
        List<MybatisPlusUser> userList = page.getRecords();
        long size = page.getSize();
        long total = page.getTotal();
        long pages = page.getPages();
        long current = page.getCurrent();
        return userList;
    }

}
