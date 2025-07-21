package cn.cc.face.service;

import cn.cc.face.dao.FaceImage;
import cn.cc.face.mapper.FaceImageMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FaceImageService {
    @Resource
    private FaceImageMapper faceImageMapper;

    private static final Logger logger = LoggerFactory.getLogger(FaceImageService.class);

    public List<FaceImage> listAll() {
        logger.info("查询所有人脸图片信息");
        List<FaceImage> images = faceImageMapper.selectAllOrderByCreatedAtDesc();
        for (FaceImage img : images) {
            img.setImagePath(convertToWebUrl(img.getImagePath()));
        }
        return images;
    }
    public FaceImage getById(Integer id) {
        logger.info("根据ID查询人脸图片: {}", id);
        return faceImageMapper.selectById(id);
    }
    public int add(FaceImage faceImage) {
        logger.info("新增人脸图片: {}", faceImage);
        return faceImageMapper.insert(faceImage);
    }
    public int updatePersonName(Integer id, String personName) {
        logger.info("更新人脸图片ID={}的姓名为: {}", id, personName);
        return faceImageMapper.updatePersonName(id, personName);
    }
    public int delete(Integer id) {
        logger.info("删除人脸图片ID={}", id);
        return faceImageMapper.deleteById(id);
    }

    private String convertToWebUrl(String localPath) {
        if (localPath == null) return null;
        String path = localPath.replace("\\", "/");
        String prefix = "D:/cache/BaiduSyncdisk/";
        if (path.startsWith(prefix)) {
            path = path.substring(prefix.length());
        }
        return "http://localhost:81/webdisk/" + path;
    }
} 