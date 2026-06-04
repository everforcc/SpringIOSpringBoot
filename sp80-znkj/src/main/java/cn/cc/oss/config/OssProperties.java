package cn.cc.oss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    /**
     * A map from business identifier (busi) to the physical root path.
     * e.g., busi1 -> C:/tmp/oss/busi1
     */
    private Map<String, String> rootPath;

    public Map<String, String> getRootPath() {
        return rootPath;
    }

    public void setRootPath(Map<String, String> rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Gets the root path for a given business identifier.
     *
     * @param busi The business identifier.
     * @return The configured physical root path.
     * @throws IllegalArgumentException if the busi is not configured.
     */
    public String getPathForBusi(String busi) {
        String path = rootPath.get(busi);
        if (path == null) {
            throw new IllegalArgumentException("No OSS root path configured for business identifier: " + busi);
        }
        return path;
    }
}
