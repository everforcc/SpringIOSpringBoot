/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 10:14
 * Copyright
 */

package cn.cc.sp04mybatisplus.codegenerate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

/**
 * 生成类
 */

public class GenerateCode {

    private static String url = "jdbc:mysql://180.76.156.43:3308/oneforall?characterEncoding=UTF-8&&serverTimezone=GMT&useSSL=false";
    private static String username = "cceverfor";
    private static String password = "5664c.c.";
    private static String author = "everforcc";
    private static String outputDir = "E:\\filesystem\\project\\SpringIOSpringBoot\\04-MybatisPlus";
    private static String outputXMLDir = "E:\\filesystem\\project\\SpringIOSpringBoot\\04-MybatisPlus";
    private static String parent = "cn.cc.sp04mybatisplus.codegenerate";
    private static String moduleName = "sp04-MybatisPlus";
    private static String tableName = "user";
    private static String[] tablePrefix = new String[]{"cc_"};

    public static void main(String[] args) {

        // 1. 设置初始化信息

        // 2. 设置数据库信息
        CodeMsg codeMsg = initCodeMsg();
        // 3. 运行
        generate(codeMsg);
    }


    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder(url, username, password);

    /**
     * 执行初始化数据库脚本
     */
    public static void before() throws SQLException {
        Connection conn = DATA_SOURCE_CONFIG.build().getConn();
        InputStream inputStream = GenerateCode.class.getResourceAsStream("/sql/init.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }

    public static CodeMsg initCodeMsg(){
        CodeMsg codeMsg = new CodeMsg();
        codeMsg.setUrl(url);
        codeMsg.setUsername(username);
        codeMsg.setPassword(password);
        codeMsg.setAuthor(author);
        codeMsg.setOutPutDis(outputDir);
        codeMsg.setOutPutXMLDis(outputXMLDir);
        codeMsg.setParent(parent);
        codeMsg.setModuleName(moduleName);
        codeMsg.setTableName(tableName);
        codeMsg.setTablePrefix(tablePrefix);
        return codeMsg;
    }

    public static void generate(CodeMsg codeMsg){
        FastAutoGenerator.create(codeMsg.getUrl(), codeMsg.getUsername(), codeMsg.getPassword())
                .globalConfig(builder -> {
                    builder.author(codeMsg.getAuthor()) // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir(codeMsg.getOutPutDis()); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(codeMsg.getParent()) // 设置父包名
                            //.moduleName(codeMsg.getModuleName()) // 设置父包模块名
                            //.pathInfo(Collections.singletonMap(OutputFile.xml, codeMsg.getOutPutXMLDis()))
                    ; // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(codeMsg.getTableName()) // 设置需要生成的表名
                            .addTablePrefix(codeMsg.getTablePrefix()); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}
