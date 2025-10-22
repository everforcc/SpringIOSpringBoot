package cn.cc.lkl.sdk.init;

import cn.cc.lkl.sdk.config.LKLPayConfig;
import cn.cc.lkl.util.CertUtil;
import com.lkl.laop.sdk.Config2;
import com.lkl.laop.sdk.LKLSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * spring 初始化的时候用
 * lkl相关的参数
 */
@Component
@Slf4j
public class LKLInitConfig {

    @Resource
    LKLPayConfig lklPayConfig;

    @PostConstruct
    public void init() {

        log.info("LKLInitConfig init: {}", lklPayConfig.toString());
        //方式4：
        Config2 config = new Config2();
        config.setAppId(lklPayConfig.getAppId());
        config.setSerialNo(lklPayConfig.getSerialNo());
        config.setPriKey(CertUtil.convertToStandardFormat(lklPayConfig.getPriKeyStrLine()));
        // 修复：使用拉卡拉公钥证书用于验签，而不是使用私钥
        config.setLklCer(CertUtil.convertToStandardFormat(lklPayConfig.getLklCerStrLine()));
        config.setLklNotifyCer(CertUtil.convertToStandardFormat(lklPayConfig.getLklCerStrLine()));
//        config.setPriKey(priKeyStr);
//        config.setLklCer(lklNotifyCerStr);
//        config.setLklNotifyCer(lklNotifyCerStr);
        config.setServerUrl(lklPayConfig.getServerUrl());
        config.setSm4Key(lklPayConfig.getSm4Key());
        boolean init = LKLSDK.init(config);
        log.info("LKLSDK.init:" + init);
    }

}