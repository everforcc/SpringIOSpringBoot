package cn.cc.sp04mybatisplus;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@SpringBootTest
class Sp04MybatisPlusApplicationTests {

    /**
     * 测试数据源
     */
    @Resource
    private DataSource dataSource;

    @Test
    void contextLoads() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("查看数据连接池, {}",connection.getClass());
    }

}
