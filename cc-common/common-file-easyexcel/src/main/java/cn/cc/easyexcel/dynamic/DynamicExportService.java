package cn.cc.easyexcel.dynamic;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class DynamicExportService {

    public void export(OutputStream out, TemplateDefinition template, List<Map<String, Object>> rows) {
        // 1) 构建表头：List<List<String>>（支持多级表头，这里用一级标题）
        List<TemplateField> sorted = template.getFields().stream()
                .sorted(Comparator.comparingInt(TemplateField::getOrder))
                .collect(Collectors.toList());
        List<List<String>> head = sorted.stream()
                .map(f -> Collections.singletonList(f.getTitle()))
                .collect(Collectors.toList());

        // 2) 构建数据：与表头列顺序严格一致
        List<List<Object>> data = new ArrayList<>(rows.size());
        for (Map<String, Object> row : rows) {
            List<Object> line = new ArrayList<>(sorted.size());
            for (TemplateField f : sorted) {
                Object v = row.get(f.getField());
                // 可在此处应用字典转换/格式化
                line.add(v);
            }
            data.add(line);
        }

        // 3) 写出
        WriteSheet sheet = EasyExcel.writerSheet("导出").head(head).build();
        EasyExcel.write(out).sheet("导出名").head(head).doWrite(data);
    }
}
