package com.cc.sp03data.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TransactionalDao {

    @Insert("INSERT INTO cc_novel (`uuid`) VALUES ( #{uuid} )")
    int insertNovel(@Param("uuid") String uuid);

    @Insert({"UPDATE cc_novel SET `uuid` = #{uuid}",
            "WHERE `id` = #{id}"})
    int updateNovel(@Param("id") String id, @Param("uuid") String uuid);

}
