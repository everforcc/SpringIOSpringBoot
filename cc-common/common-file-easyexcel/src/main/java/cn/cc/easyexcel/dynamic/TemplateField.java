package cn.cc.easyexcel.dynamic;

import lombok.Data;

@Data
public class TemplateField {
    private String title;     // 展示标题，如“管理员”
    private String field;     // 字段编码，如“username”
    private String type;      // string/int/long/bool/date/...
    private Boolean required; // 是否必填
    private Integer order;    // 列顺序，越小越靠前
    private String regex;     // 正则校验，如手机号
    // 可扩展：dictCode、enumClass、dateFormat、defaultValue、converterBeanName 等
}
