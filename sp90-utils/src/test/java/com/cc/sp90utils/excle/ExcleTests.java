/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-26 18:32
 * Copyright
 */

package com.cc.sp90utils.excle;

import com.alibaba.fastjson.JSONObject;
import com.cc.sp90utils.excle.modules.Student2;
import com.github.crab2died.ExcelUtils;
import com.github.crab2died.exceptions.Excel4JException;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcleTests {

    @Test
    public void readUserExcleTests() {
        int offsetLine = 2;
        int limitLine = Integer.MAX_VALUE;
        int sheetIndex = 0;
        File file = new File("F:\\Cache\\Tencent\\718497737\\FileRecv\\王文庆用户系统注册-20221020151720.xlsx");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            List<UcodeUser> ucodeUserList = ExcelUtils.getInstance().readExcel2Objects(fileInputStream, UcodeUser.class, offsetLine, limitLine, sheetIndex);
            System.out.println(ucodeUserList.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Excel4JException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从文件路径读取数据
     */
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

    /**
     * 从文件流，读取数据
     */
    @Test
    public void readExcleStreamTests() {

        // 开始读取行坐标(默认0)
        int offsetLine = 0;
        // 最大读取行数(默认表尾)
        int limitLine =  Integer.MAX_VALUE;
        // Sheet索引(默认0)
        int sheetIndex = 0;
        String path = "./Student2.xlsx";
        try (InputStream inputStream = Files.newInputStream(Paths.get(path))) {
            /*
             * offsetLine
             * limitLine
             * sheetIndex
             */
            List<Student2> lists = ExcelUtils.getInstance().readExcel2Objects(inputStream, Student2.class, 1, Integer.MAX_VALUE, 0);
            System.out.println("读取Excel至String数组：");
            for (Student2 list : lists) {
                System.out.println(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件数据
     */
    @Test
    public void writeExcleTests() {
        String path = "./Student2.xlsx";
        List<Student2> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
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

    @Test
    public void tJson() {
        Student2 student2 = new Student2(10000L, "学生", new Date(), 201, true);
        System.out.println(student2.toString());
    }

    @Test
    public void jsonToDto() {
        String json = "{\"classes\":201,\"date\":1658990859538,\"expel\":true,\"id\":10000,\"name\":\"学生\"}";
        Student2 student2 = JSONObject.parseObject(json, Student2.class);
        System.out.println(student2.toString());
    }

}
