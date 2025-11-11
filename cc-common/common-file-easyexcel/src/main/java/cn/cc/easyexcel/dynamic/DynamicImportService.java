package cn.cc.easyexcel.dynamic;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.StringUtils;

import java.io.InputStream;
import java.util.*;

public class DynamicImportService {

    public List<RowResult> importData(InputStream in, TemplateDefinition template) {
        List<RowResult> results = new ArrayList<>();
        Map<Integer, String> indexToTitle = new HashMap<>();
        List<TemplateField> fields = new ArrayList<>(template.getFields());
        fields.sort(Comparator.comparingInt(TemplateField::getOrder));

        AnalysisEventListener<Map<Integer, String>> listener = new AnalysisEventListener<Map<Integer, String>>() {
            private boolean headerParsed = false;

            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                // 读取第一行表头
                indexToTitle.clear();
                indexToTitle.putAll(headMap);
                headerParsed = true;
                // 可做表头一致性校验（标题、数量、顺序等）
                if(fields.size()!=headMap.size()){
                    throw new RuntimeException("表头数量有误");
                }
                // 检查表头内容是否一致
                for (int i = 0; i < fields.size(); i++) {
                    String title = indexToTitle.get(i);
                    if (!title.equals(fields.get(i).getTitle())) {
                        throw new RuntimeException("表头有误，请导出最新模板");
                        //  +  title + ":" + fields.get(i).getTitle()
                    }
                }
            }

            @Override
            public void invoke(Map<Integer, String> data, AnalysisContext context) {
                // 逐行读取数据
                RowResult rr = new RowResult();
                rr.rowIndex = context.readRowHolder().getRowIndex() + 1;

                Map<String, Object> mapped = new LinkedHashMap<>();
                List<String> errors = new ArrayList<>();

                for (Map.Entry<Integer, String> e : data.entrySet()) {
                    Integer colIndex = e.getKey();
                    String raw = e.getValue();
                    String title = indexToTitle.get(colIndex);
                    if (title == null) { continue; }

                    // 根据标题找到模板字段
                    TemplateField field = fields.stream()
                            .filter(f -> Objects.equals(f.getTitle(), title))
                            .findFirst().orElse(null);
                    if (field == null) { continue; }

                    // 基础转换与校验
                    Object value = convert(raw, field.getType());
                    List<String> err = validate(value, field);
                    if (!err.isEmpty()) { errors.addAll(err); }
                    mapped.put(field.getField(), value);
                }

                // 必填校验（模板中 required=true 的字段）
                for (TemplateField f : fields) {
                    if (Boolean.TRUE.equals(f.getRequired())) {
                        Object v = mapped.get(f.getField());
                        if (v == null || StringUtils.isBlank(String.valueOf(v))) {
                            errors.add("必填字段缺失：" + f.getTitle());
                        }
                    }
                }

                rr.ok = errors.isEmpty();
                rr.errors = errors;
                rr.data = mapped;
                results.add(rr);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        };

        EasyExcel.read(in, listener).headRowNumber(1).sheet().doRead();
        return results;
    }

    private Object convert(String raw, String type) {
        if (raw == null) {
            return null;
        }
        switch (type) {
            case "string": return raw.trim();
            case "int":
            case "integer": return StringUtils.isBlank(raw) ? null : Integer.valueOf(raw);
            case "long": return StringUtils.isBlank(raw) ? null : Long.valueOf(raw);
            case "bool":
            case "boolean": return "是".equals(raw) || "true".equalsIgnoreCase(raw);
            case "date": // 简化：按 yyyy-MM-dd，可扩展多格式
                try { return java.time.LocalDate.parse(raw); } catch (Exception ignore) { return raw; }
            default: return raw;
        }
    }

    private List<String> validate(Object v, TemplateField f) {
        List<String> errs = new ArrayList<>();
        if (v == null) {
            return errs;
        }
        if (f.getRegex() != null && v instanceof String) {
            if (!((String) v).matches(f.getRegex())) {
                errs.add("格式不正确：" + f.getTitle());
            }
        }
        // 可扩展：长度、范围、枚举、字典、跨字段校验等
        return errs;
    }

    public static class RowResult {
        public int rowIndex;
        public boolean ok;
        public Map<String, Object> data;
        public List<String> errors;
    }
}
