package cn.cc.oss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class OssConfiguration {

    /**
     * Disable Spring's default multipart resolver to handle raw file streams.
     * This is crucial for large file uploads to prevent OOM errors.
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new MultipartResolver() {
            @Override
            public boolean isMultipart(HttpServletRequest request) {
                return false;
            }

            @Override
            public org.springframework.web.multipart.MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws org.springframework.web.multipart.MultipartException {
                throw new UnsupportedOperationException("Multipart processing is disabled");
            }

            @Override
            public void cleanupMultipart(org.springframework.web.multipart.MultipartHttpServletRequest request) {
                // No-op
            }
        };
    }
}