/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-06 13:53
 * Copyright
 */

package cn.cc.sp31usercraw.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NovelMsgDto {

    /* 标题 */
    private String title;

    /* 作者 */
    private String auther;

    /* 描述 */
    private String description;

    private String indexMenuUrl;

    private List<String> novelCapterUrlList;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
    }
}
