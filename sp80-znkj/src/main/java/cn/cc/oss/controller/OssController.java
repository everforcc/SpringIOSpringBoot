package cn.cc.oss.controller;

import cn.cc.oss.model.SysOssObject;
import cn.cc.oss.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 轻量级S3兼容协议的控制器层。
 * <p>
 * 负责接收和处理所有S3相关的HTTP请求，包括上传、下载和删除。
 * 本控制器实现了基于URL路径的多租户（业务）隔离方案。
 * URL结构为: `/{busi}/{bucket}/**`
 * - {busi}: 业务标识，用于映射到不同的物理存储根路径。
 * - {bucket}: 存储桶名称。
 * - **: 对象的完整键（objectKey），可以包含多级目录。
 */
@RestController
public class OssController {

    private static final Logger log = LoggerFactory.getLogger(OssController.class);

    /**
     * Spring的路径匹配器，用于从URL中精确提取通配符匹配的部分。
     */
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private OssService ossService;

    /**
     * 处理文件上传请求 (PUT Object)。
     *
     * @param busi    业务标识，从URL路径中动态获取。
     * @param bucket  存储桶名称，从URL路径中动态获取。
     * @param request HTTP请求对象，用于获取文件输入流和内容类型。
     * @return 包含ETag的HTTP 200 OK响应。
     * @throws IOException 如果读取输入流失败。
     */
    @PutMapping("/{busi}/{bucket}/**")
    public ResponseEntity<Void> upload(@PathVariable String busi, @PathVariable String bucket, HttpServletRequest request) throws IOException {
        // 从请求中精确地提取出对象的完整键(objectKey)
        String objectKey = getObjectKey(request);

        // 调用业务逻辑层执行文件上传
        SysOssObject ossObject = ossService.upload(
                busi,
                bucket,
                objectKey,
                request.getInputStream(),
                request.getContentType()
        );

        // 构造并打印可直接访问的文件URL，方便调试
        String accessUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(busi, bucket)
                .path(objectKey) // 使用 .path() 以正确处理objectKey中可能包含的'/'
                .build()
                .toUriString();
        log.info("文件上传成功! 业务: {}, 访问链接: {}", busi, accessUrl);

        // 根据S3协议，上传成功后在响应头中返回ETag
        HttpHeaders headers = new HttpHeaders();
        headers.add("ETag", ossObject.getEtag());

        return ResponseEntity.ok().headers(headers).build();
    }

    /**
     * 处理文件下载/访问请求 (GET Object)。
     * <p>
     * 支持浏览器直接预览（如图片、PDF）和强制下载。
     * 支持HTTP Range请求，实现分段下载和视频拖拽。
     *
     * @param busi     业务标识。
     * @param bucket   存储桶名称。
     * @param request  HTTP请求对象。
     * @param response HTTP响应对象，文件流将直接写入此响应。
     * @throws IOException 如果文件读写失败。
     */
    @GetMapping("/{busi}/{bucket}/**")
    public void download(@PathVariable String busi, @PathVariable String bucket, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String objectKey = getObjectKey(request);
        ossService.download(busi, bucket, objectKey, request, response);
    }

    /**
     * 处理文件删除请求 (DELETE Object)。
     *
     * @param busi    业务标识。
     * @param bucket  存储桶名称。
     * @param request HTTP请求对象。
     * @return 根据S3协议，删除成功返回HTTP 204 No Content。
     */
    @DeleteMapping("/{busi}/{bucket}/**")
    public ResponseEntity<Void> delete(@PathVariable String busi, @PathVariable String bucket, HttpServletRequest request) {
        String objectKey = getObjectKey(request);
        ossService.delete(busi, bucket, objectKey);

        log.info("文件删除成功: 业务={}, bucket={}, key={}", busi, bucket, objectKey);

        // 返回204 No Content表示服务器成功处理了请求，但没有返回任何内容。
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 使用AntPathMatcher从请求URI中精确地提取对象的完整键(objectKey)。
     * 这是从 `/**` 通配符中获取路径的健壮方法。
     *
     * @param request 传入的HTTP请求。
     * @return 提取出的对象键(objectKey)。
     */
    private String getObjectKey(HttpServletRequest request) {
        // 获取Spring MVC匹配到的URL模式，例如: "/{busi}/{bucket}/**"
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        // 获取当前请求的完整路径
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        // 使用路径匹配器，从完整路径中提取出通配符'**'匹配的部分
        return pathMatcher.extractPathWithinPattern(bestMatchPattern, path);
    }
}