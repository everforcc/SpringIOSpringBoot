package cn.cc.json.service;

import cn.cc.json.dto.JSONDto;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JSONService {

    /**
     * 循环依赖
     * 复现需要注释掉这一行
     * JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask(); // 解决循环引用问题
     */
    public JSONArray circular() {
//        List<ParamDto> paramDtoList = new ArrayList<>();

//        JSONDto pre = new JSONDto();
//        pre.setName("name");
//        pre.setDate(new Date());

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < 3; i++) {
            JSONDto jsonDto = new JSONDto();
            jsonDto.setDescription("第几个对象: " + i);
//            jsonDto.setDescription("写死对象");
            jsonDto.setName("name");
            jsonArray.add(jsonDto);
        }

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < 3; i++) {
            jsonObject.put("k", i);
            jsonObject.put("key0", jsonArray);
        }

        JSONArray jsonArray2 = new JSONArray();
        for (int i = 0; i < 3; i++) {
            jsonArray2.add(jsonObject);
        }

//        return JSON.toJSONString(jsonArray2);
        return jsonArray2;
    }

    /**
     * 格式化日期
     *
     * @return 格式化之后的数据
     */
    public JSONDto formatDate() {
        JSONDto jsonDto = new JSONDto();
        jsonDto.setDate(new Date());
        return jsonDto;
    }

}
