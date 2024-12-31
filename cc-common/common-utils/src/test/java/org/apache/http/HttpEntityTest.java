package org.apache.http;

import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * HttpClients 请求的返回值
 */
public class HttpEntityTest {

    @Test
    public void string() {
        HttpEntity entity = null;
        try {
            returnString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void bytes() {
        HttpEntity entity = null;
        try {
            returBytes(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理字符串
     */
    private String returnString(HttpEntity entity) throws IOException {
        return EntityUtils.toString(entity);
    }

    /**
     * 处理为媒体文件
     */
    private byte[] returBytes(HttpEntity entity) throws IOException {
        return EntityUtils.toByteArray(entity);
    }

}
