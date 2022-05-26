/**
 * Project:TODO ADD PROJECT NAME OneForAll
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-05-26 09:26
 * Copyright
 */

package com.cc.sp90utils.commons.io;

import com.cc.sp90utils.commons.lang.RDateUtils;
import com.cc.sp90utils.commons.lang.RFastDateFormat;
import com.cc.sp90utils.constant.DateFormatConstant;
import com.cc.sp90utils.exception.Code;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class JInputStreamByteUtils {

    public static void streamToFile(InputStream inputStream, String filePath, String fileName, BigDecimal fileLength){
        // 总时间
        Date startDate = new Date();
        Date startMoment = new Date();

        File file = new File(filePath + File.separator+ fileName);
        //  加参数是否允许重复写入
        if(file.exists()){
            // 如果文件存在那么不在写入?
            log.info(filePath+fileName+"文件存在");
            return;
        }

        // rate两位小数
        DecimalFormat df = new DecimalFormat("00");
        // 间隔
        BigDecimal rate = new BigDecimal(0.01);
        // 比例
        BigDecimal tempRate = new BigDecimal(0);
        // 缓冲区
        byte[] buf = new byte[1024];
        //
        int length = 0;
        // 文件长度 累计计算当前比例
        BigDecimal tempLength = new BigDecimal(length);

        try (FileOutputStream fo = new FileOutputStream(file);
             InputStream in = inputStream){

            //据说包装了有缓存，不切换上下文，比较快
            //BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            while ((length = in.read(buf, 0, buf.length)) != -1) {
                // 不管给没给 fileLength 都计算下总大小和速度
                if(fileLength!=null&&(fileLength.compareTo(new BigDecimal(0))==1)) {
                    tempLength = tempLength.add(new BigDecimal(length));
                    // 每 1% 跳出一行数据     num * 100 / 总长 向下取整
                    tempRate = new BigDecimal(df.format(tempLength.multiply(new BigDecimal(100)).divide(fileLength, 1, BigDecimal.ROUND_DOWN)));
                    // 如果当前比例大于之前存储的比例，那么就输出一行,表示下载比例的数据
                    if (tempRate.compareTo(rate) == 1) {
                        Date endMoment = new Date();
                        double momentTimeDif = RDateUtils.betweenDateSecond(startMoment,endMoment);
                        // kb
                        BigDecimal averageSpeed = tempLength.divide(new BigDecimal(1024), 0, BigDecimal.ROUND_DOWN)
                                .divide(new BigDecimal(momentTimeDif), 1, BigDecimal.ROUND_DOWN);

                        //log.info(fileName + ":下载进度 >>>>>> " + tempRate + "%");
                        log.info("{} 下载进度 >>> [{}%] , 当前速度 >>> [{} kb/s] ",fileName,tempRate,averageSpeed);
                        rate = tempRate;
                    }
                }else {
                    tempLength = tempLength.add(new BigDecimal(length));
                }

                // 写入文件
                fo.write(buf, 0, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw Code.A00001.toUserException(e);
        }

        /**
         * 计算下载所用时间
         */
        Date endDate = new Date();
        double staticTimeDif = RDateUtils.betweenDateSecond(startDate,endDate);
        String fileSize = RFileUtils.byteCountToDisplaySize(tempLength.toBigInteger());

        log.info("开始时间: {} 结束时间: {}",
                RFastDateFormat.format(startDate, DateFormatConstant.yyyy_MM_dd_HH_mm_ss),
                RFastDateFormat.format(endDate, DateFormatConstant.yyyy_MM_dd_HH_mm_ss));
        log.info("{} 文件大小: {} 下载耗时: {}s", fileName, fileSize, staticTimeDif);
    }

    public static void main(String[] args) {

        try {
            String urlPath = "https://downv6.qq.com/qqweb/QQ_1/android_apk/Android_8.8.90.7975_537119415_64.apk";

            URL url = new URL(urlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            Map<String,List<String>> map = httpURLConnection.getHeaderFields();
            int contentLength = httpURLConnection.getContentLength();
            System.out.println(contentLength);

//            for(Map.Entry<String,List<String>> entry: map.entrySet()){
//                System.out.println(entry.getKey());
//                System.out.println(entry.getValue());
//            }
            streamToFile(inputStream,"E:\\filesystem\\project\\OneForAll\\craw\\test","7119415_64.apk",new BigDecimal(contentLength));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
