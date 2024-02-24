/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-25 22:39
 * Copyright
 */

package cn.cc.sp31usercraw.dodo;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import com.cc.sp90utils.commons.web.HttpParamUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 初始化配置后保存的位置
 */
@Getter
@Setter
@Slf4j
public class NovelConfigDo extends HashMap {

    public NovelConfigDo() {
    }

    public void putConfig(NovelConfigDto novelConfigDto) {
        String rootUrl = novelConfigDto.getRootUrl();
        if (containsKey(rootUrl)) {
            log.info("网站 [{}] 配置已存在", rootUrl);
        } else {
            put(rootUrl, novelConfigDto);
            log.info("网站 [{}] 配置已新增", rootUrl);
        }
    }

    public NovelConfigDto getConfig(String url) {
        String rootUrl = HttpParamUtils.getRootUrl(url);

        if (!containsKey(rootUrl)) {
            throw new RuntimeException("地址[ " + rootUrl + " ]配置不存在");
        } else {
            log.info("正在读 [{}] 取配置", rootUrl);
        }

        return (NovelConfigDto) get(rootUrl);
    }

    public boolean contains(String url) {
        log.info("校验 [{}] 配置是否存在", url);
        String rootUrl = HttpParamUtils.getRootUrl(url);
        return containsKey(rootUrl);
    }


}
