/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-05-27 10:09
 * Copyright
 */

package cn.cc.sp04mybatisplus.codegenerate;

import lombok.Data;

@Data
public class CodeMsg {

    /**
     * 数据库信息
     */
    private String url;
    private String username;
    private String password;

    /**
     * 作者
     */
    private String author;
    /**
     * 输出目录
     */
    private String outPutDis;
    private String outPutXMLDis;
    /**
     * 包名
     */
    private String parent;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 过滤表前缀?
     */
    private String[] tablePrefix;

}
