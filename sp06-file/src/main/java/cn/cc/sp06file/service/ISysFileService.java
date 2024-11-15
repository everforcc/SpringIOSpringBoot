package cn.cc.sp06file.service;

import org.springframework.web.multipart.MultipartFile;

public interface ISysFileService {

    /**
     * 上传文件
     *
     * @param file 文件流对象
     * @return 访问链接
     */
    String uploadFile(MultipartFile file) throws Exception;

    /**
     * 下载文件
     *
     * @param path 文件地址
     */
    void downFile(String path);

    /**
     * 删除文件
     *
     * @param path 文件地址
     * @return 删除结果
     */
    boolean removeFile(String path);

}
