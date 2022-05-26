/**
 * Project:TODO ADD PROJECT NAME OneForAll
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-05-26 09:47
 * Copyright
 */

package com.cc.sp90utils.commons.lang;

import java.util.Date;

public class RDateUtils {

    public static double betweenDateSecond(Date startDate, Date endDate){
        return (endDate.getTime() - startDate.getTime())/1000d;
    }

}
