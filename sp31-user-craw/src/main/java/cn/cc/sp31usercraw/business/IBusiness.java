/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-14 21:50
 * Copyright
 */

package cn.cc.sp31usercraw.business;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.utils.http.selenium.WebDriverPDto;

/**
 * 处理特殊网站，通用接口无法完全处理的问题
 * 可能需要注册到spring,先实现再说
 */
public interface IBusiness {

    /**
     * 1. 判断爬取异常的情况
     * 2. 对处理完的数据进行最后的处理
     */
    /**
     * 可能爬数据的结果不是正确的页面，
     * 需要进行分析
     *
     * @param pageSource html内容
     * @param novelConfigDto 配置信息
     * @return
     */
    default String endCondition(String pageSource, NovelConfigDto novelConfigDto){
        return pageSource;
    }

    String endCondition(String pageSource, NovelConfigDto novelConfigDto, WebDriverPDto webDriverPDto, String linkText);

    /**
     * 如果结果是正确的页面,进行降噪处理
     *
     * @param content 正确的内容
     * @param rootUrl 网站跟地址
     * @return 处理后的内容
     */
    String dealContent(String content, String rootUrl);

}
