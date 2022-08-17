package com.cc.sp03data.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class BearHandler<T> implements IResultSetHandler {

	public Class<?> type;// 创建反射

	public BearHandler(Class<?> type) {
		this.type = type;
	}

	@Override
	public Object handler(ResultSet rs) throws Exception {
		if (!rs.next()) {
			return null;
		}

		// 获取结果集中列的个数
		ResultSetMetaData meta = rs.getMetaData();
		int cols = meta.getColumnCount();// 返回对象中的列数

		// 创建反射的对象
		Object obj = type.newInstance();

		// Object[] result = new Object[cols];
		for (int i = 0; i < cols; i++) {
			// 列的值
			Object value = rs.getObject(i + 1);
			// 列的名称
			String columnName = meta.getColumnName(i + 1);
			// 通过反射探知对象有哪些属性，并给属性赋值
			Field field = type.getDeclaredField(columnName);
			// Field[] f = type.getDeclaredFields(columnName);

			field.setAccessible(true);// 变为可访问
			field.set(obj, value);// 设置为新值
		}
		return obj;
	}

}
