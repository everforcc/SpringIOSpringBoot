package cn.cc.easyexcel.dynamic;

import lombok.Data;
import java.util.List;

@Data
public class TemplateDefinition {
    private String code;
    private String name;
    private List<TemplateField> fields;
}