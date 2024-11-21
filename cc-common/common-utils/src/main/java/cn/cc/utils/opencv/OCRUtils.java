/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-14 13:45
 * Copyright
 */

package cn.cc.utils.opencv;

import cn.cc.utils.opencv.t.CleanElementImage;
import cn.cc.utils.opencv.util.OcrTess;
import cn.cc.utils.opencv.util.PicDeal;

import java.io.IOException;

/**
 * 提供给外部的ocr识别接口
 * 两种简单的情况
 */
public class OCRUtils {

    /**
     * 1. 简单的文字识别
     * 图片少量文字，直接能识别
     *
     * @param picStr   图片路径
     * @param fileName 图片名
     * @return 返回识别结果
     */
    public static String ocrSimple(String picStr, String fileName) {
        String newPath = "";
        try {
            newPath = CleanElementImage.handlImage(picStr, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return OcrTess.simple_OCR_UTF_8(newPath);
    }

    /**
     * 2. 类似png的图片处理完再识别<br/>
     * 图片少量文字，但是背景透明，处理白底<br/>
     * 处理后在调整黑白，高亮<br/>
     * 识别<br/>
     * <p>
     * <p>
     * <font color='red'>还有干扰点没有去除</font>
     *
     * @param picStr   图片路径
     * @param fileName 图片名
     * @return
     */
    public static String ocrSimpleDealLucency(String picStr, String fileName) {
        // 将图片处理为jpg，然后返回新文件路径，然后识别
        String newFileName = PicDeal.dealPic(picStr, fileName);
        // 将jpg再处理黑白，亮度，生成新图片
        String newPath = "";
        try {
            newPath = CleanElementImage.handlImage(picStr, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return OcrTess.simple_OCR_UTF_8(newPath);
    }


    public static String ocrComplexDealColor(String picStr, String fileName) {
        String result = "";
        try {
            String newPath = CleanElementImage.handlImage(picStr, fileName);
            result = OcrTess.simple_OCR_UTF_8(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String picStr = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp90-utils\\ocr";
        String fileName = "20220714140324.jpg";

//        String resultSimple = ocrSimple(picStr, fileName);
//        System.out.println("resultSimple: " + resultSimple);

        String resultSimpleDealLucency = ocrSimpleDealLucency(picStr, fileName);
        System.out.println("resultSimpleDealLucency: " + resultSimpleDealLucency);

//        String resultComplexDealColor = ocrComplexDealColor(picStr, fileName);
//        System.out.println("resultComplexDealColor: " + resultComplexDealColor);
    }

}
