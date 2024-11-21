/**
 * @Description
 * @Author everforcc
 * @Date 2022-10-20 15:57
 * Copyright
 */

package cn.cc.utils.excle;

import com.github.crab2died.annotation.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UcodeUser {

    @ExcelField(title = "名字", order = 1)
    private String  name;

    @ExcelField(title = "手机号", order = 2)
    private String  userAccount;

    @ExcelField(title = "所属部门(层级)", order = 3)
    private String deptName;

    @ExcelField(title = "角色", order = 4)
    private String roleName;

    @ExcelField(title = "成员类型", order = 5)
    private String type;

    @ExcelField(title = "是否为部门(层级)负责人", order = 6)
    private String leader;
}
