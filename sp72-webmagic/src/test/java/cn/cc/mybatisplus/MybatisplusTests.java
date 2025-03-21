package cn.cc.mybatisplus;

import cn.cc.sp01.dao.WebmagicDao;
import cn.cc.sp01.dto.WebmagicDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class MybatisplusTests {

    @Autowired
    WebmagicDao webmagicDao;

    @Test
    void mpTest() {
        List<WebmagicDto> webmagicDtoList = webmagicDao.selectList(new LambdaQueryWrapper<>(WebmagicDto.class)
                .in(WebmagicDto::getStr, "todo", "author")
                .in(WebmagicDto::getNum, 3, 4)
        );
        webmagicDtoList.forEach(System.out::println);

        WebmagicDto webmagicDto = new WebmagicDto();
        webmagicDto.setId(1L);
        webmagicDto.setNum(999);
        webmagicDto.setStr("sss");
        webmagicDao.insert(webmagicDto);

        log.info("end...");
    }

}
