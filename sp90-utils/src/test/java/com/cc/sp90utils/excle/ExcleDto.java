/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-26 18:44
 * Copyright
 */

package com.cc.sp90utils.excle;

import com.github.crab2died.annotation.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcleDto {

    @ExcelField(title = "姓名", order = 1)
    private String name;

    @ExcelField(title = "密码", order = 2)
    private String password;

}
