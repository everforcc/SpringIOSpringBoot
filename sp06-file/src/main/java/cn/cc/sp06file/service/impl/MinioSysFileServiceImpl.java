package cn.cc.sp06file.service.impl;

import cn.cc.config.MinioConfig;
import cn.cc.sp06file.service.ISysFileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class MinioSysFileServiceImpl implements ISysFileService {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient minioClient;

    /**
     * 可以按照分类日期来存放文件
     * 文件避免重名可以用uuid.ext 或者时间
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        log.info("上传文件: {}", fileName);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        String viewUrl = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
        log.info("文件访问地址: {}", viewUrl);
        return viewUrl;
    }

    @Override
    public void downFile(String path) {
        log.info("下载文件: {}", path);
    }

    @Override
    public boolean removeFile(String path) {
        log.info("删除文件: {}", path);
        return false;
    }

}