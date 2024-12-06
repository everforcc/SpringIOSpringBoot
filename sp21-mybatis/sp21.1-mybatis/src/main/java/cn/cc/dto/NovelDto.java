package cn.cc.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelDto {

    @JSONField(name = "uuid")
    private String newuuid;

    private String name;

    private String sourceurl;

    private String sourcename;

}
