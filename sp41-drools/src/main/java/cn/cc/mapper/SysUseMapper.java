package cn.cc.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @Description : 用户查询接口
 * @Author : GKL
 * @Date: 2024-04-30 10:55
 */
public interface SysUseMapper {

    @Select("SELECT nick_name FROM sys_user WHERE user_id = #{id };")
    public String selectAdmin(int id);

}
