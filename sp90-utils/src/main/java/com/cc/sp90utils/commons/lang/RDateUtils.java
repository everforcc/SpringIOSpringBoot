/**
 * Project:TODO ADD PROJECT NAME OneForAll
 *
 * @Description
 * @Author everforcc
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
