package cn.cc.service.impl;

import cn.cc.dao.UserDao;
import cn.cc.entity.User;
import cn.cc.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String account, String password) {
        User user = userDao.findByAccount(account);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean register(User user) {
        return userDao.insert(user) > 0;
    }

    @Override
    public User getById(Long id) {
        return userDao.findById(id);
    }
} 