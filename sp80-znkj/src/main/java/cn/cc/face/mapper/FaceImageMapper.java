package cn.cc.face.mapper;

import cn.cc.face.dao.FaceImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FaceImageMapper {
    List<FaceImage> selectAllOrderByCreatedAtDesc();
    FaceImage selectById(Integer id);
    int insert(FaceImage faceImage);
    int updatePersonName(@Param("id") Integer id, @Param("personName") String personName);
    int deleteById(Integer id);
} 