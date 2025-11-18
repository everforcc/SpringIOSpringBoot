package cn.cc.easyexcel.dynamic;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 动态导出测试类
 * 测试第一行固定备注、红色星号、单元格合并等功能
 */
public class DynamicTestColor {

    public static String filePath = "D:\\cache\\test\\动态模板导出-带颜色-示例37.xlsx";
    @Test
    public void main() {
        try {
            testExport();
            System.out.println("测试完成！文件已生成：" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void testExport() throws IOException {
        System.out.println("========================================");
        System.out.println("开始测试动态Excel导出（带红色星号）");
        System.out.println("========================================");
        
        // 1. 创建模板定义
        TemplateDefinition template = new TemplateDefinition();
        template.setCode("personnel_template");
        template.setName("人员管理下载模板");
        System.out.println("[TEST] 模板定义创建完成: " + template.getName());

        // 2. 设置备注信息（将显示在第一行，自动合并所有列）
        template.setRemarks(Arrays.asList(
                "备注:",
                "①组织机构格式: 一级组织-子组织",
                "②红色\"*\"为必填项;"
        ));
        System.out.println("[TEST] 备注信息设置: " + template.getRemarks());

        // 3. 定义字段（按照截图顺序）
        List<TemplateField> fields = new ArrayList<>();

        // 房屋位置
        TemplateField field1 = new TemplateField();
        field1.setTitle("房屋位置");
        field1.setField("houseLocation");
        field1.setType("string");
        field1.setRequired(false);
        field1.setOrder(1);
        fields.add(field1);

        // 组织机构
        TemplateField field2 = new TemplateField();
        field2.setTitle("组织机构");
        field2.setField("organization");
        field2.setType("string");
        field2.setRequired(false);
        field2.setOrder(2);
        fields.add(field2);

        // *管理员（必填，红色星号）
        TemplateField field3 = new TemplateField();
        field3.setTitle("*管理员");
        field3.setField("admin");
        field3.setType("string");
        field3.setRequired(true);  // 必填字段
        field3.setOrder(3);
        fields.add(field3);
        System.out.println("[TEST] 添加必填字段: " + field3.getTitle() + " (required=" + field3.getRequired() + ")");

        // *手机号（必填，红色星号）
        TemplateField field4 = new TemplateField();
        field4.setTitle("*手机号");
        field4.setField("mobile");
        field4.setType("string");
        field4.setRequired(true);  // 必填字段
        field4.setOrder(4);
        field4.setRegex("^1[3-9]\\d{9}$"); // 手机号正则
        fields.add(field4);
        System.out.println("[TEST] 添加必填字段: " + field4.getTitle() + " (required=" + field4.getRequired() + ")");

        template.setFields(fields);
        System.out.println("[TEST] 字段总数: " + fields.size());

        // 4. 准备测试数据（可选，用于填充示例数据）
        List<Map<String, Object>> dataList = new ArrayList<>();
        
        // 添加几行示例数据
        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("houseLocation", "**A栋101");
        row1.put("organization", "**技术部-开发组");
        row1.put("admin", "**张三");
        row1.put("mobile", "13800138000");
        dataList.add(row1);

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("houseLocation", "**B栋205");
        row2.put("organization", "**市场部-销售组");
        row2.put("admin", "**李四");
        row2.put("mobile", "13900139000");
        dataList.add(row2);

        // 也可以添加空行（用于模板下载）
        // 如果只需要模板，可以传入空列表：dataList = new ArrayList<>();

        // 5. 执行导出
        System.out.println("[TEST] 开始执行导出...");
        System.out.println("[TEST] 输出文件路径: " + filePath);
        
        DynamicExportService exportService = new DynamicExportService();
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            exportService.export(out, template, dataList);
            System.out.println("[TEST] 导出完成，文件已写入");
        }

        System.out.println("========================================");
        System.out.println("导出成功！");
        System.out.println("字段数量: " + fields.size());
        System.out.println("数据行数: " + dataList.size());
        System.out.println("备注信息: " + template.getRemarks());
        System.out.println("文件路径: " + filePath);
        System.out.println("========================================");
        System.out.println("请检查生成的Excel文件，确认红色星号是否正确显示");
        System.out.println("如果星号仍然是黑色，请查看上方的DEBUG日志以定位问题");
    }
}
