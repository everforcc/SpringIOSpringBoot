package cn.cc.minio.service.impl;

import cn.cc.config.MinioConfig;
import cn.cc.minio.service.ISysFileService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

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
    @Override
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
    public void downFile(HttpServletResponse response, String path) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if (Objects.isNull(path)) {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                String data = "文件下载失败";
                OutputStream ps = response.getOutputStream();
                ps.write(data.getBytes(StandardCharsets.UTF_8));
                return;
            }
            outputStream = response.getOutputStream();

            // 检查桶是否存在
//            boolean res = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build());

            // 检查文件是否存在
//            StatObjectArgs statObjectArgs = StatObjectArgs.builder()
//                    .bucket(minioConfig.getBucketName())
//                    .object(path)
//                    .build();
//            try {
//                StatObjectResponse statObjectResponse = minioClient.statObject(statObjectArgs);
//                statObjectResponse.
//            }catch (MinioException minioException){
//
//            }
//            System.out.println("对象存在：" + objectExists);

            GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(minioConfig.getBucketName()).object(path).build();

            // 获取文件对象
            GetObjectResponse getObjectResponse = minioClient.getObject(getObjectArgs);

            inputStream = getObjectResponse;

            byte[] buf = new byte[1024];
            int length = 0;
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(path.substring(path.lastIndexOf("/") + 1), "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            // 输出文件
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            System.out.println("下载成功");
            inputStream.close();
        } catch (RuntimeException ex) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            try {
                OutputStream ps = response.getOutputStream();
                ps.write(data.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            ErrorResponse errorResponse = e.errorResponse();
            String code = errorResponse.code();
            String message = errorResponse.message();
            String bucketName = errorResponse.bucketName();
            String objectName = errorResponse.objectName();
            String resource = errorResponse.resource();
            String requestId = errorResponse.requestId();
            log.info("code: {}, message: {}, bucketName: {}, objectName: {}, resource: {}, requestId: {}",
                    code, message, bucketName, objectName, resource, requestId
            );
            // code: NoSuchKey, message: The specified key does not exist., bucketName: pro-test, objectName: 1736739944299百度.png1, resource: /pro-test/1736739944299百度.png1, requestId: 181A2E97A4994E96
            if ("NoSuchKey".equals(code)) {
                response.setContentType("application/json");
//                response.setHeader("Content-type", "text/html;charset=UTF-8");
                String data = "文件: " + objectName + " 不存在";
                OutputStream ps = null;
                try {
                    ps = response.getOutputStream();
                    ps.write(data.getBytes(StandardCharsets.UTF_8));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        String fileName = "D:\\2156.xlsx";
//        response.setContentType("application/octet-stream");
//        try {
//            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode("中文-2156-abc.xlsx", "UTF-8"));
//            OutputStream outputStream = response.getOutputStream();
//            outputStream.write(Files.readAllBytes(new File(fileName).toPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        log.info("下载文件: {}", path);
    }

    @Override
    public boolean removeFile(String path) {
        try {
            // 判断桶是否存在
            boolean res = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build());
            if (res) {
                // 删除文件
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioConfig.getBucketName())
                        .object(path).build());
            }
        } catch (Exception e) {
            log.info("删除文件失败");
            e.printStackTrace();
        }
        log.info("删除文件成功");
        return true;
    }

}