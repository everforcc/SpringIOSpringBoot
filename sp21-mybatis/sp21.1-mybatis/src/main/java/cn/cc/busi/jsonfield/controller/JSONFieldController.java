/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-10 16:50
 * Copyright
 */

package cn.cc.busi.jsonfield.controller;

import cn.cc.busi.jsonfield.service.JSONFieldService;
import cn.cc.core.domain.R;
import cn.cc.dto.JSONDto;
import cn.cc.dto.NovelDto;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/jsonfield")
public class JSONFieldController {

    @Resource
    JSONFieldService jsonFieldService;

    @PostMapping("/test")
    public R<NovelDto> cNovelDto(@RequestBody NovelDto novelDto) {
        return R.ok(jsonFieldService.jsonFieldT(novelDto));
    }

    @PostMapping("/list")
    public R<List<NovelDto>> list(@RequestBody NovelDto novelDto) {
        return R.ok(jsonFieldService.list(novelDto));
    }

    @GetMapping("/circular")
    public JSONArray circular() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 3; i++) {
            JSONDto jsonDto = new JSONDto();
            jsonDto.setDescription("第几个对象: " + i);
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
        return jsonArray2;
    }

}
