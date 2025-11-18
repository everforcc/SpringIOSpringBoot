package cn.cc.easyexcel.dynamic;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCell;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STCellType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Excel样式处理器
 * 用于处理表头样式、红色星号、单元格合并等
 * 使用两级表头结构：第一级是备注（自动合并），第二级是字段标题
 */
public class ExcelStyleHandler implements SheetWriteHandler {

    private final TemplateDefinition template;
    private final List<String> remarks; // 备注信息列表

    public ExcelStyleHandler(TemplateDefinition template, List<String> remarks) {
        this.template = template;
        this.remarks = remarks != null ? remarks : Collections.emptyList();
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder,
                                 WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        
        // EasyExcel 会在 afterSheetCreate 之后创建表头
        // 我们使用两级表头结构，第一级是备注，第二级是字段标题
        // EasyExcel 会自动合并第一级的相同内容
        
        // 处理表头样式：将必填字段的*显示为红色
        // 由于表头可能还没有创建，我们需要延迟处理
        // 但不用线程，而是直接检查表头是否已创建
        processHeaderStyle(sheet, workbook);
    }
    
    /**
     * 处理表头样式（在表头创建后调用）
     */
    private void processHeaderStyle(Sheet sheet, Workbook workbook) {
        int headerRowIndex = remarks.isEmpty() ? 0 : 1; // 如果有备注，表头在第1行
        
        // 检查表头行是否存在
        Row headerRow = sheet.getRow(headerRowIndex);
        if (headerRow == null) {
            // 表头还没有创建，稍后处理
            return;
        }
        
        // 处理表头样式
        styleHeaderRow(workbook, headerRow);
    }

    /**
     * 处理备注行：合并单元格并设置样式
     * 公开方法，供外部调用
     */
    public void processRemarkRow(Sheet sheet, Workbook workbook) {
        if (remarks.isEmpty()) {
            return;
        }
        
        int totalColumns = template.getFields().size();
        Row remarkRow = sheet.getRow(0);
        if (remarkRow == null) {
            return;
        }
        
        // 构建备注文本，统一星号字符为半角星号
        StringBuilder remarkText = new StringBuilder();
        for (int i = 0; i < remarks.size(); i++) {
            if (i > 0) {
                remarkText.append("\n");
            }
            // 统一星号字符：将全角星号转换为半角星号
            String remark = remarks.get(i).replace("＊", "*").replace("×", "*");
            remarkText.append(remark);
        }
        
        // 设置备注单元格的值和样式
        Cell remarkCell = remarkRow.getCell(0);
        if (remarkCell == null) {
            remarkCell = remarkRow.createCell(0);
        }
        String remarkContent = remarkText.toString();
        applyRemarkRichText(workbook, remarkCell, remarkContent);
        
        // 创建备注样式
        CellStyle remarkStyle = workbook.createCellStyle();
        Font remarkFont = workbook.createFont();
        remarkFont.setFontName("宋体");
        remarkFont.setFontHeightInPoints((short) 10);
        remarkStyle.setFont(remarkFont);
        remarkStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        remarkStyle.setWrapText(true); // 允许换行
        remarkCell.setCellStyle(remarkStyle);
        
        // 清除其他单元格的内容
        for (int i = 1; i < totalColumns; i++) {
            Cell cell = remarkRow.getCell(i);
            if (cell != null) {
                cell.setCellValue("");
            } else {
                remarkRow.createCell(i).setCellValue("");
            }
        }
        
        // 合并第一行的所有列
        if (totalColumns > 1) {
            // 先移除可能存在的合并区域
            removeMergedRegion(sheet, 0);
            // 添加新的合并区域
            CellRangeAddress remarkRange = new CellRangeAddress(0, 0, 0, totalColumns - 1);
            sheet.addMergedRegion(remarkRange);
        }
    }

    /**
     * 将备注中的星号渲染为红色，并保持其余文字为黑色
     */
    private void applyRemarkRichText(Workbook workbook, Cell remarkCell, String remarkContent) {
        if (!(workbook instanceof XSSFWorkbook) || !(remarkCell instanceof XSSFCell)) {
            remarkCell.setCellValue(remarkContent);
            return;
        }
        XSSFWorkbook xssfWorkbook = (XSSFWorkbook) workbook;
        XSSFCell xssfCell = (XSSFCell) remarkCell;

        XSSFFont redFont = xssfWorkbook.createFont();
        redFont.setColor(new XSSFColor(new java.awt.Color(255, 0, 0), null));
        redFont.setFontName("宋体");
        redFont.setFontHeightInPoints((short) 10);
        XSSFFont normalFont = xssfWorkbook.createFont();
        normalFont.setColor(new XSSFColor(new java.awt.Color(0, 0, 0), null));
        normalFont.setFontName("宋体");
        normalFont.setFontHeightInPoints((short) 10);

        XSSFRichTextString richText = buildRemarkRichText(remarkContent, redFont, normalFont);
        CTCell ctCell = xssfCell.getCTCell();
        ctCell.setT(STCellType.INLINE_STR);
        ctCell.setIs(richText.getCTRst());
        if (ctCell.isSetV()) {
            ctCell.unsetV();
        }
    }

