/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-26 18:32
 * Copyright
 */

package com.cc.sp90utils.excle;

import com.cc.sp90utils.excle.modules.Student2;
import com.github.crab2died.ExcelUtils;
import com.github.crab2died.exceptions.Excel4JException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcleTests {


    @Test
    public void readExcleTests() {
        // 从弟几行开始,第一行是0
        int offsetLine = 0;
        int sheetIndex = 0;
        String path = "./Student2.xlsx";

        try {

            // 1)
            // 不基于注解,将Excel内容读至List<List<String>>对象内
            List<List<String>> lists = ExcelUtils.getInstance().readExcel2List(path, offsetLine, 2, sheetIndex);
            System.out.println("读取Excel至String数组：");
            for (List<String> list : lists) {
                System.out.println(list);
            }

            // 2)
            // 基于注解,将Excel内容读至List<Student2>对象内
            // 验证读取转换函数Student2ExpelConverter
            // 注解 `@ExcelField(title = "是否开除", order = 5, readConverter =  Student2ExpelConverter.class)`
            List<Student2> students = ExcelUtils.getInstance().readExcel2Objects(path, Student2.class, offsetLine, sheetIndex);
            System.out.println("读取Excel至对象数组(支持类型转换)：");
            for (Student2 st : students) {
                System.out.println(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeExcleTests() {
        String path = "./Student2.xlsx";
        List<Student2> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Student2(10000L + i, "学生" + i, new Date(), 201, i % 2 == 0));
        }
        try {
            ExcelUtils.getInstance().exportObjects2Excel(list, Student2.class, true, "sheet0", true, path);
        } catch (Excel4JException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
