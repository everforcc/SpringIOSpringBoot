/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-14 21:58
 * Copyright
 */

package cn.cc.sp31usercraw.business.impl;

import cn.cc.sp31usercraw.business.IBusiness;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import org.springframework.stereotype.Service;

/**
 * 某个业务的实现类
 */
@Service("businessDYBZ")
public class BusinessDYBZ implements IBusiness {
    @Override
    public String endCondition(String pageSource, NovelConfigDto novelConfigDto) {
        return null;
    }

    @Override
    public String dealContent(String content, String rootUrl) {
        return null;
    }
}
