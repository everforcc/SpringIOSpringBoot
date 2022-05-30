package cn.cc.sp04mybatisplus.dao;

import cn.cc.sp04mybatisplus.dto.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("SELECT * FROM user WHERE NAME = #{name}")
    List<User> listUser(String name);

    /**
     * 在用sql的同时使用 mybatis-plus 分页插件
     * @param name
     * @param userPage
     * @return
     */
    @Select("SELECT * FROM user WHERE NAME = #{name}")
    Page<User> listUserPages(String name, Page<User> userPage);

}
