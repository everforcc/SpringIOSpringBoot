package org.apache.http;

import cn.cc.feign.util.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class HttpClientsMultipartFileTest {

    @Test
    public void upload() {
        MultipartFile file = null;
        FileUpload.uploadMultipartFile("http://127.0.0.1:8043/upload", null);
    }


}
