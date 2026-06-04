package cn.cc.oss;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class S3ClientTest {

    // 通用配置
    private final String region = "us-east-1";
    // 请根据您的实际服务器地址修改
    private final String serverBaseUrl = "http://192.168.3.109:8888";

    /**
     * 创建一个专用于特定业务(busi)的S3客户端的辅助方法。
     */
    private AmazonS3 createS3ClientForBusi(String busi) {
        String endpoint = serverBaseUrl + "/" + busi;
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .withClientConfiguration(new ClientConfiguration().withSocketTimeout(5000))
                .build();
    }

    @Test
    public void testMultiTenantUploadAndDownload() throws IOException {
        AmazonS3 busi1Client = createS3ClientForBusi("busi1");
        String busi1Bucket = "busi1-bucket3";
        String busi1Key = "reports/january.txt";
        String busi1Content = "Content for busi1";

        System.out.println("--- 正在测试 busi1 ---");
        busi1Client.putObject(busi1Bucket, busi1Key, busi1Content);
        System.out.println("为 busi1 上传成功。");

        S3Object busi1Object = busi1Client.getObject(busi1Bucket, busi1Key);
        String busi1DownloadedContent = readS3ObjectContent(busi1Object);
        assertEquals(busi1Content, busi1DownloadedContent);
        System.out.println("为 busi1 下载并验证成功。");

        AmazonS3 busi2Client = createS3ClientForBusi("busi2");
        String busi2Bucket = "busi2-bucket";
        String busi2Key = "logs/app.log";
        String busi2Content = "Log content for busi2";

        System.out.println("\n--- 正在测试 busi2 ---");
        busi2Client.putObject(busi2Bucket, busi2Key, busi2Content);
        System.out.println("为 busi2 上传成功。");

        S3Object busi2Object = busi2Client.getObject(busi2Bucket, busi2Key);
        String busi2DownloadedContent = readS3ObjectContent(busi2Object);
        assertEquals(busi2Content, busi2DownloadedContent);
        System.out.println("为 busi2 下载并验证成功。");

        System.out.println("\n--- 正在测试隔离性 ---");
        AmazonS3Exception exception = assertThrows(AmazonS3Exception.class, () -> busi1Client.getObject(busi2Bucket, busi2Key));
        assertEquals(404, exception.getStatusCode());
        System.out.println("隔离性测试通过：busi1 的客户端无法访问 busi2 的对象。");
    }

    @Test
    public void testUploadFromRealLocalFile() throws IOException {
        // --- 请将此路径修改为您本地真实存在的一个文件路径 ---
        Path sourcePath = Paths.get("C:/Users/Administrator/Pictures/logo.jpg");
        if (!Files.exists(sourcePath)) {
            System.out.println("测试跳过：源文件 " + sourcePath + " 不存在。");
            return;
        }

        String busi = "busi1";
        String bucket = "local-uploads";
        String objectKey = "images3/" + sourcePath.getFileName().toString();

        System.out.println("\n--- 正在测试从本地真实文件上传 ---");
        AmazonS3 s3Client = createS3ClientForBusi(busi);
        s3Client.putObject(bucket, objectKey, sourcePath.toFile());
        System.out.println("成功从本地文件上传到: " + String.join("/", busi, bucket, objectKey));

        // 验证：下载文件并与源文件进行字节级比较
        S3Object s3Object = s3Client.getObject(bucket, objectKey);
        byte[] sourceBytes = Files.readAllBytes(sourcePath);
        byte[] downloadedBytes = readS3ObjectBytes(s3Object);
        assertArrayEquals(sourceBytes, downloadedBytes, "下载的文件内容与本地源文件内容不一致！");
        System.out.println("验证成功：下载的文件内容与本地源文件内容完全一致。");
    }

    @Test
    public void testDownloadToFile(@TempDir Path tempDir) throws IOException {
        String busi = "busi1";
        String bucket = "local-uploads";
        String objectKey = "images2/logo.jpg";
        String content = "这是用于测试下载到文件的内容。";

        System.out.println("\n--- 正在测试下载到本地文件 ---");
        AmazonS3 s3Client = createS3ClientForBusi(busi);

        // 1. 先上传一个文件作为测试对象
//        s3Client.putObject(bucket, objectKey, content);
        System.out.println("已上传用于下载测试的文件。");

        // 2. 准备本地目标文件路径
        Path destinationPath = tempDir.resolve("C:/logo2.jpg");
        System.out.println("文件将被下载到: " + destinationPath);

        // 3. 执行下载到文件操作
        s3Client.getObject(new GetObjectRequest(bucket, objectKey), destinationPath.toFile());

        // 4. 验证下载下来的文件内容是否正确
        String downloadedContent = new String(Files.readAllBytes(destinationPath), StandardCharsets.UTF_8);
        assertEquals(content, downloadedContent, "下载到本地的文件内容与原始上传内容不一致！");
        System.out.println("验证成功：下载到本地的文件内容正确。");
    }

    @Test
    public void testDeleteObject() {
        String busi = "busi1";
        String bucket = "local-uploads";
        String objectKey = "images3/logo.jpg";
//        String content = "这个文件将被删除。";

        System.out.println("\n--- 正在测试删除功能 ---");
        AmazonS3 s3Client = createS3ClientForBusi(busi);

        // 1. 准备：先上传一个确定要删除的文件
//        s3Client.putObject(bucket, objectKey, content);
        System.out.println("已上传待删除的文件: " + objectKey);

        // 2. 执行：调用删除方法
        s3Client.deleteObject(bucket, objectKey);
        System.out.println("已执行删除操作。");

        // 3. 验证：尝试再次获取该文件，预期应抛出 404 Not Found 异常
        AmazonS3Exception exception = assertThrows(AmazonS3Exception.class, () -> {
            s3Client.getObject(bucket, objectKey);
        });
        assertEquals(404, exception.getStatusCode(), "删除后再次获取文件，预期应返回404状态码。");
        System.out.println("验证成功：删除后无法再次获取该对象，符合预期。");
    }

    private String readS3ObjectContent(S3Object s3Object) throws IOException {
        return new String(readS3ObjectBytes(s3Object), StandardCharsets.UTF_8);
    }

    private byte[] readS3ObjectBytes(S3Object s3Object) throws IOException {
        try (InputStream is = s3Object.getObjectContent()) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toByteArray();
        }
    }
}