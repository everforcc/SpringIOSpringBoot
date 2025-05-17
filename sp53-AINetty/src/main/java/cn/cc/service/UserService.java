package cn.cc.service;

import cn.cc.entity.User;

public interface UserService {
    User login(String account, String password);
    boolean register(User user);
    User getById(Long id);
} 