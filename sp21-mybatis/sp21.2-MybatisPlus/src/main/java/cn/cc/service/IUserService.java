package cn.cc.service;

import cn.cc.dto.MybatisPlusUser;

import java.util.List;

public interface IUserService {

    List<MybatisPlusUser> listUser(String name);
    List<MybatisPlusUser> selectCondition(MybatisPlusUser condition);
    List<MybatisPlusUser> listUserPages(String name, long current, long size);

}
