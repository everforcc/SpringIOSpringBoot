package cn.cc.minio.controller;

import cn.cc.minio.service.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
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

    /**
     * http://43.140.221.95:9000/pro-test/1736739944299百度.png
     *
     * @param response 下载
     * @param path     文件位置
     */
    @GetMapping("/down")
    @ResponseBody
    public void down(HttpServletResponse response, @RequestParam("path") String path) {
        log.info("下载文件");
        minioSysFileServiceImpl.downFile(response, path);
    }

    @GetMapping("/remove")
    @ResponseBody
    public boolean remove(HttpServletResponse response, @RequestParam("path") String path) {
        log.info("下载文件");
        return minioSysFileServiceImpl.removeFile(path);
    }

}
