/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-06 21:26
 * Copyright
 */

package cn.cc.utils.opencv.util;

import cn.cc.utils.opencv.constant.OCRConstant;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.global.lept;
import org.bytedeco.tesseract.TessBaseAPI;

import java.io.File;

/**
 * javacv
 * TessBaseAPI 识别简单的文字
 * [TessBaseAPI-API参考文档](https://blog.csdn.net/u010307127/article/details/122738387)
 *
 */
@Slf4j
public class OcrTess {


    /**
     * 文字识别
     * @param lng
     * @param dataPath
     * @param imagePath
     * @return
     */
    private static String OCR(String lng, String dataPath, String imagePath) {
        TessBaseAPI tessBaseAPI = new TessBaseAPI();

        if (tessBaseAPI.Init(dataPath, lng) != 0) {
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

    private static String OCRTess4j(String lng, String dataPath, String imagePath) {
        //加载待读取图片
        File imageFile = new File(imagePath);
        //创建tess对象
        Tesseract instance = new Tesseract();
        //设置训练所属文件目录
        instance.setDatapath(dataPath);
        //设置训练语言
        instance.setLanguage(lng);
        //执行转换
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            throw new RuntimeException("图像识别异常", e);
        }

        return result;
    }

    /**
     * 少量文字简单的识别
     *
     * @param imagePath 图片地址
     * @return 返回识别结果
     */
    public static String simple_OCR_UTF_8(String imagePath) {
        return OCRTess4j(OCRConstant.data, OCRConstant.dataPath, imagePath);
    }

    public static void main(String[] args) {
        //String imagePath = "E:\\filesystem\\project\\SpringIOSpringBoot\\92webapi\\2546160883.png";
        String imagePath = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp31-user-craw\\test\\";
        try {
            String result = simple_OCR_UTF_8(imagePath + "3.jpg");
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
