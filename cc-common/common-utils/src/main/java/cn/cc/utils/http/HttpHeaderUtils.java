/**
 * Project:TODO ADD PROJECT NAME OneForAll
 *
 * @Description
 * @Author everforcc
 * @Date 2022-05-24 15:15
 * Copyright
 */

package cn.cc.utils.http;

import cn.cc.utils.commons.regex.RegexUtils;
import cn.cc.utils.enums.CharsetsEnum;
import cn.cc.utils.exception.Code;
import cn.cc.utils.commons.lang.RStringUtils;
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

}
