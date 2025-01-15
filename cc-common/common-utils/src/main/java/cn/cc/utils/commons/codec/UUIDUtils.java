package cn.cc.utils.commons.codec;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 生成uuid并去掉中间 -
     * @return
     */
    public static String uuid32(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(uuid32());
    }

}
