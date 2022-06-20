/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-14 11:36
 * Copyright
 */

package com.cc.sp90utils.opencv.util;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 给透明背景的png
 * 加上白色背景
 * 没有背景OCR识别不了
 */
@Slf4j
public class PicDeal {

    /**
     * 透明背景图片
     * 生成新的白色背景图片
     * 有写死的，但是目前就用到这些，用到了再改
     *
     * @param picStr   图片地址
     * @param fileName 图片名
     * @return 返回信图片地址
     */
    public static String dealPic(String picStr, String fileName) {
        String newFileName;
        if (fileName.endsWith(".png")) {
            newFileName = fileName.replace(".png", ".jpg");
        } else {
            throw new RuntimeException("图片问题识别，未处理情况");
        }
        changeImageBackground(picStr, fileName, newFileName);
        return newFileName;
    }

    /**
     * 给图片加上白色背景
     *
     * @param picStr      图片路径
     * @param fileName    图片名
     * @param newFileName 新图片名
     */
    private static void changeImageBackground(String picStr, String fileName, String newFileName) {

        try {
            // 1. 读取图片
            InputStream in = new FileInputStream(picStr + File.separator + fileName);
            BufferedImage srcImage = ImageIO.read(in);

            // 2. 获取图片高宽
            int oldHeight = srcImage.getHeight();
            int oldWidth = srcImage.getWidth();

            // 3. 背景图大小
            int destWidth = oldWidth * 2;
            int destHeight = oldHeight * 2;

            // 4. 给背景图设置 颜色 -> 白色
            BufferedImage outImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = (Graphics2D) outImage.getGraphics();
            // 设置背景颜色
            graphics2D.setBackground(Color.WHITE);
            graphics2D.clearRect(0, 0, destWidth, destHeight);

            // 5.设置图片居中显示
            graphics2D.drawImage(srcImage, (destWidth - oldWidth) / 2, (destHeight - oldHeight) / 2, null);
            graphics2D.dispose();

            // 6. 图片
            /**
             * outImage背景和原始图片合并
             * os 获取输出流
             * outputStream 将输出流输出到文件
             */
            @Cleanup
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            //ImageIO.write(outImage, ConstantUtil.FileType.PNG, os);
            ImageIO.write(outImage, "jpg", os);

            @Cleanup
            OutputStream outputStream = new FileOutputStream(picStr + File.separator + newFileName);
            os.writeTo(outputStream);

            // 也可以拿到byte处理
            //byte[] bytes = os.toByteArray();
        } catch (IOException e) {
            log.info("ocr 将str转化io异常:");
            e.printStackTrace();
            throw new RuntimeException("图片添加背景色异常");
        }
    }


}
