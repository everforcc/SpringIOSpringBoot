package com.cc.sp91test.test.com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FastJsonDemo {

    /**
     * 大致有以下几种
     * 1. str转 JSONObject
     * 2. str转 JSONArray
     * 3. str转 Obj
     * 4. str转 List<JSONObject>
     * 5. List<JSONObject>转 JSONObject[]
     * 6. Obj转 str, 格式化结果
     * 7. 从JSONObject取出信息
     * 8. 便利JSON（相同结构）取出信息
     * 9. map转 JSONObject
     * 10. jsonAry 转java List
     * 11. list转 jsonAry
     * <p>
     * Ary 到 Obj
     */
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        System.out.println(objToJSONStr(map));
    }

    /**
     * 1. json字符串-简单对象型与JSONObject之间的转换
     */
    public static JSONObject strToJSONObject(String json) {
        // 方式一
        //JSONObject jsonObject1 = JSONObject.parseObject(JSON_OBJ_STR); //因为JSONObject继承了JSON，所以这样也是可以的
        // 方式二
        return JSON.parseObject(json);
    }

    /**
     * 2. json字符串-数组类型与JSONArray之间的转换
     */
    public static JSONArray strToJSONArray(String json) {
        // 方式一
        //JSONArray jsonArray1 = JSONArray.parseArray(JSON_ARRAY_STR);//因为JSONArray继承了JSON，所以这样也是可以的
        // 方式二
        return JSON.parseArray(json);
    }

    /**
     * 3. json字符串-字符串与T对象之间的转换
     */
    public static <T> T strToObj(String json, Class<T> t) { // <T extends Object>
        return JSON.parseObject(json, t);
        //JSONArray jsonArray1 = JSONArray.parseArray(JSON_ARRAY_STR);//因为JSONArray继承了JSON，所以这样也是可以的
    }

    /**
     * 5.  List<JSONObject>转 JSONObject[]
     * json数组转jsonObject集合
     * 某些情况下所有数据返回会被公共框架包裹 []
     * 如果是数组会在原先数组下，再包裹一个[] 所以要取出为list
     *
     * @param jsonArray jsonAry
     */
    private void parseArrayToObjList(JSONArray jsonArray) {
        //JSONArray jsonArray = JSONArray.parseArray(str);
        log.info(jsonArray.toJSONString());
        String json = jsonArray.getJSONObject(0).getString("key");
        List<JSONObject> datas = JSON.parseArray(json, JSONObject.class);
        JSONObject[] jsonAry = new JSONObject[datas.size()];
        // datas.toArray(jsonAry)
        System.out.println(Arrays.toString(datas.toArray(jsonAry)));
    }

    /**
     * 6. Obj转 str
     *
     * @param obj obj转str
     */
    public static <T> String objToJSONStr(T obj) {
        String jsonStr = JSONObject.toJSONString(obj, SerializerFeature.PrettyFormat);
        log.info("格式化对象json: 【{}】", jsonStr);
        return JSON.toJSON(obj).toString();
    }

    /**
     * 7. 从JSONObject取出信息
     */
    public static void jsonObjectForMsg(JSONObject jsonObject) {
        String teacherName = jsonObject.getString("String");
        log.info("teacherName 【{}】", teacherName);
        Integer teacherAge = jsonObject.getInteger("Integer");
        log.info("teacherAge 【{}】", teacherAge);
        JSONObject course = jsonObject.getJSONObject("JSONObject");
        log.info("course 【{}】", course);
        JSONArray students = jsonObject.getJSONArray("JSONArray");
        log.info("students 【{}】", students);
    }

    /**
     * 8. 便利JSON（相同结构）取出信息
     * 如果有变长但是数据类型相同的可以用这种方式来取值
     * 例如某些旧系统或者哪些,这种格式的，就要便利来取出
     * {
     * "name[0]":"1",
     * "name[1]":"2",
     * "name[2]":"3"
     * }
     *
     * @param json jsonStr
     */
    public static void jsonForeach(String json) {
        Map<Object, Object> jsonMap = JSON.parseObject(json, Map.class);
        //Map<String, Object> jsonMap = (Map<String, Object>)strToObj(json, Map.class);
        for (Map.Entry<Object, Object> entry : jsonMap.entrySet()) {
            Object k = entry.getKey();
            Object v = entry.getValue();
            log.info("便利map结果: 【{}】:【{}】", k, v);
        }
    }

    /**
     * 9. map转JSONObject
     *
     * @param map java map
     * @return jsonObj
     */
    public static JSONObject mapToJsonObject(Map<String, Object> map) {
        return new JSONObject(map);
    }

    /**
     * 10. jsonAry 转java List
     *
     * @param jsonArray jsonary
     * @param t         java class 类型
     * @param <T>       泛型
     * @return 格式化之后的javalist
     */
    public static <T> List<T> aryToT(JSONArray jsonArray, Class<T> t) {
        return jsonArray.toJavaList(t);
    }

    /**
     * 11. list转 jsonAry
     *
     * @param list java List
     * @return 格式化之后的数组
     */
    private JSONArray listToArry(List<Object> list) {
        return JSONArray.parseArray(JSON.toJSONString(list));
    }

}