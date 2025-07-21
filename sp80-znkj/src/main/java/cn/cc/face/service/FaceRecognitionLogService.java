package cn.cc.face.service;

import cn.cc.face.dao.FaceRecognitionLog;
import cn.cc.face.mapper.FaceRecognitionLogMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FaceRecognitionLogService {
    @Resource
    private FaceRecognitionLogMapper logMapper;

    private static final Logger logger = LoggerFactory.getLogger(FaceRecognitionLogService.class);

    public List<FaceRecognitionLog> listAll() {
        logger.info("查询所有识别日志");
        List<FaceRecognitionLog> logs = logMapper.selectAllOrderByRecognizedAtDesc();
        for (FaceRecognitionLog log : logs) {
            log.setInputImagePath(convertToWebUrl(log.getInputImagePath()));
        }
        return logs;
    }
    public int add(FaceRecognitionLog log) {
        logger.info("新增识别日志: {}", log);
        return logMapper.insert(log);
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