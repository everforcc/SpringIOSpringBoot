package cn.cc.lkl.util;

public class CertUtil {

    /**
     * 将多行证书/密钥字符串转换为单行格式
     * 注意：此格式仅用于存储，不能直接用于Java证书解析
     * 支持的格式：CERTIFICATE, PRIVATE KEY, PUBLIC KEY, RSA PRIVATE KEY, RSA PUBLIC KEY等
     *
     * @param multiLineCert 多行证书/密钥字符串
     * @return 单行证书/密钥字符串
     */
    public static String convertToSingleLine(String multiLineCert) {
        if (multiLineCert == null) {
            return null;
        }

        // 移除所有换行符和回车符
        String singleLine = multiLineCert
                .replace("\n", "")  // 移除换行符
                .replace("\r", ""); // 移除回车符

        // 处理各种可能的BEGIN和END标记
        singleLine = processBeginEndMarkers(singleLine);

        return singleLine;
    }

    /**
     * 将单行证书/密钥字符串转换为标准格式（每64个字符一行）
     * 此格式可用于Java证书解析器
     * 支持的格式：CERTIFICATE, PRIVATE KEY, PUBLIC KEY, RSA PRIVATE KEY, RSA PUBLIC KEY等
     *
     * @param singleLineCert 单行证书/密钥字符串（通常来自数据库存储）
     * @return 标准格式的证书/密钥字符串（可用于解析）
     */
    public static String convertToStandardFormat(String singleLineCert) {
        if (singleLineCert == null) {
            return null;
        }

        // 检测证书/密钥类型
        String certType = detectCertType(singleLineCert);
        if (certType == null) {
            throw new IllegalArgumentException("无法识别的证书/密钥格式");
        }

        // 提取证书内容（去掉BEGIN和END标记）
        String certContent = singleLineCert
                .replace("-----BEGIN " + certType + "-----", "")
                .replace("-----END " + certType + "-----", "")
                .trim();

        // 每64个字符一行
        StringBuilder result = new StringBuilder();
        result.append("-----BEGIN ").append(certType).append("-----\n");

        for (int i = 0; i < certContent.length(); i += 64) {
            int endIndex = Math.min(i + 64, certContent.length());
            result.append(certContent.substring(i, endIndex)).append("\n");
        }

        result.append("-----END ").append(certType).append("-----\n");
        return result.toString();
    }

    /**
     * 处理各种BEGIN和END标记，确保它们与内容之间有空格
     *
     * @param singleLine 单行字符串
     * @return 处理后的字符串
     */
    private static String processBeginEndMarkers(String singleLine) {
        // 常见的证书和密钥类型
        String[] certTypes = {
            "CERTIFICATE",
            "PRIVATE KEY", 
            "PUBLIC KEY",
            "RSA PRIVATE KEY",
            "RSA PUBLIC KEY",
            "DSA PRIVATE KEY",
            "DSA PUBLIC KEY",
            "EC PRIVATE KEY",
            "EC PUBLIC KEY",
            "X509 CERTIFICATE"
        };

        for (String certType : certTypes) {
            String beginMarker = "-----BEGIN " + certType + "-----";
            String endMarker = "-----END " + certType + "-----";
            
            // 确保BEGIN标记后有空格
            singleLine = singleLine.replace(beginMarker, beginMarker + " ");
            // 确保END标记前有空格
            singleLine = singleLine.replace(endMarker, " " + endMarker);
        }

        return singleLine;
    }

    /**
     * 检测证书/密钥类型
     *
     * @param singleLineCert 单行证书/密钥字符串
     * @return 证书/密钥类型，如果无法识别则返回null
     */
    private static String detectCertType(String singleLineCert) {
        // 常见的证书和密钥类型
        String[] certTypes = {
            "CERTIFICATE",
            "PRIVATE KEY", 
            "PUBLIC KEY",
            "RSA PRIVATE KEY",
            "RSA PUBLIC KEY",
            "DSA PRIVATE KEY",
            "DSA PUBLIC KEY",
            "EC PRIVATE KEY",
            "EC PUBLIC KEY",
            "X509 CERTIFICATE"
        };

        for (String certType : certTypes) {
            String beginMarker = "-----BEGIN " + certType + "-----";
            if (singleLineCert.contains(beginMarker)) {
                return certType;
            }
        }

        return null;
    }
}
