package cn.cc.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface StockMapper {

    @Update("UPDATE cc_t_stock SET stocknum = stocknum+1 WHERE id = #{id}")
    int updateByIncrease(@Param("id") int id);

}
