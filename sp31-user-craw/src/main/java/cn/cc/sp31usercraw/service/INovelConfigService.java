/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-25 11:21
 * Copyright
 */

package cn.cc.sp31usercraw.service;

public interface INovelConfigService {

    /**
     * 下载小说
     *
     * @param url 小说地址
     * @return 下载结果
     */
    String down(String url);

}
