/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-14 13:45
 * Copyright
 */

package com.cc.sp90utils.opencv;

import com.cc.sp90utils.opencv.util.OcrTess;
import com.cc.sp90utils.opencv.util.PicDeal;

/**
 * 提供给外部的ocr识别接口
 * 两种简单的情况
 */
public class OCRUtils {

    /**
     * 1. 简单的文字识别
     * 图片少量文字，直接能识别
     *
     * @param path 图片文件路径
     * @return 返回识别结果
     */
    public static String ocrSimple(String path) {
        return OcrTess.simple_OCR_UTF_8(path);
    }

    /**
     * 2. 类似png的图片处理完再识别
     * 图片少量文字，但是背景透明，处理白底后识别
     *
     * @param picStr   图片路径
     * @param fileName 图片名
     * @return
     */
    public static String ocrSimpleDealLucency(String picStr, String fileName) {
        // 将图片处理为jpg，然后返回新文件路径，然后识别
        String filePath = PicDeal.dealPic(picStr, fileName);
        return OcrTess.simple_OCR_UTF_8(filePath);
    }

//    public static void main(String[] args) {
//        String picStr = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp31-user-craw\\test\\";
//        String fileName = "8637348340.png";
//        String result = ocrSimpleDealLucency(picStr, fileName);
//        System.out.println("result: " + result);
//    }

}
