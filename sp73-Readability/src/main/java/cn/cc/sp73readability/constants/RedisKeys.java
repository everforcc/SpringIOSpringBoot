package cn.cc.sp73readability.constants;

/**
 * Redis Key 约定，集中管理，便于修改和复用。
 * 参考《通用小说爬虫系统设计方案》中的命名示例。
 */
public final class RedisKeys {

    private RedisKeys() {
    }

    /** 目录页待爬队列，元素为目录页 URL。 */
    public static final String QUEUE_CATALOG = "novel:queue:catalog";

    /** 章节页待爬队列，元素为序列化的 ChapterTask。 */
    public static final String QUEUE_CHAPTER = "novel:queue:chapter";

    /** 已抓取 URL 去重集合，建议存储 URL 哈希值或标准化后的 URL。 */
    public static final String SET_CRAWLED = "novel:url:crawled";

    /** 按域名存储上次访问时间，用于限速。 */
    public static final String HASH_RATE_LIMIT = "novel:rate_limit";

    /** 小说元数据热点缓存前缀，例如 novel:metadata:{novel_id}。 */
    public static final String HASH_METADATA_PREFIX = "novel:metadata:";
}

