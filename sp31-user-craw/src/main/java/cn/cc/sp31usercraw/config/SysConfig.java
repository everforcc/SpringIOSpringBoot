/**
 * @Description
 * @Author everforcc
 * @Date 2023-06-01 16:54
 * Copyright
 */

package cn.cc.sp31usercraw.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sysconfig")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig {

    /**
     * 文件保存的根目录
     * 1. 跟路径
     * 2. 小说名
     * 3. 章节名
     */
    private String fileRoot;

    private String chromedriver;

}
