package cn.cc.sp73readability.service.impl;

import cn.cc.sp73readability.constants.RedisKeys;
import cn.cc.sp73readability.model.ChapterTask;
import cn.cc.sp73readability.service.TaskQueueService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Redis List/Set 的任务队列与去重实现。
 */
@Service
public class TaskQueueServiceImpl implements TaskQueueService {

    private final RedisTemplate<String, Object> redisTemplate;

    public TaskQueueServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void pushCatalogUrl(String catalogUrl) {
        redisTemplate.opsForList().leftPush(RedisKeys.QUEUE_CATALOG, Objects.requireNonNull(catalogUrl));
    }

    @Override
    public Optional<String> popCatalogUrl() {
        Object val = redisTemplate.opsForList().rightPop(RedisKeys.QUEUE_CATALOG, 1, TimeUnit.SECONDS);
        return Optional.ofNullable(val).map(Object::toString);
    }

    @Override
    public void pushChapterTask(ChapterTask task) {
        redisTemplate.opsForList().leftPush(RedisKeys.QUEUE_CHAPTER, Objects.requireNonNull(task));
    }

    @Override
    public Optional<ChapterTask> popChapterTask() {
        Object val = redisTemplate.opsForList().rightPop(RedisKeys.QUEUE_CHAPTER, 1, TimeUnit.SECONDS);
        return Optional.ofNullable(val).map(o -> (ChapterTask) o);
    }

    @Override
    public void markCrawled(String urlHash) {
        redisTemplate.opsForSet().add(RedisKeys.SET_CRAWLED, Objects.requireNonNull(urlHash));
    }

    @Override
    public boolean isCrawled(String urlHash) {
        SetOperations<String, Object> ops = redisTemplate.opsForSet();
        Boolean exists = ops.isMember(RedisKeys.SET_CRAWLED, Objects.requireNonNull(urlHash));
        return Boolean.TRUE.equals(exists);
    }
}

