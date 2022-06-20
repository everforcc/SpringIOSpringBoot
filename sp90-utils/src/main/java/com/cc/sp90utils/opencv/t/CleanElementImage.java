/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-14 09:39
 * Copyright
 */

package com.cc.sp90utils.opencv.t;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CleanElementImage {

    public static void main(String[] args) {
        String oldFile = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp90-utils\\ocr\\lol-2.png";
        String newFilePath = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp90-utils\\ocr\\lol-3.jpg";
        try {
            handlImage(oldFile, newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param picStr   图片路径
     * @param fileName 图片名
     * @throws IOException
     */
    public static String handlImage(String picStr, String fileName) throws IOException {
        // 需要去噪的图像
        String sfile = picStr + File.separator + fileName;
        // 去噪后的图像保存地址
        String destDir = picStr + File.separator + fileName.substring(0, fileName.lastIndexOf(".")) + "-dc.jpg";
        ;
        //
        File destF = new File(destDir);
        if (!destF.exists()) {
            destF.mkdirs();
        }

        BufferedImage bufferedImage = ImageIO.read(new File(sfile));
        int h = bufferedImage.getHeight();
        int w = bufferedImage.getWidth();

        // 灰度化
        int[][] gray = new int[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int argb = bufferedImage.getRGB(x, y);
                // 图像加亮（调整亮度识别率非常高）
                int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
                int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
                int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
                if (r >= 255) {
                    r = 255;
                }
                if (g >= 255) {
                    g = 255;
                }
                if (b >= 255) {
                    b = 255;
                }
                gray[x][y] = (int) Math
                        .pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)
                                * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
            }
        }

        // 二值化
        int threshold = ostu(gray, w, h);
        BufferedImage binaryBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (gray[x][y] > threshold) {
                    gray[x][y] |= 0x00FFFF;
                } else {
                    gray[x][y] &= 0xFF0000;
                }
                binaryBufferedImage.setRGB(x, y, gray[x][y]);
            }
        }

        //去除干扰线条
        /* 注释 */
        for(int y = 1; y < h-1; y++){
            for(int x = 1; x < w-1; x++){
                boolean flag = false ;
                if(isBlack(binaryBufferedImage.getRGB(x, y))){
//                    //左右均为空时，去掉此点
//                    if(isWhite(binaryBufferedImage.getRGB(x-1, y)) && isWhite(binaryBufferedImage.getRGB(x+1, y))){
//                        flag = true;
//                    }
//                    //上下均为空时，去掉此点
//                    if(isWhite(binaryBufferedImage.getRGB(x, y+1)) && isWhite(binaryBufferedImage.getRGB(x, y-1))){
//                        flag = true;
//                    }
//                    //斜上下为空时，去掉此点
//                    if(isWhite(binaryBufferedImage.getRGB(x-1, y+1)) && isWhite(binaryBufferedImage.getRGB(x+1, y-1))){
//                        flag = true;
//                    }
//                    if(isWhite(binaryBufferedImage.getRGB(x+1, y+1)) && isWhite(binaryBufferedImage.getRGB(x-1, y-1))){
//                        flag = true;
//                    }

                    //左右均为空时，去掉此点
                    if(isWhite(binaryBufferedImage.getRGB(x-1, y)) && isWhite(binaryBufferedImage.getRGB(x+1, y))
                            //上下均为空时，去掉此点
                        && isWhite(binaryBufferedImage.getRGB(x, y+1)) && isWhite(binaryBufferedImage.getRGB(x, y-1))
                            //斜上下为空时，去掉此点
                        && isWhite(binaryBufferedImage.getRGB(x-1, y+1)) && isWhite(binaryBufferedImage.getRGB(x+1, y-1))
                        && isWhite(binaryBufferedImage.getRGB(x+1, y+1)) && isWhite(binaryBufferedImage.getRGB(x-1, y-1))){
                        flag = true;
                    }

                    if(flag){
                        binaryBufferedImage.setRGB(x,y,-1);
                    }
                }
            }
        }
        /* 注释 */

        ImageIO.write(binaryBufferedImage, "jpg", new File(destDir));
        return destDir;
    }

    public static boolean isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
            return true;
        }
        return false;
    }

    public static boolean isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
            return true;
        }
        return false;
    }

    public static int isBlackOrWhite(int colorInt) {
        if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
            return 1;
        }
        return 0;
    }

    public static int getColorBright(int colorInt) {
        Color color = new Color(colorInt);
        return color.getRed() + color.getGreen() + color.getBlue();
    }

    public static int ostu(int[][] gray, int w, int h) {
        int[] histData = new int[w * h];
        // Calculate histogram
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int red = 0xFF & gray[x][y];
                histData[red]++;
            }
        }

        // Total number of pixels
        int total = w * h;

        float sum = 0;
        for (int t = 0; t < 256; t++) {
            sum += t * histData[t];
        }

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int t = 0; t < 256; t++) {
            wB += histData[t]; // Weight Background
            if (wB == 0) {
                continue;
            }

            wF = total - wB; // Weight Foreground
            if (wF == 0) {
                break;
            }

            sumB += (float) (t * histData[t]);

            float mB = sumB / wB; // Mean Background
            float mF = (sum - sumB) / wF; // Mean Foreground

            // Calculate Between Class Variance
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            // Check if new maximum found
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }

        return threshold;
    }
}
