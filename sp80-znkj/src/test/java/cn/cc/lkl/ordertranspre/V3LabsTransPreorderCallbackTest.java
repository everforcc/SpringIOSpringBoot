package cn.cc.lkl.ordertranspre;

import cn.cc.lkl.dto.scanpreorder.V3LabsTransPreorderCallback;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class V3LabsTransPreorderCallbackTest {

    @Test
    public void test() {
        String json = LoadFileUtil.loadJsonFromResource("聚合扫码/demo-callback.json");
        V3LabsTransPreorderCallback request = JSONObject.parseObject(json, V3LabsTransPreorderCallback.class);
        log.info("request: \r\n{}", JSONObject.toJSONString(request, SerializerFeature.PrettyFormat));
    }

    /**
     * 回调方法处理
     */
    public static void orderCallback(String json) {
        V3LabsTransPreorderCallback request = JSONObject.parseObject(json, V3LabsTransPreorderCallback.class);
        log.info("request: \r\n{}", JSONObject.toJSONString(request, SerializerFeature.PrettyFormat));
    }

}
