package cn.cc.feign.controller;

import cn.cc.feign.dto.ListDemo;
import cn.cc.feign.util.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Description : 测试模板生成
 * @Author : GKL
 * @Date: 2024-05-21 10:08
 */
@Slf4j
@RequestMapping("/open/feign")
@RestController
public class FeignTestController {

    @GetMapping("/string")
    public String string() {
        log.info(".../feign/string");
        return "feign";
    }

    @GetMapping("/dto")
    public ListDemo dto() {
        log.info(".../feign/dto");
        ListDemo listDemo = new ListDemo();
        listDemo.setName("feign");
        return listDemo;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     */
    @PostMapping("/file")
    public void file(MultipartFile file) {
        log.info("...file start");
        FileUpload.uploadMultipartFile("http://127.0.0.1:8043/upload", file);
        log.info("...file end");
    }

    // 文件下载
    // org.apache.http.HttpClientsDownTest

}