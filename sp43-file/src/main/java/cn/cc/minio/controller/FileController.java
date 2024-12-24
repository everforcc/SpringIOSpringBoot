package cn.cc.minio.controller;

import cn.cc.minio.service.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
public class FileController {

    @Autowired
    @Qualifier("minioSysFileServiceImpl")
    ISysFileService minioSysFileServiceImpl;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file) {
        log.info("上传文件");
        try {
            return minioSysFileServiceImpl.uploadFile(file);
        } catch (Exception e) {
            log.error("上传文件异常: {}", e.toString());
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/down")
    @ResponseBody
    public void down(HttpServletResponse response) {
        log.info("下载文件");
        minioSysFileServiceImpl.downFile(response, "");
    }

}
