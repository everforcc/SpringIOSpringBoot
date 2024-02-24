/**
 * @Description
 * @Author everforcc
 * @Date 2023-06-01 18:03
 * Copyright
 */

package cn.cc.sp31usercraw.dodo;

import com.cc.sp90utils.commons.web.HttpParamUtils;
import org.junit.Test;

public class NovelConfigDoTest {

    @Test
    public void url() {
//        NovelConfigDto novelConfigDto = new NovelConfigDto();
//        novelConfigDto.setRootUrl("setRootUrl_1");
//        novelConfigDto.setTypeUrlXR("setTypeUrlXR-1");
//
//        NovelConfigDto novelConfigDto_2 = new NovelConfigDto();
//        novelConfigDto_2.setRootUrl("setRootUrl_2");
//        novelConfigDto_2.setTypeUrlXR("setTypeUrlXR_2");
//
//        NovelConfigDo novelConfigDo = new NovelConfigDo();
//        novelConfigDo.putConfig(novelConfigDto);
//        novelConfigDo.putConfig(novelConfigDto_2);
//
//        System.out.println(novelConfigDo.getConfig("setRootUrl_1").toString());
//        System.out.println(novelConfigDo.getConfig("setRootUrl_2").toString());
//
        String url = "https://www.vipxs.la/56_56024/";
        System.out.println(HttpParamUtils.getRootUrl(url));

    }

}
