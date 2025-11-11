package cn.cc.easyexcel.dynamic;

import com.alibaba.excel.util.FileUtils;

import java.io.*;
import java.util.*;

public class DynamicTest {

    public static void main(String[] args) {
        try {
//            excelImport();
            readFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String filePath = "D:\\cache\\test\\动态模板导出示例2.xlsx";
    public static TemplateDefinition tpl = new TemplateDefinition();

    static {
        tpl.setCode("user_import_v1");
        tpl.setName("用户导入模板V1");

        // 固定字段
        TemplateField f1 = new TemplateField();
        f1.setTitle("管理员");
        f1.setField("username");
        f1.setType("string");
        f1.setRequired(true);
        f1.setOrder(1);
        TemplateField f2 = new TemplateField();
        f2.setTitle("手机号");
        f2.setField("phone");
        f2.setType("string");
//        f2.setRequired(true);
        f2.setOrder(2);
        f2.setRegex("^1\\d{10}$");

        // 自定义字段
        TemplateField f3 = new TemplateField();
        f3.setTitle("自定义1");
        f3.setField("1234");
        f3.setType("string");
//        f2.setRequired(true);
        f3.setOrder(2);
//        f3.setRegex("^1\\d{10}$");

        tpl.setFields(Arrays.asList(f1, f2, f3));
    }

    public static void excelImport() throws IOException {
        // 构造模板（与《方案与示例.md》中一致）


// ========== 导出测试 ==========
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DynamicExportService exportService = new DynamicExportService();

        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> r1 = new HashMap<>();
        r1.put("username", "admin");
        r1.put("phone", "13800138000");
        r1.put("1234", "自定义的值");
        rows.add(r1);

        Map<String, Object> r2 = new HashMap<>();
        r2.put("username", "tom");
        r2.put("phone", "13900139000");
        r2.put("1234", "自定义的值");
        rows.add(r2);

        exportService.export(out, tpl, rows);
        byte[] excelBytes = out.toByteArray();
// 将 excelBytes 保存为文件以便查看
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(excelBytes);
        }
    }

    public static void readFile(String filePath) throws IOException {
        byte[] excelBytes = FileUtils.readFileToByteArray(new File(filePath));
        excelOut(excelBytes, tpl);
    }

    public static void readFile(byte[] excelBytes) throws IOException {
//        byte[] excelBytes = FileUtils.readFileToByteArray(new File(filePath));
        // 没有这个api byte[] excelBytes = FileUtils.readInputStreamToByteArray(is);

        excelOut(excelBytes, tpl);
    }

    public static void excelOut(byte[] excelBytes, TemplateDefinition tpl) throws IOException {
// ========== 导入测试 ==========
// 这里直接使用上一步导出的字节进行读取；实际项目中可来自上传文件 InputStream
        DynamicImportService importService = new DynamicImportService();
        try (ByteArrayInputStream in = new ByteArrayInputStream(excelBytes)) {
            List<DynamicImportService.RowResult> results = importService.importData(in, tpl);
            for (DynamicImportService.RowResult rr : results) {
                System.out.println("Row=" + rr.rowIndex + ", ok=" + rr.ok + ", data=" + rr.data + ", errors=" + rr.errors);
                Map<String, Object> data = rr.data;
                // 便利data
                for (Map.Entry<String, Object> e : data.entrySet()) {
                    System.out.println("  " + e.getKey() + "=" + e.getValue());
                }
            }

        }
    }

}
