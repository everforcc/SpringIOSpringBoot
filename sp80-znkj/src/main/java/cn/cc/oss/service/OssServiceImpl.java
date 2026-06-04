package cn.cc.oss.service;

import cn.cc.oss.config.OssProperties;
import cn.cc.oss.mapper.SysOssObjectMapper;
import cn.cc.oss.model.SysOssObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * S3兼容协议的核心业务逻辑实现层。
 */
@Service
public class OssServiceImpl implements OssService {

    private static final Logger log = LoggerFactory.getLogger(OssServiceImpl.class);

    @Autowired
    private OssProperties ossProperties;

    @Autowired(required = false)
    private SysOssObjectMapper ossObjectMapper;

    /**
     * 定义一个列表，包含所有可以在浏览器中直接预览（inline）而不是强制下载（attachment）的文件类型前缀。
     */
    private static final List<String> PREVIEWABLE_TYPES = Arrays.asList(
            "image/", "text/", "application/pdf", "audio/", "video/"
    );

    @Override
    public SysOssObject upload(String busi, String bucket, String objectKey, InputStream inputStream, String contentType) {
        try {
            // 1. 根据业务标识(busi)从配置中动态获取该业务的物理存储根路径。
            String rootDir = ossProperties.getPathForBusi(busi);

            // 2. 构建物理存储路径。采用 /根路径/bucket/日期/objectKey 的结构。
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            String dateDir = sdf.format(new Date());
            Path physicalPath = Paths.get(rootDir, bucket).resolve(objectKey);
            // 确保所有父级目录都存在，如果不存在则自动创建。
            Files.createDirectories(physicalPath.getParent());

            // 3. 使用NIO和DigestInputStream流式写入磁盘，同时计算MD5。
            // 这种方式可以防止因文件过大导致的内存溢出（OOM）。
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream dis = new DigestInputStream(inputStream, md); // 包装输入流以计算哈希
                 ReadableByteChannel inChannel = Channels.newChannel(dis);
                 FileChannel outChannel = FileChannel.open(physicalPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

                ByteBuffer buffer = ByteBuffer.allocate(64 * 1024); // 64KB缓冲区
                while (inChannel.read(buffer) != -1) {
                    buffer.flip(); // 切换到读模式
                    outChannel.write(buffer);
                    buffer.compact(); // 为下一次写入做准备
                }
            }

            // 4. 完成文件写入后，获取最终的MD5哈希值，并格式化为S3 ETag标准格式（带双引号的32位小写hex）。
            byte[] md5hash = md.digest();
            StringBuilder etag = new StringBuilder("\"");
            for (byte b : md5hash) {
                etag.append(String.format("%02x", b));
            }
            etag.append("\"");

            long contentLength = Files.size(physicalPath);

            // 5. 将文件元数据保存或更新到数据库，实现覆盖上传逻辑。
            if (ossObjectMapper != null) {
                // 先根据业务、bucket和key查询记录是否存在
                SysOssObject existingObject = ossObjectMapper.findByBusiAndBucketAndObjectKey(busi, bucket, objectKey);
                if (existingObject == null) {
                    // 如果记录不存在，则创建新对象并执行插入操作。
                    SysOssObject newObject = new SysOssObject();
                    newObject.setBusi(busi);
                    newObject.setBucket(bucket);
                    newObject.setObjectKey(objectKey);
                    newObject.setPhysicalPath(physicalPath.toString().replace("\\", "/"));
                    newObject.setContentLength(contentLength);
                    newObject.setContentType(contentType);
                    newObject.setEtag(etag.toString());
                    newObject.setCreateTime(new Date());
                    ossObjectMapper.insert(newObject);
                    return newObject;
                } else {
                    // 如果记录已存在，则更新该记录的元数据。
                    existingObject.setPhysicalPath(physicalPath.toString().replace("\\", "/"));
                    existingObject.setContentLength(contentLength);
                    existingObject.setContentType(contentType);
                    existingObject.setEtag(etag.toString());
                    existingObject.setCreateTime(new Date());
                    ossObjectMapper.update(existingObject);
                    return existingObject;
                }
            }

            // 如果没有配置数据库，则只返回一个包含ETag的临时对象。
            SysOssObject tempObject = new SysOssObject();
            tempObject.setEtag(etag.toString());
            return tempObject;

        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("文件上传失败: busi={}, bucket={}, key={}", busi, bucket, objectKey, e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public void download(String busi, String bucket, String objectKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 关键：下载时必须同时使用busi, bucket, objectKey来确保只访问到正确的租户数据。
        SysOssObject ossObject = getOssObject(busi, bucket, objectKey);
        if (ossObject == null) {
            // 对象不存在，返回一个干净的404响应，没有响应体，以兼容S3客户端。
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path physicalPath = Paths.get(ossObject.getPhysicalPath());
        if (!Files.exists(physicalPath)) {
            // 数据库有记录但物理文件丢失，同样返回404。
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 设置S3标准响应头
        long totalSize = ossObject.getContentLength();
        String contentType = ossObject.getContentType();
        response.setContentType(contentType);
        response.setHeader("ETag", ossObject.getEtag());
        response.setHeader("Accept-Ranges", "bytes"); // 表明服务器支持范围请求

        // 根据文件类型决定是在线预览还是强制下载
        boolean isPreviewable = PREVIEWABLE_TYPES.stream().anyMatch(prefix -> contentType.startsWith(prefix));
        String dispositionType = isPreviewable ? "inline" : "attachment";

        // 正确设置Content-Disposition头，以RFC 5987标准兼容中文文件名。
        String fileName = Paths.get(objectKey).getFileName().toString();
        String headerValue = dispositionType + "; " +
                "filename=\"" + URLEncoder.encode(fileName, StandardCharsets.US_ASCII.name()) + "\"; " + // 兼容老浏览器的备用文件名
                "filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()).replace("+", "%20"); // 现代浏览器优先使用的标准文件名
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);

        // 检查是否为Range请求（分段下载）
        String rangeHeader = request.getHeader("Range");
        if (rangeHeader != null) {
            handleRangeDownload(rangeHeader, totalSize, physicalPath, response);
        } else {
            // 全量下载
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentLengthLong(totalSize);
            // 使用零拷贝(Zero-Copy)技术，高效传输文件，避免JVM内存占用。
            try (FileChannel fileChannel = FileChannel.open(physicalPath, StandardOpenOption.READ);
                 OutputStream out = response.getOutputStream()) {
                WritableByteChannel outChannel = Channels.newChannel(out);
                fileChannel.transferTo(0, totalSize, outChannel);
            }
        }
    }

    @Override
    public void delete(String busi, String bucket, String objectKey) {
        if (ossObjectMapper == null) {
            log.warn("数据库未配置，无法执行删除操作。");
            return;
        }

        // 关键：删除时也必须同时使用busi, bucket, objectKey来确保只删除正确的租户数据。
        SysOssObject ossObject = getOssObject(busi, bucket, objectKey);
        if (ossObject == null) {
            // 对象元数据不存在，视为删除成功，直接返回。
            return;
        }

        try {
            // 1. 删除物理文件
            Path physicalPath = Paths.get(ossObject.getPhysicalPath());
            if (Files.exists(physicalPath)) {
                Files.delete(physicalPath);
                log.info("成功删除物理文件: {}", physicalPath);
            }
            // 2. 删除数据库记录
            ossObjectMapper.delete(busi, bucket, objectKey);
            log.info("成功删除数据库记录: busi={}, bucket={}, key={}", busi, bucket, objectKey);
        } catch (IOException e) {
            log.error("删除文件失败: busi={}, bucket={}, key={}", busi, bucket, objectKey, e);
            throw new RuntimeException("文件删除失败", e);
        }
    }

    /**
     * 处理HTTP Range请求，实现分段下载。
     */
    private void handleRangeDownload(String rangeHeader, long totalSize, Path physicalPath, HttpServletResponse response) throws IOException {
        String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
        long start = Long.parseLong(ranges[0]);
        long end = ranges.length > 1 && !ranges[1].isEmpty() ? Long.parseLong(ranges[1]) : totalSize - 1;

        // 校验Range范围的合法性
        if (start > end || start < 0 || end >= totalSize) {
            response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            response.setHeader("Content-Range", "bytes */" + totalSize);
            return;
        }

        long length = end - start + 1;
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206状态码表示部分内容
        response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + totalSize);
        response.setContentLengthLong(length);

        // 使用零拷贝技术传输指定范围的文件片段
        try (FileChannel fileChannel = FileChannel.open(physicalPath, StandardOpenOption.READ);
             OutputStream out = response.getOutputStream()) {
            WritableByteChannel outChannel = Channels.newChannel(out);
            fileChannel.transferTo(start, length, outChannel);
        }
    }

    /**
     * 根据业务、bucket和key从数据库获取对象元数据。
     * 这是确保数据隔离的核心私有方法。
     */
    private SysOssObject getOssObject(String busi, String bucket, String objectKey) {
        if (ossObjectMapper != null) {
            return ossObjectMapper.findByBusiAndBucketAndObjectKey(busi, bucket, objectKey);
        }
        return null;
    }

    /**
     * @deprecated 此方法已废弃，因为它缺少业务标识(busi)，无法保证数据隔离。请使用 getOssObject(busi, bucket, objectKey)。
     */
    @Override
    @Deprecated
    public SysOssObject getOssObject(String bucket, String objectKey) {
        throw new UnsupportedOperationException("This method is deprecated. Use the version with 'busi' identifier.");
    }
}