package cn.cc.oss.service;

import cn.cc.oss.model.SysOssObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public interface OssService {

    SysOssObject upload(String busi, String bucket, String objectKey, InputStream inputStream, String contentType);

    void download(String busi, String bucket, String objectKey, HttpServletRequest request, HttpServletResponse response) throws IOException;

    SysOssObject getOssObject(String bucket, String objectKey);

    void delete(String busi, String bucket, String objectKey);
}
