package cn.cc.lkl.util;

import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LoadFileUtil {

    public static void main(String[] args) {
        String result = loadJsonFromResource("1.json");
        System.out.println(result);
    }

    public static String loadJsonFromResource(String fileName) {
        String path = "json/lkl/" + fileName;
        ClassLoader classLoader = LoadFileUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (inputStream == null) {
            throw new IllegalArgumentException("找不到资源文件: " + path);
        }
        try {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("读取文件失败: " + path, e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

}
