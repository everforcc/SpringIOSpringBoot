package cn.cc.sp04mybatisplus.dao;

import cn.cc.sp04mybatisplus.dto.MybatisPlusUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("SELECT * FROM user WHERE NAME = #{name}")
    List<MybatisPlusUser> listUser(String name);

    /**
     * 在用sql的同时使用 mybatis-plus 分页插件
     * @param name
     * @param userPage
     * @return
     */
    @Select("SELECT * FROM user WHERE NAME = #{name}")
    Page<MybatisPlusUser> listUserPages(String name, Page<MybatisPlusUser> userPage);

    /**
     *
     * 处理复杂sql
     */
    Page<MybatisPlusUser> selectCondition();

    /**
     * 多行脚本用数组
     * 新增用户
     */
    @Insert({"INSERT INTO USER ",
            "(id, NAME, age, email, VERSION, deleted, create_time, update_time)",
            "VALUES",
            "(#{id}, #{name}, #{age}, #{email}, #{version}, #{deleted}, #{create_time}, #{update_time})"})
    int insert(MybatisPlusUser user);
}
