/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-26 18:32
 * Copyright
 */

package com.cc.sp90utils.excle;

import com.github.crab2died.ExcelUtils;
import com.github.crab2died.exceptions.Excel4JException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcleTests {


    @Test
    public void readExcleTests() {
        // 从弟几行开始,第一行是0
        int offsetLine = 0;
        int sheetIndex = 0;
        String path = "./ExcleDto.xlsx";

        try {
            List<ExcleDto> excleDtoList = ExcelUtils.getInstance().readExcel2Objects(path,
                    ExcleDto.class, offsetLine, sheetIndex);
            excleDtoList.forEach(e -> {
                System.out.println("name: " + e.getName() + "\r\n password: " + e.getPassword());
            });
        } catch (Excel4JException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void writeExcleTests() {

        String path = "./ExcleDto.xlsx";

        int size = 100;

        List<ExcleDto> excleDtoList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            excleDtoList.add(new ExcleDto("name" + i, "pas" + i));
        }
        try {
            ExcelUtils.getInstance().exportObjects2Excel(excleDtoList, ExcleDto.class, path);
        } catch (Excel4JException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
