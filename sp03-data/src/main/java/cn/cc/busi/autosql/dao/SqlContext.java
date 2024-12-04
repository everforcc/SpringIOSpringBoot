/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-04 10:02
 * Copyright
 */

package cn.cc.busi.autosql.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * 动态拼接sql
 */
public class SqlContext {

    public String selectTest(Map<String, Object> param) {//  , String effect, String status
        return new SQL() {
            {
                SELECT("uuid");
                FROM("cc_novel" + param.get("from"));
                WHERE(" id=#{id } ", " effect=#{effect } ", " status=#{status } ");
            }
        }.toString();
    }

    public String deleteTest(final String from, String id, String effect, String status) {
        return new SQL() {
            {
                DELETE_FROM("cc_novel" + from);
                WHERE(" id=#{id } ", " effect=#{effect } ", " status=#{status } ");
            }
        }.toString();
    }

}
