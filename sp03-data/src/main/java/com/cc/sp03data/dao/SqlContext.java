/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-04 10:02
 * Copyright
 */

package com.cc.sp03data.dao;

import org.apache.ibatis.jdbc.SQL;

/**
 * 动态拼接sql
 */
public class SqlContext {

    public String selectTest(final String from, String id, String effect, String status) {
        return new SQL() {
            {
                SELECT("uuid");
                FROM("cc_novel" + from);
                WHERE(" id=#{id } ", " effect=#{effect } ", " status=#{status } ");
            }
        }.toString();
    }


}
