package cn.cc.face.mapper;

import cn.cc.face.dao.FaceRecognitionLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface FaceRecognitionLogMapper {
    List<FaceRecognitionLog> selectAllOrderByRecognizedAtDesc();
    int insert(FaceRecognitionLog log);
} 