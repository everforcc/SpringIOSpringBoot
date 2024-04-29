package cn.cc.utils;

import java.math.BigDecimal;

/**
 * @Description : 数值工具类
 * @Author : GKL
 * @Date: 2024-04-29 11:30
 */
public class BigDecimalUtils {

    public static String trans(BigDecimal bigDecimal) {
        return bigDecimal.setScale(7, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }

}
