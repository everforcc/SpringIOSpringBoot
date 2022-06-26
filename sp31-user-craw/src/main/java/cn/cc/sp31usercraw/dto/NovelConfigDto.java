/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-06 11:12
 * Copyright
 */

package cn.cc.sp31usercraw.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 小说的配置对象
 * XR: xsoupRegex
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NovelConfigDto {

    @NotNull(message = "网站跟地址不能为空")
    private String rootUrl;

    /* 分类一般只有一组 */
    // 小说分类名
    //@NotNull(message = "网站分类正则不能为空")
    private String typeNameXR;
    // 小说分类地址
    private String typeUrlXR;

    /* 小说列表 */
    private String noveNameListXR;
    private String noveUrlListXR;
    /* 小说列表分页 */
    private String novePagesXR;

    /* 小说的各种信息 */
    //private String novelMsgXR;
    /* 小说名 */
    private String novelMsgTileXR;
    /* 小说简介 */
    private String novelMsgDescriptionXR;
    /* 小说作者 */
    private String novelMsgAuthXR;

    /* 小说章节信息 */

    /**
     * 1. 如果不为空,点了才出现章节目录
     * 2. 如果为空,当前页内就是目录
     */
    private String novelCapterMenuXR;
    /* 章节列表名字 */
    private String novelCapterNameListXR;
    /* 章节列表地址 */
    private String novelCapterUrlListXR;
    /* 下一页 */
    private String novelCapterPageNextUrlXR;

    /* 小说内容 */
    private String novelContentXR;
    private List<String> novelContentXRFlowList;
    /* 章节名，在小说内容页面获取 */
    private String novelCapterNameXR;
    /* 小说内容分页 */
    private String novelContentUrlPageXR;

    /* 对最后的数据进行处理 */

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
