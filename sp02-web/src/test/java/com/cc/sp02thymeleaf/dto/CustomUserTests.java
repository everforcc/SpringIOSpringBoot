/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-28 16:45
 * Copyright
 */

package com.cc.sp02thymeleaf.dto;

import org.junit.Test;

public class CustomUserTests {

    @Test
    public void tJson(){
        CustomUser customUser = new CustomUser();
        customUser.setName("name");
        customUser.setPas("pas");
        System.out.println(customUser.toString());
    }

    public void tDto(){
        String json = "{\"name\":\"name\",\"pas\":\"pas\"}";
    }

}
