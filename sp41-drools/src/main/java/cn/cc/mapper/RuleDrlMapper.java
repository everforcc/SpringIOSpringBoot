package cn.cc.mapper;

import cn.cc.dto.RuleDrlDto;
import org.apache.ibatis.annotations.Select;

/**
 * @Description : 从数据库加载规则
 * @Author : GKL
 * @Date: 2024-05-08 17:25
 */
public interface RuleDrlMapper {

    @Select("SELECT * FROM c_rule_drl where rule_id = #{id}")
    public RuleDrlDto selectRule(int id);

}
