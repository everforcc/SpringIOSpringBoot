package cn.cc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class Sp02AopApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("aaaaaaaaaa");
        log.info("info...测试测试测试");
        Assertions.assertEquals(2, 1 + 2);
    }

}