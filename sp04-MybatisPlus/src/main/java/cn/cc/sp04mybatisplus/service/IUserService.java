package cn.cc.sp04mybatisplus.service;

import cn.cc.sp04mybatisplus.dto.User;

import java.util.List;

public interface IUserService {

    List<User> listUser(String name);

    List<User> listUserPages(String name, long current, long size);

}
