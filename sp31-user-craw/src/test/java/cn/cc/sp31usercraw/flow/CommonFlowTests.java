/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-13 17:41
 * Copyright
 */

package cn.cc.sp31usercraw.flow;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommonFlowTests {

    @Autowired
    INovelCommonFlowService iNovelCommonFlowService;

    @Before
    public void config() {
        System.setProperty("webdriver.chrome.driver", "C:/everforcc/java/environment\\driver\\chromedriver.exe");
    }

    @Test
    public void msg() {
        String url = "https://m.kxwx.cc/book/242833/";
        NovelConfigDto novelConfigDto = new NovelConfigDto();
        novelConfigDto.setNovelMsgTileXR("//div[@class='info i4']//h3//text()");
        novelConfigDto.setNovelMsgAuthXR("//div[@class='info i4']//p[1]//text()");
        novelConfigDto.setNovelMsgDescriptionXR("//div[@class='intro box']//text()");
        iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);
    }

    @Test
    public void menu() {
        String url = "https://m.kxwx.cc/book/242833/";
        NovelConfigDto novelConfigDto = new NovelConfigDto();
        novelConfigDto.setNovelMsgTileXR("//div[@class='info i4']//h3//text()");
        novelConfigDto.setNovelMsgAuthXR("//div[@class='info i4']//p[1]//text()");
        novelConfigDto.setNovelMsgDescriptionXR("//div[@class='intro box']//text()");

        NovelMsgDto msgDto = iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);
        //
        //novelConfigDto.setNovelCapterMenuXR("");
        //novelConfigDto.setNovelCapterUrlListXR("//div[@id='chapterlist']//a//@href");
        novelConfigDto.setNovelCapterPageNextButtonXR("//div[@class='paging']//li[4]");
        novelConfigDto.setNovelCapterUrlListXR("//ul[@id='chapterlist']//a//@href");// chapterlist
        NovelMsgDto novelMsgDto = iNovelCommonFlowService.getMenu(url, novelConfigDto, msgDto);
        System.out.println(novelMsgDto);
    }

    @Test
    void content() {
        String url = "https://m.kxwx.cc/book/242833/";
        NovelConfigDto novelConfigDto = new NovelConfigDto();
        novelConfigDto.setNovelMsgTileXR("//div[@class='info i4']//h3//text()");
        novelConfigDto.setNovelMsgAuthXR("//div[@class='info i4']//p[1]//text()");
        novelConfigDto.setNovelMsgDescriptionXR("//div[@class='intro box']//text()");

        NovelMsgDto msgDto = iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);
        //
        //novelConfigDto.setNovelCapterMenuXR("");
        //novelConfigDto.setNovelCapterUrlListXR("//div[@id='chapterlist']//a//@href");
        novelConfigDto.setNovelCapterPageNextButtonXR("//div[@class='paging']//li[4]");
        novelConfigDto.setNovelCapterUrlListXR("//ul[@id='chapterlist']//a//@href");// chapterlist
        NovelMsgDto novelMsgDto = iNovelCommonFlowService.getMenu(url, novelConfigDto, msgDto);
        System.out.println(novelMsgDto);

        novelConfigDto.setNovelCapterNameXR("//strong[@class='l jieqi_title']//text()");//
        novelConfigDto.setNovelContentXR("//div[@id='content']");
        //novelConfigDto
        List<String> novelCapterUrlList = novelMsgDto.getNovelCapterUrlList();
        for (String novelCapterUrl : novelCapterUrlList) {
            NovelContentDto novelContentDto = iNovelCommonFlowService.getContent(novelCapterUrl, novelConfigDto, novelMsgDto);
            System.out.println(novelConfigDto);
        }
    }

}
