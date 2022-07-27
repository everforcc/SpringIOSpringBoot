/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-27 09:54
 * Copyright
 */

package com.cc.sp90utils.excle.converter;

import com.github.crab2died.converter.ReadConvertible;

/**
 * excel是否开除 列数据转换器
 */
public class Student2ExpelConverter implements ReadConvertible {

//    @Override
//    public Object execRead(String object) {
//        return object.equals("是");
//    }
    @Override
    public Object execRead(String object) {
        return object.equals("true");
    }
}