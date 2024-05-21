package cn.cc.sp42velocity.utils;

import cn.cc.sp42velocity.dto.DemoTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
/**
 * @Description : 模板工具类
 * @Author : GKL
 * @Date: 2024-05-21 09:56
 */

/**
 * 模板工具类
 */
//@UtilityClass
public class VelocityUtils {

    /**
     * 初始化 Velocity 环境
     */
    public static void initVelocity() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("resource.loader.file.class",
                    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, StandardCharsets.UTF_8.toString());
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置批复模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareApprovalContext(DemoTemplate demoTemplate) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("time", demoTemplate.getTime());
        velocityContext.put("id", demoTemplate.getId());
        velocityContext.put("listDemo", demoTemplate.getListDemo());
        return velocityContext;
    }

    /**
     * 获取模板信息
     *
     * @param tplCategory 模版类型
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = new ArrayList<String>();
        if ("demo".equals(tplCategory)) {
            templates.add("vm/demo.vm");
        }
        return templates;
    }

    /**
     * 生成模板信息
     *
     * @param demoTemplate 模版数据
     * @return 模板信息
     */
    public static String getXmlByData(DemoTemplate demoTemplate) {
        // 初始化上下文
        VelocityUtils.initVelocity();
        // 获取模版变量
        VelocityContext context = VelocityUtils.prepareApprovalContext(demoTemplate);
        // 获取预制模版
        List<String> templates = VelocityUtils.getTemplateList("demo");

        StringWriter sw = new StringWriter();
        for (String template : templates) {
            // 渲染模板
            Template tpl = Velocity.getTemplate(template, StandardCharsets.UTF_8.toString());
            tpl.merge(context, sw);
        }
        return sw.toString();
    }
}