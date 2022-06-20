/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-13 17:41
 * Copyright
 */

package cn.cc.sp31usercraw.flow;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NovelCommonFlowServiceTests {

    @Autowired
    INovelCommonFlowService iNovelCommonFlowService;

    void getContent(){
        String url = "https://www.wenku8.net/novel/1/1973/75974.htm";
        NovelConfigDto novelConfigDto = new NovelConfigDto();
        NovelContentDto novelContentDto = iNovelCommonFlowService.getContent(url, novelConfigDto, null);
        System.out.println(novelConfigDto.toString());
    }

}
