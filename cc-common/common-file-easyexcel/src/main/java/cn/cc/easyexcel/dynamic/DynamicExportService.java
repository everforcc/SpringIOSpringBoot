package cn.cc.easyexcel.dynamic;

import com.alibaba.excel.EasyExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCell;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STCellType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class DynamicExportService {

    public void export(OutputStream out, TemplateDefinition template, List<Map<String, Object>> rows) throws IOException {
        // 1) 构建表头：List<List<String>>（支持多级表头）
        List<TemplateField> sorted = template.getFields().stream()
                .sorted(Comparator.comparingInt(f -> f.getOrder() != null ? f.getOrder() : Integer.MAX_VALUE))
                .collect(Collectors.toList());
        
        // 构建表头结构
        // EasyExcel 的表头结构：List<List<String>>，每个内层 List 代表一列，可以有多行
        // 如果有备注，使用两级表头：第一级是备注（合并所有列），第二级是字段标题
        List<List<String>> head = new ArrayList<>();
        boolean hasRemarks = template.getRemarks() != null && !template.getRemarks().isEmpty();
        
        if (hasRemarks) {
            // 构建备注文本
            StringBuilder remarkText = new StringBuilder();
            for (int i = 0; i < template.getRemarks().size(); i++) {
                if (i > 0) {
                    remarkText.append("\n");
                }
                remarkText.append(template.getRemarks().get(i));
            }
            String remarkStr = remarkText.toString();
            
            // 两级表头：第一级是备注，第二级是字段标题
            for (int i = 0; i < sorted.size(); i++) {
                List<String> column = new ArrayList<>();
                column.add(remarkStr); // 第一行：备注（EasyExcel 会自动合并）
                column.add(sorted.get(i).getTitle()); // 第二行：字段标题
                head.add(column);
            }
        } else {
            // 单级表头
            head = sorted.stream()
                    .map(f -> Collections.singletonList(f.getTitle()))
                    .collect(Collectors.toList());
        }

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

        // 3) 创建处理器
        ExcelStyleHandler styleHandler = new ExcelStyleHandler(template, template.getRemarks());

        // 4) 先写入到内存，处理样式后再输出
        // 这样可以确保表头样式正确应用
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EasyExcel.write(baos)
                .registerWriteHandler(styleHandler)
                .head(head)
                .sheet("导出")
                .doWrite(data);
        
        // 5) 从内存中读取 Workbook，处理备注行合并和表头样式
        byte[] bytes = baos.toByteArray();
        try (XSSFWorkbook workbook = new XSSFWorkbook(new java.io.ByteArrayInputStream(bytes))) {
            Sheet sheet = workbook.getSheetAt(0);
            
            boolean hasRemarks2 = template.getRemarks() != null && !template.getRemarks().isEmpty();
            
            // 处理备注行：合并单元格并设置样式
            if (hasRemarks2) {
                styleHandler.processRemarkRow(sheet, workbook);
            }
            
            // 处理表头样式（此时表头已创建）
            int headerRowIndex = hasRemarks2 ? 1 : 0;

            Row headerRow = sheet.getRow(headerRowIndex);
                  if (headerRow != null) {
                      System.out.println("[DEBUG][DynamicExportService] headerRowIndex=" + headerRowIndex + ", cellCount=" + headerRow.getLastCellNum());
                      styleHandler.styleHeaderRow(workbook, headerRow);
                      applyRedStar(workbook, headerRow, sorted, headerRowIndex);
                  } else {
                      System.out.println("[DEBUG][DynamicExportService] headerRow is null, index=" + headerRowIndex);
                  }
            
            // 自动调整列宽和行高
            styleHandler.autoSizeColumnsAndRows(sheet);
            
            // 写入到输出流
            workbook.write(out);
        }
    }

          private void applyRedStar(XSSFWorkbook workbook, Row headerRow, List<TemplateField> sortedFields, int headerRowIndex) {
              if (headerRow == null || sortedFields == null || sortedFields.isEmpty()) {
                  System.out.println("[DEBUG][applyRedStar] headerRow为空或fields为空");
                  return;
              }
              System.out.println("[DEBUG][applyRedStar] 开始处理，行号=" + headerRowIndex + ", 字段数=" + sortedFields.size());
              XSSFFont redFont = workbook.createFont();
              redFont.setColor(new XSSFColor(new java.awt.Color(255, 0, 0), null));
              redFont.setFontName("宋体");
              redFont.setFontHeightInPoints((short) 11);

              XSSFFont normalFont = workbook.createFont();
              normalFont.setColor(new XSSFColor(new java.awt.Color(0, 0, 0), null));
              normalFont.setFontName("宋体");
              normalFont.setFontHeightInPoints((short) 11);

              for (int i = 0; i < sortedFields.size(); i++) {
                  TemplateField field = sortedFields.get(i);
                  if (field == null) {
                      System.out.println("[DEBUG][applyRedStar] 字段索引" + i + "为空，跳过");
                      continue;
                  }
                  if (!Boolean.TRUE.equals(field.getRequired())) {
                      System.out.println("[DEBUG][applyRedStar] 字段" + field.getTitle() + " 非必填，跳过");
                      continue;
                  }
                  String title = field.getTitle();
                  if (title == null) {
                      System.out.println("[DEBUG][applyRedStar] 字段索引" + i + "标题为空");
                      continue;
                  }
                  String normalized = title.replace("＊", "*").replace("×", "*");
                  int starIndex = normalized.indexOf('*');
                  if (starIndex < 0) {
                      System.out.println("[DEBUG][applyRedStar] 字段" + title + " 没有星号");
                      continue;
                  }

                  Cell cell = headerRow.getCell(i);
                  if (cell == null) {
                      System.out.println("[DEBUG][applyRedStar] 单元格为空 index=" + i);
                      continue;
                  }
                  if (!(cell instanceof XSSFCell)) {
                      System.out.println("[DEBUG][applyRedStar] 单元格不是XSSFCell, 实际类型=" + cell.getClass().getName());
                      continue;
                  }

                  System.out.println("[DEBUG][applyRedStar] 处理列 index=" + i + ", 标题=" + normalized + ", starIndex=" + starIndex);
                  XSSFRichTextString richText = new XSSFRichTextString();
                  int redEnd = Math.min(normalized.length(), starIndex + 1); // 星号和其后的第一个字符一起变红
                  if (starIndex > 0) {
                      richText.append(normalized.substring(0, starIndex), normalFont);
                  }
                  richText.append(normalized.substring(starIndex, redEnd), redFont);
                  if (redEnd < normalized.length()) {
                      richText.append(normalized.substring(redEnd), normalFont);
                  }
                  XSSFCell xssfCell = (XSSFCell) cell;
                  // 使用 inlineStr 是为了解决 EasyExcel 先写入字符串后又覆盖富文本的问题
                  // 直接操作 CTCell 可以确保最终 sheet1.xml 中包含 <rPr> 的颜色信息
                  CTCell ctCell = xssfCell.getCTCell();
                  ctCell.setT(STCellType.INLINE_STR);
                  ctCell.setIs(richText.getCTRst());
                  if (ctCell.isSetV()) {
                      ctCell.unsetV();
                  }
                  System.out.println("[DEBUG][applyRedStar] 已写入富文本, runCount=" + richText.numFormattingRuns());
                  for (int run = 0; run < richText.numFormattingRuns(); run++) {
                      XSSFFont runFont = richText.getFontOfFormattingRun(run);
                      if (runFont != null) {
                          XSSFColor fontColor = runFont.getXSSFColor();
                          System.out.println("[DEBUG][applyRedStar] run=" + run +
                                  ", rgb=" + (fontColor != null && fontColor.getRGB() != null ?
                                  java.util.Arrays.toString(fontColor.getRGB()) : "null"));
                      } else {
                          System.out.println("[DEBUG][applyRedStar] run=" + run + ", font信息为空");
                      }
                  }
              }
          }
}
