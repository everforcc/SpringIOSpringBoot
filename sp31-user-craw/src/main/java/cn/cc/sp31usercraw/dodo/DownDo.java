/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-26 15:33
 * Copyright
 */

package cn.cc.sp31usercraw.dodo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DownDo {

    // 文件完整地址
    private String path;
    // 文件完整内容
    private String content;
    // 是否追加
    private boolean append;

    @Override
    public String toString() {
        return "DownDo{" +
                "path='" + path + '\'' +
                ", append=" + append +
                '}';
    }
}
