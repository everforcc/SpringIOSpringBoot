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

    private String str;

    private long num;

    private Date createTime;

}

