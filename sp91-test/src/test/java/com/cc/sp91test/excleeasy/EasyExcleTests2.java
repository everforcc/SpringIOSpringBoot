/**
 * @Description
 * @Author everforcc
 * @Date 2022-09-02 15:48
 * Copyright
 */

package com.cc.sp91test.excleeasy;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyExcleTests2 {

    private static int size = 0;

    public static List<Map<String, Object>> init() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("a", "name");
        List<String> stringList1 = new ArrayList<>();
        stringList1.add("001");
        int i = 1;
        for (String s : stringList1) {
            tempMap.put("b" + i++, s);
        }

        list.add(tempMap);

        Map<String, Object> tempMap2 = new HashMap<>();
        tempMap2.put("a", "name");
        List<String> stringList2 = new ArrayList<>();
        stringList2.add("001");
        stringList2.add("002");
        stringList2.add("003");
        size = stringList2.size();
        System.out.println("stringList2.size(): " + stringList2.size());
        i = 1;
        for (String s : stringList2) {
            tempMap2.put("b" + i++, s);
        }
        list.add(tempMap2);

        Map<String, Object> tempMap3 = new HashMap<>();
        tempMap3.put("a", "name");
        List<String> stringList3 = new ArrayList<>();
        stringList3.add("001");
        stringList3.add("002");

        System.out.println("stringList2.size(): " + stringList3.size());
        i = 1;
        for (String s : stringList3) {
            tempMap3.put("b" + i++, s);
        }

        list.add(tempMap3);

        return list;
    }

    public static void getMap(int length) {
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("a", "name");
        List<String> stringList1 = new ArrayList<>();
        stringList1.add("001");
        for (int i = 1; i <= length; i++) {
            tempMap.put("b" + i++, "测试-" + i);
        }
    }

    public static int max() {
        return size;
    }

    public static void test() {
        try {
            List<Map<String, Object>> list = init();

            List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
//构造对象等同于@Excel
            entity.add(new ExcelExportEntity("物料编码", "a"));
            int max = max();
            System.out.println("max: " + max);
            for (int i = 1; i <= max(); i++) {
                System.out.println("i: " + i);
                entity.add(i, new ExcelExportEntity("合并编码" + i, "b" + i));
            }

//把我们构造好的bean对象放到params就可以了
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试title", "测试sheetName"), entity,
                    list);
            FileOutputStream fos = new FileOutputStream("E:\\filesystem\\project\\SpringIOSpringBoot\\sp90-utils\\easyExcle\\ExcelExportForMap.tt.xls");
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
//        int i = 1;
//        System.out.println(i++);
    }

}
