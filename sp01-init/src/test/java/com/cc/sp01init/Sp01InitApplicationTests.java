package com.cc.sp01init;

import com.cc.sp01init.pojo.Dog;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class Sp01InitApplicationTests {

    @Autowired
    Dog dogg;

    @Test
    void contextLoads() {
      log.info(dogg.toString());
    }

}
