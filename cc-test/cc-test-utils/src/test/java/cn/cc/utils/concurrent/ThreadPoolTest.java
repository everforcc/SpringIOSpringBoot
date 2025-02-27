package cn.cc.utils.concurrent;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ThreadPoolTest {

    @Test
    public void threadSizeTest() {
        log.info("核心数: {}", ThreadPool.threadSize());
    }

}
