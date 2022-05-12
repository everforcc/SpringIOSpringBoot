package com.cc.sp90utils.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface HttpUtils {

    String requestForMsg(HttpParam httpParam);

    /**
     * 这种默认用 JDK自带的做
     * @param httpParam
     * @return
     */
    default InputStream requestForStream(HttpParam httpParam){
        try {
            // 待完善
            return new URL(httpParam.getUrl()).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
