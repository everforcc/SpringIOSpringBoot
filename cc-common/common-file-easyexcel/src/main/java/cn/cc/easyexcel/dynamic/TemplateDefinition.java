package cn.cc.easyexcel.dynamic;

import lombok.Data;
import java.util.List;

@Data
public class TemplateDefinition {
    private String code;
    private String name;
    private List<TemplateField> fields;
    /**
     * 备注信息列表，将显示在第一行（固定内容）
     * 例如：["①组织机构格式: 一级组织-子组织", "②红色\"*\"为必填项;"]
     */
    private List<String> remarks;
}