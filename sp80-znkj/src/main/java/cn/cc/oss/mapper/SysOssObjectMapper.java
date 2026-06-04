package cn.cc.oss.mapper;

import cn.cc.oss.model.SysOssObject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SysOssObjectMapper {

    @Insert("INSERT INTO sys_oss_object (busi, bucket_name, object_key, physical_path, content_length, content_type, etag, create_time) " +
            "VALUES (#{busi}, #{bucket}, #{objectKey}, #{physicalPath}, #{contentLength}, #{contentType}, #{etag}, #{createTime})")
    void insert(SysOssObject ossObject);

    @Select("SELECT * FROM sys_oss_object WHERE busi = #{busi} AND bucket_name = #{bucket} AND object_key = #{objectKey}")
    SysOssObject findByBusiAndBucketAndObjectKey(@Param("busi") String busi, @Param("bucket") String bucket, @Param("objectKey") String objectKey);

    @Update("UPDATE sys_oss_object SET " +
            "physical_path = #{physicalPath}, " +
            "content_length = #{contentLength}, " +
            "content_type = #{contentType}, " +
            "etag = #{etag}, " +
            "create_time = #{createTime} " +
            "WHERE id = #{id}")
    void update(SysOssObject ossObject);

    @Delete("DELETE FROM sys_oss_object WHERE busi = #{busi} AND bucket_name = #{bucket} AND object_key = #{objectKey}")
    void delete(@Param("busi") String busi, @Param("bucket") String bucket, @Param("objectKey") String objectKey);
}