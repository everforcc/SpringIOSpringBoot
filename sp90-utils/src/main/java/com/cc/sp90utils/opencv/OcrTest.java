/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-06 21:26
 * Copyright
 */

package com.cc.sp90utils.opencv;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.global.lept;
import org.bytedeco.tesseract.TessBaseAPI;

/**
 * javacv测试
 */
@Slf4j
public class OcrTest {

    private static String dataPath = "F:\\Cache\\BaiduNetdiskWorkspace\\java\\github\\tesseract-ocr\\tessdata";
    private static String data = "chi_sim";

    /**
     * [语言包](https://github.com/tesseract-ocr/tessdata)
     *
     * @param lng
     * @param dataPath
     * @param imagePath
     * @return
     */
    private static String OCR(String lng, String dataPath, String imagePath){
        TessBaseAPI tessBaseAPI = new TessBaseAPI();

        if(tessBaseAPI.Init(dataPath, lng)!=0){
            log.error("初始化异常error");
        }

        PIX image = lept.pixRead(imagePath);

        tessBaseAPI.SetImage(image);

        BytePointer bytePointer = tessBaseAPI.GetUTF8Text();
        String result = bytePointer.getString();
        tessBaseAPI.End();
        bytePointer.deallocate();
        lept.pixDestroy(image);

        return result;
    }

    /**
     * 少量文字简单的识别
     * @param imagePath 图片地址
     * @return 返回识别结果
     */
    public static String simple_OCR_UTF_8(String imagePath){
        return OCR(data, dataPath, imagePath);
    }

    public static void main(String[] args) {
        String imagePath = "E:\\filesystem\\project\\SpringIOSpringBoot\\92webapi\\2546160883.png";

        String result = simple_OCR_UTF_8(imagePath);

        System.out.println(result);
    }

}
