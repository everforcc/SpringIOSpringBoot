package cn.cc.test.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cc_webmagic")
public class WebmagicDto {

    private Long id;

    private String str;

    private long num;

    private Date createTime;

    public WebmagicDto(String str, long num, Date createTime) {
        this.str = str;
        this.num = num;
        this.createTime = createTime;
    }
}

