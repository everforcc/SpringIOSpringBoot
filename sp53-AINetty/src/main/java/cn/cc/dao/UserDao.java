package cn.cc.dao;

import cn.cc.entity.User;
import java.util.List;

public interface UserDao {
    User findByAccount(String account);
    User findById(Long id);
    int insert(User user);
    int update(User user);
    List<User> findAll();
} 