import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageToExcelConverter1 {

    // 预定义调色板
    private static final int[] PALETTE = {
        0x000000, 0xFFFFFF, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF
    };

    public static void convertImageToExcel(String imagePath, String excelPath) throws IOException {
        long startTime = System.currentTimeMillis();

        // 读取图片并降低分辨率
        BufferedImage image = ImageIO.read(new File(imagePath));
        int scaleFactor = 2; // 降低分辨率因子
        int width = image.getWidth() / scaleFactor;
        int height = image.getHeight() / scaleFactor;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Image Data");

        // 设置单元格的宽度和高度
        int cellWidth = 256 * 2; // 256 是 Excel 中默认的宽度单位，乘以 2 表示 2 个字符宽度
        int rowHeight = 20 * 15; // 20 是 Excel 中默认的高度单位，乘以 15 表示 15 个像素高度

        for (int i = 0; i < width; i++) {
            sheet.setColumnWidth(i, cellWidth);
        }

        Map<String, CellStyle> styleCache = new HashMap<>();

        // 分批处理图片
        int batchSize = 1000; // 每批处理的行数
        for (int yStart = 0; yStart < height; yStart += batchSize) {
            int yEnd = Math.min(yStart + batchSize, height);
            for (int y = yStart; y < yEnd; y++) {
                Row row = sheet.createRow(y);
                row.setHeight((short) rowHeight);
                for (int x = 0; x < width; x++) {
                    // 获取像素颜色并映射到调色板
                    int rgb = image.getRGB(x * scaleFactor, y * scaleFactor);
                    int closestColor = findClosestColor(rgb);

                    int red = (closestColor >> 16) & 0xFF;
                    int green = (closestColor >> 8) & 0xFF;
                    int blue = closestColor & 0xFF;

                    String colorKey = red + "," + green + "," + blue;

                    CellStyle cellStyle = styleCache.get(colorKey);
                    if (cellStyle == null) {
                        byte[] rgbBytes = new byte[]{(byte) red, (byte) green, (byte) blue};
                        XSSFColor color = new XSSFColor(rgbBytes, null);

                        cellStyle = workbook.createCellStyle();
                        cellStyle.setFillForegroundColor(color);
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        styleCache.put(colorKey, cellStyle);
                    }

                    Cell cell = row.createCell(x);
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        // 调整列宽
        for (int i = 0; i < width; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(excelPath)) {
            workbook.write(fileOut);
        }

        workbook.close();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Image converted to Excel successfully. Time elapsed: " + elapsedTime + " ms");
    }

    // 找到最接近的调色板颜色
    private static int findClosestColor(int rgb) {
        int minDistance = Integer.MAX_VALUE;
        int closestColor = 0;
        for (int color : PALETTE) {
            int distance = colorDistance(rgb, color);
            if (distance < minDistance) {
                minDistance = distance;
                closestColor = color;
            }
        }
        return closestColor;
    }

    // 计算颜色距离
    private static int colorDistance(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >> 8) & 0xFF;
        int b1 = rgb1 & 0xFF;
        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >> 8) & 0xFF;
        int b2 = rgb2 & 0xFF;
        return (r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2);
    }

    public static void main(String[] args) {
        try {
            convertImageToExcel("D:/cache/test/派蒙.jpg", "D:/cache/test/派蒙1.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}