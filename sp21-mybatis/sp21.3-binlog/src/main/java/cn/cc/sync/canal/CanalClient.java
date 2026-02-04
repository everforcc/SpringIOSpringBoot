package cn.cc.sync.canal;

import cn.cc.sync.canal.impl.CanalSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
//@Component
public class CanalClient implements InitializingBean {

    @Resource
    private CanalSync canalSync;

    @Override
    public void afterPropertiesSet() throws Exception {
        canalSync.flow();
    }

}
