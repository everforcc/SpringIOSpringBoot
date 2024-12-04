package cn.cc.busi.jdbc.utils;

import java.sql.ResultSet;

/**
 * 泛型接口
 *
 * @author cc
 *
 * @param <T>
 */


/**
 *
 */
public interface IResultSetHandler {
	Object handler(ResultSet rs) throws Exception;
}
