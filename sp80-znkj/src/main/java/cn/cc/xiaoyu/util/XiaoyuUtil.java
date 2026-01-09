package cn.cc.xiaoyu.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class XiaoyuUtil {

    public static List<String> hexString(byte[] bytes) {
        List<String> strings = new ArrayList<>();
        for (byte aByte : bytes) {
            log.info("aByte: {}", aByte);
            String hexString = Integer.toHexString(aByte & 0xff);
            strings.add(hexString);
        }
        return strings;
    }

}
