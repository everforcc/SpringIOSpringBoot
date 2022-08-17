package com.cc.sp03data.jdbc;

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
