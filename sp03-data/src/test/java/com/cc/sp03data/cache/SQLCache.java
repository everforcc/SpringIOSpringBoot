package com.cc.sp03data.cache;

import com.cc.sp03data.dto.NovelDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class SQLCache {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Test
    public void level_1() {
        /*InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();

        log.info("一 次查询---start");
        NovelDto n1 = session.selectOne("com.cc.sp03data.mapper.NovelMapper.queryByID", 1);
        log.info("一 次查询---end");

        log.info("二 次查询---start");
        NovelDto n2 = session.selectOne("com.cc.sp03data.mapper.NovelMapper.queryByID", 0);
        log.info("二 次查询---end");

        // 从日志中可以看出这次并没有发起sql查询，一级缓存
        log.info("三 次查询---start");
        NovelDto n3 = session.selectOne("com.cc.sp03data.mapper.NovelMapper.queryByID", 1);
        log.info("三 次查询---end");

        session.commit();

        session.close();
    }

    @Test
    public void level_2() {
        /*InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session_1 = sqlSessionFactory.openSession();
        SqlSession session_2 = sqlSessionFactory.openSession();

        log.info("session_1 次查询---start");
        NovelDto n1 = session_1.selectOne("com.cc.sp03data.mapper.NovelMapper.queryByID", 1);
        log.info("session_1 次查询---end");
        session_1.commit();
        session_1.close();

        log.info("session_2 次查询---start");
        NovelDto n2 = session_2.selectOne("com.cc.sp03data.mapper.NovelMapper.queryByID", 1);
        log.info("session_2 次查询---end");
        session_2.commit();
        session_2.close();
    }

}
