/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-13 22:55
 * Copyright
 */

package cn.cc.sp31usercraw.utils;

import com.cc.sp90utils.commons.io.RFileUtils;
import com.cc.sp90utils.commons.web.HttpParamUtils;
import com.cc.sp90utils.commons.web.JHttpUrlConnectUtils;
import com.cc.sp90utils.opencv.OCRUtils;
import com.cc.sp90utils.opencv.util.OcrTess;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 对单字OCR识别
 */
@Slf4j
public class CharsetOCR {

    private static String rootPath = "F:\\Cache\\BaiduNetdiskWorkspace\\java\\filesystem\\ocr\\";
    private static String fileSuffix = "\\.[a-z]{3,4}";
    private static String ocrResultSuffix = "\\.txt";

    /**
     * 通用的简单png文字识别
     * 背景是透明的，如果是简单的调用另外的接口
     *
     * @param url 问题图片地址
     * @return 返回结果
     */
    public static String ocr(String url) {

        // 1. 取出图片名
        String imgName = HttpParamUtils.getLastPath(url);
        // 识别后保存的文件名
        String resultFileName = imgName.replaceAll(fileSuffix, ocrResultSuffix);

        // 2. 检测图片是否存在
        String filePath = rootPath + imgName;
        if (RFileUtils.exists(filePath)) {
            String result = RFileUtils.readFileToString(rootPath + resultFileName);
            log.info("数据已存在：[{}]", result);
            return result;

        } else {
            // 3. 不存在就保存图片
            InputStream inputStream = JHttpUrlConnectUtils.getInputStream(url, "GET");
            RFileUtils.copyToFile(inputStream, filePath);
        }

        // 4. 识别
        /**
         * 识别文字，替换末尾的换行符
         */
        String result = OCRUtils.ocrSimpleDealLucency(rootPath, imgName)
                .replace("\r\n", "")
                .replace("\n", "");

        log.info("[{}] 识别结果 [{}]", filePath, result);

        // 5. 保存
        RFileUtils.writeStringToFile(rootPath + resultFileName, result);

        return result;
    }

    public static void main(String[] args) {
        String url = "";
        log.info(ocr(url));
    }

}
