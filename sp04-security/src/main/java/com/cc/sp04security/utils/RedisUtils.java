package com.cc.sp04security.utils;

import com.cc.sp04security.dto.CustomUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisUtils {

    public static CustomUser getUser(int id){
        log.info("从缓存中取出user");
        return new CustomUser(id);
    }

    public static void removeUser(int id){
        log.info("从缓存中根据id删除user");
    }

    public static void removeUser(String token){
        log.info("从缓存中根据token删除user");
    }

}
