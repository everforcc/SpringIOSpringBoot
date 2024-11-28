package cn.cc.valid.dto;

import cn.cc.core.validate.ISave;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidDto {

    @NotEmpty(message = "name不能为空")
    private String name;

    @NotEmpty(groups = {ISave.class},message = "str不允许为null")
    private String str;

    private Integer age;

    @Email(message = "email自定义异常消息")
    private String email;

    private List<String> list;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
    }

}
