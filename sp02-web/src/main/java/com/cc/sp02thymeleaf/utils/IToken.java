
package com.cc.sp02thymeleaf.utils;

import com.cc.sp02thymeleaf.dto.CustomUser;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public interface IToken {


    /**
     * 从用户名密码变成字符串
     *
     * @param customUser 用户
     * @return 字符串
     */
    String userToString(CustomUser customUser);

    /**
     * 从字符串变成用户名密码
     *
     * @param str 字符串
     * @return 用户名密码
     */
    CustomUser stringToUser(String str);

    /**
     * 临时返回下信息
     */
    @SneakyThrows
    default void response(HttpServletResponse response, String msg){
        response.setContentType("text/plain;charset=utf-8");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(("被拦截返回异常信息:" + msg).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
