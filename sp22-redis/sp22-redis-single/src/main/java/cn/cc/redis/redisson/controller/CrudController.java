package cn.cc.redis.redisson.controller;

import cn.cc.core.domain.R;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/msg")
public class CrudController {

    @Autowired
    RedissonClient redissonClient;

    private static final String key = "REDIS_SINGLE";

    /**
     * @return 列出缓存清单
     */
    @GetMapping("/list")
    public R<List<JSONObject>> list() {
        RKeys rKeys = redissonClient.getKeys();
        List<JSONObject> objectList = new ArrayList<>();
        rKeys.getKeysStreamByPattern("sample_bicycle:*").forEach(e -> {

            log.info("key: {}", e);
            RBucket<JSONObject> jsonObjectRBucket = redissonClient.getBucket(e);
            log.info("result: {}", jsonObjectRBucket.get());
            objectList.add(jsonObjectRBucket.get());
        });
        return R.ok(objectList);
    }

    @GetMapping("/c")
    public R<Void> c() {
        final RBucket<String> stringRBucket = redissonClient.getBucket(key);
        stringRBucket.set("value", 999, TimeUnit.SECONDS);
        log.info("新增成功");
        return R.ok();
    }

    @GetMapping("/r")
    public R<String> r() {
        final RBucket<String> stringRBucket = redissonClient.getBucket(key);
        String result = stringRBucket.get();
        log.info("缓存读取: {}", result);
        return R.ok("操作成功", result);
    }

    /**
     * Required JSON module is not loaded.
     * json 应该是高版本的所以不能用
     */
    @GetMapping("/r/{key}")
    public R<String> r(@PathVariable String key) {
        RList<String> stringRList = redissonClient.getList(key);
        List<String> stringList = stringRList.readAll();
        String result = stringList.toString();

//        final RBucket<String> stringRBucket = redissonClient.getBucket(key);
//        result = stringRBucket.get();

        log.info("缓存读取: {}", result);
        return R.ok("操作成功", result);
    }

    @GetMapping("/u")
    public R<String> u() {
        final RBucket<String> stringRBucket = redissonClient.getBucket(key);
        if (stringRBucket.isExists()) {
            stringRBucket.set("value-new", 999, TimeUnit.SECONDS);
        }
        String result = stringRBucket.get();
        log.info("缓存更新: {}", result);
        return R.ok(result);
    }

    @GetMapping("/d")
    public R<Boolean> d() {
        final RBucket<String> stringRBucket = redissonClient.getBucket(key);
        boolean result = stringRBucket.delete();
        log.info("缓存删除: {}", result);
        return R.ok(result);
    }

}
