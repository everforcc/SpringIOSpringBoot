/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-14 21:50
 * Copyright
 */

package cn.cc.sp31usercraw.business;

import cn.cc.sp31usercraw.dto.NovelConfigDto;

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
    String endCondition(String pageSource, NovelConfigDto novelConfigDto);

    /**
     * 如果结果是正确的页面,进行降噪处理
     *
     * @param content 正确的内容
     * @param rootUrl
     * @return
     */
    String dealContent(String content, String rootUrl);

}
