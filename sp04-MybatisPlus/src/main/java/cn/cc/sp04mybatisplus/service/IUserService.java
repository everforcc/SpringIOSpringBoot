package cn.cc.sp04mybatisplus.service;

import cn.cc.sp04mybatisplus.dto.MybatisPlusUser;

import java.util.List;

public interface IUserService {

    List<MybatisPlusUser> listUser(String name);

    List<MybatisPlusUser> listUserPages(String name, long current, long size);

}