    /**
     * 构建备注的富文本，将所有半角/全角星号着色为红色
     */
    private XSSFRichTextString buildRemarkRichText(String content, XSSFFont redFont, XSSFFont normalFont) {
        String normalized = content.replace("＊", "*").replace("×", "*");
        XSSFRichTextString richText = new XSSFRichTextString();
        int cursor = 0;
        for (int i = 0; i < normalized.length(); i++) {
            char ch = normalized.charAt(i);
            if (ch == '*') {
                if (cursor < i) {
                    richText.append(normalized.substring(cursor, i), normalFont);
                }
                richText.append("*", redFont);
                cursor = i + 1;
            }
        }
        if (cursor < normalized.length()) {
            richText.append(normalized.substring(cursor), normalFont);
        }
        return richText;
    }
    
    /**
     * 移除指定行的合并区域
     */
    private void removeMergedRegion(Sheet sheet, int rowIndex) {
        int mergedRegionCount = sheet.getNumMergedRegions();
        for (int i = mergedRegionCount - 1; i >= 0; i--) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            if (region.getFirstRow() == rowIndex && region.getLastRow() == rowIndex) {
                sheet.removeMergedRegion(i);
            }
        }
    }

    /**
     * 处理表头样式，将必填字段的*显示为红色
     * 公开方法，供外部调用
     */
    public void styleHeaderRow(Workbook workbook, Row headerRow) {
        if (headerRow == null) {
            System.out.println("[DEBUG] 警告：表头行为 null");
            return;
        }

        System.out.println("[DEBUG] 开始处理表头样式，Workbook类型: " + workbook.getClass().getName());

        // 创建表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        List<TemplateField> sortedFields = template.getFields().stream()
                .sorted(Comparator.comparingInt(f -> f.getOrder() != null ? f.getOrder() : Integer.MAX_VALUE))
                .collect(Collectors.toList());

        int cellIndex = 0;
        for (TemplateField field : sortedFields) {
            Cell cell = headerRow.getCell(cellIndex);
            if (cell == null) {
                cell = headerRow.createCell(cellIndex);
            }

            String title = field.getTitle();
            
               String currentValue = getCellValueAsString(cell);
               if (!title.equals(currentValue)) {
                   cell.setCellValue(title);
               }
               cell.setCellStyle(headerStyle);

            cellIndex++;
        }
    }

    /**
     * 获取单元格的值作为字符串
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * 创建富文本，将*显示为红色
     * 注意：统一使用半角星号 "*" (U+002A)，不使用全角星号 "＊" (U+FF0A)
     */
    @SuppressWarnings("unused")
    private RichTextString createRichTextWithRedStar(Workbook workbook, String title,
                                                     Font redFont, Font normalFont) {
        // 统一星号字符：将全角星号转换为半角星号
        String normalizedTitle = title.replace("＊", "*").replace("×", "*");
        System.out.println("[DEBUG] 原始标题: " + title + ", 标准化后: " + normalizedTitle);
        
        // 使用 XSSFRichTextString 以获得更好的支持
        RichTextString richText;
        if (workbook instanceof XSSFWorkbook) {
            XSSFRichTextString xssfRichText = new XSSFRichTextString(normalizedTitle);
            
            // 查找星号位置
            int starIndex = normalizedTitle.indexOf("*");
            System.out.println("[DEBUG] 星号位置: " + starIndex);
            
            if (starIndex >= 0) {
                // 对于 XSSFRichTextString，先设置整个文本为普通字体
                xssfRichText.applyFont(0, normalizedTitle.length(), normalFont);
                System.out.println("[DEBUG] 已设置整个文本为普通字体");

                // 然后将星号设置为红色字体（这会覆盖普通字体）
                xssfRichText.applyFont(starIndex, starIndex + 1, redFont);
                System.out.println("[DEBUG] 已设置星号为红色字体");

//                // 先对星号前的部分应用普通字体
//                if (starIndex > 0) {
//                    xssfRichText.applyFont(0, starIndex, normalFont);
//                }
//
//                // 对星号应用红色字体
//                xssfRichText.applyFont(starIndex, starIndex + 1, redFont);
//
//                // 对星号后的部分应用普通字体
//                if (starIndex + 1 < normalizedTitle.length()) {
//                    xssfRichText.applyFont(starIndex + 1, normalizedTitle.length(), normalFont);
//                }

                // 验证红色字体
                if (redFont instanceof XSSFFont) {
                    XSSFFont xssfRedFont = (XSSFFont) redFont;
                    XSSFColor color = xssfRedFont.getXSSFColor();
                    if (color != null) {
                        System.out.println("[DEBUG] 红色字体颜色: RGB(" + 
                                         (color.getRGB() != null ? java.util.Arrays.toString(color.getRGB()) : "null") + ")");
                    } else {
                        System.out.println("[DEBUG] 警告：红色字体颜色为 null");
                    }
                }
            } else {
                // 如果没有星号，使用普通字体
                xssfRichText.applyFont(0, normalizedTitle.length(), normalFont);
                System.out.println("[DEBUG] 未找到星号，使用普通字体");
            }
            richText = xssfRichText;
        } else {
            // 非 XSSF 格式
            richText = workbook.getCreationHelper().createRichTextString(normalizedTitle);
            int starIndex = normalizedTitle.indexOf("*");
            if (starIndex >= 0) {
                richText.applyFont(0, normalizedTitle.length(), normalFont);
                richText.applyFont(starIndex, starIndex + 1, redFont);
            } else {
                richText.applyFont(0, normalizedTitle.length(), normalFont);
            }
        }
        return richText;
    }
    
    /**
     * 自动调整列宽和行高
     */
    public void autoSizeColumnsAndRows(Sheet sheet) {
        if (sheet == null) {
            return;
        }
        
        int totalColumns = template.getFields().size();
        
        // 自动调整列宽
        for (int i = 0; i < totalColumns; i++) {
            sheet.autoSizeColumn(i);
            // 设置最小列宽和最大列宽
            int currentWidth = sheet.getColumnWidth(i);
            // 最小宽度：256 * 8 = 8个字符宽度
            int minWidth = 256 * 8;
            // 最大宽度：256 * 50 = 50个字符宽度
            int maxWidth = 256 * 50;
            if (currentWidth < minWidth) {
                sheet.setColumnWidth(i, minWidth);
            } else if (currentWidth > maxWidth) {
                sheet.setColumnWidth(i, maxWidth);
            }
        }
        
        // 特别处理备注行的行高
        if (!remarks.isEmpty()) {
            Row remarkRow = sheet.getRow(0);
            if (remarkRow != null) {
                // 直接根据备注列表的行数计算行高
                // 每行至少15点高度，多行时加上额外间距（每行之间5点间距）
                int lineCount = remarks.size();
                float rowHeight = Math.max(15.0f * lineCount + 5.0f * Math.max(0, lineCount - 1), 20.0f);
                // 限制最大高度
                rowHeight = Math.min(rowHeight, 200.0f);
                
                remarkRow.setHeightInPoints(rowHeight);
            }
        }
        
        // 自动调整其他行的行高（表头行和数据行）
        int startRow = remarks.isEmpty() ? 0 : 1; // 如果有备注，从表头行开始
        int endRow = sheet.getLastRowNum();
        
        for (int rowIndex = startRow; rowIndex <= endRow && rowIndex <= startRow + 10; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                // 计算行所需的高度
                double maxHeight = 0;
                for (int cellIndex = 0; cellIndex < totalColumns; cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    if (cell != null) {
                        CellStyle style = cell.getCellStyle();
                        if (style != null && style.getWrapText()) {
                            // 如果单元格支持换行，需要计算多行高度
                            String cellValue = getCellValueAsString(cell);
                            if (cellValue != null && !cellValue.isEmpty()) {
                                // 估算行数（每30个字符一行）
                                int lines = (int) Math.ceil(cellValue.length() / 30.0);
                                maxHeight = Math.max(maxHeight, lines * 15.0); // 每行15点高度
                            }
                        }
                    }
                }
                
                // 设置行高（最小高度15，最大高度根据内容）
                if (maxHeight > 0) {
                    row.setHeightInPoints((float) Math.max(15, Math.min(maxHeight, 100)));
                } else {
                    // 默认行高
                    row.setHeightInPoints(20);
                }
            }
        }
    }
    
}
