/**
 * Project:TODO ADD PROJECT NAME OneForAll
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-05-24 15:15
 * Copyright
 */

package com.cc.sp90utils.http;

import com.cc.sp90utils.commons.lang.RStringUtils;
import com.cc.sp90utils.commons.regex.RegexUtils;
import com.cc.sp90utils.enums.CharsetsEnum;
import com.cc.sp90utils.exception.AppCode;
import com.cc.sp90utils.exception.Code;
import lombok.SneakyThrows;

import java.net.URLDecoder;

public class HttpHeaderUtils {

    private static String dispositonFileRegex = "attachment;filename=\"(.*)\"";
    private static int dispositonFileGroup = 1;

    /**
     * 将 content_dispositon 处理为文件名
     * @param content_dispositon
     * @return
     */
    @SneakyThrows
    public static String contentDispositionToFileName(String content_dispositon){
        Code.A00001.assertHasTrue(RStringUtils.isNotEmpty(content_dispositon));
        //String fileName = content_dispositon.replace("attachment;filename=\"", "").replace("\"", "");
        String fileName = RegexUtils.matcheStr(dispositonFileRegex,content_dispositon,dispositonFileGroup);
        return URLDecoder.decode(fileName, CharsetsEnum.UTF_8.charset.toString());
    }

    public static void main(String[] args) {
        String content_dispositon = "attachment;filename=\"中文.mp4\"";
        System.out.println(contentDispositionToFileName(content_dispositon));
    }

}
