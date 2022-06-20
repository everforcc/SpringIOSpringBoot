/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-30 10:56
 * Copyright
 */

package cn.cc.sp04mybatisplus.service.impl;

import cn.cc.sp04mybatisplus.dao.UserDao;
import cn.cc.sp04mybatisplus.dto.User;
import cn.cc.sp04mybatisplus.service.IUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    UserDao userDao;

    public List<User> listUser(String name){
        return userDao.listUser(name);
    }

    @Override
    public List<User> listUserPages(String name, long currentPage, long sizePage) {
        Page<User> page = new Page<>(currentPage, sizePage);
        userDao.listUserPages(name, page);
        List<User> userList = page.getRecords();
        long size = page.getSize();
        long total = page.getTotal();
        long pages = page.getPages();
        long current = page.getCurrent();
        return userList;
    }

}
