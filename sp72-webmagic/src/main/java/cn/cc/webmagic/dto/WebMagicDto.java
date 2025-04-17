package cn.cc.webmagic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.codecraft.webmagic.ResultItems;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("cc_webmagic")
public class WebMagicDto extends ResultItems {

    /**
     * 主键
     */
    private Long id;

    /**
     * 章节排序号
     */
    private long num;

    /**
     * 章节标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 新增时间
     */
    private Date createTime;

}
