import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.IntStream;

public class ImageToExcelConverter {

    // 预定义调色板
    private static final int[] PALETTE = {
        0x000000, 0xFFFFFF, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF
    };

    public static void convertImageToExcel(String imagePath, String excelPath) throws IOException {
        long startTime = System.currentTimeMillis();

        // 读取图片并降低分辨率
        BufferedImage image = ImageIO.read(new File(imagePath));
        int scaleFactor = 4; // 增加降低分辨率因子，调整为4以减少处理时间
        int width = image.getWidth() / scaleFactor;
        int height = image.getHeight() / scaleFactor;

        // 确保宽高一致
        int size = Math.max(width, height); // 使用最大值确保宽高一致
        width = size;
        height = size;

        // 确保宽高一致，再次调整

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Image Data");

        // 设置单元格的宽度和高度
        int cellWidth = 256 * 1; // 调整为1个字符宽度
        int rowHeight = 100 * 1; // 调整为10个像素高度

        // 确保单元格的宽度和高度在像素点长度上一致
        int pixelSize = 2; // 定义每个单元格的像素大小
        cellWidth = 256 * pixelSize; // 调整单元格宽度为像素大小的倍数
        rowHeight = 100 * pixelSize; // 调整单元格高度为像素大小的倍数

        // 确保所有单元格的宽度和高度一致
        for (int i = 0; i < width; i++) {
            sheet.setColumnWidth(i, cellWidth);
        }

        // 使用顺序流处理图片的每一行，确保顺序正确
        final int finalWidth = width; // 将 finalWidth 声明为 final
        int finalHeight = height;
        int finalRowHeight = rowHeight;
        IntStream.range(0, height).forEach(y -> {
            Row row = sheet.createRow(y);
            row.setHeight((short) finalRowHeight);
            for (int x = 0; x < finalWidth; x++) {
                // 获取像素颜色并直接使用原始颜色值
                int rgb = image.getRGB(x * scaleFactor, y * scaleFactor);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                byte[] rgbBytes = new byte[]{(byte) red, (byte) green, (byte) blue};
                XSSFColor color = new XSSFColor(rgbBytes, null);

                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFillForegroundColor(color);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                Cell cell = row.createCell(x);
                cell.setCellStyle(cellStyle);
            }

            // 显示进度条
            if (y % 10 == 0) { // 每处理10行更新一次进度
                int progress = (int) ((y / (double) finalHeight) * 100);
                System.out.print("\rProgress: " + progress + "%");
            }
        });

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
        System.out.println("\nImage converted to Excel successfully. Time elapsed: " + elapsedTime + " ms");
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
            convertImageToExcel("D:/cache/test/派蒙.jpg", "D:/cache/test/派蒙13.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}