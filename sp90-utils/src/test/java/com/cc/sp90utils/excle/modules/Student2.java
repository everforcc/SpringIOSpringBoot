/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-26 18:44
 * Copyright
 */

package com.cc.sp90utils.excle.modules;

import com.cc.sp90utils.excle.converter.Student2DateConverter;
import com.cc.sp90utils.excle.converter.Student2ExpelConverter;
import com.github.crab2died.annotation.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student2 {

    @ExcelField(title = "学号", order = 1)
    private Long id;

    @ExcelField(title = "姓名", order = 2)
    private String name;

    // 写入数据转换器 Student2DateConverter
    @ExcelField(title = "入学日期", order = 3, writeConverter = Student2DateConverter.class)
    private Date date;

    @ExcelField(title = "班级", order = 4)
    private Integer classes;

    // 读取数据转换器 Student2ExpelConverter
    @ExcelField(title = "是否开除", order = 5, readConverter = Student2ExpelConverter.class)
    private boolean expel;

}
