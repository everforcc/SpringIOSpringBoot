package cn.cc.controller;

import cn.cc.constant.RuleCacheConstants;
import cn.cc.dto.PCarInfo;
import cn.cc.service.IRuleService;
import cn.cc.utils.KieUtils;
import cn.cc.utils.ReloadDroolsRules;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @Description : 测试drools
 * @Author : GKL
 * @Date: 2024-04-29 10:01
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ReloadDroolsRules reloadDBDroolsRules;
    @Autowired
    ReloadDroolsRules reloadFileDroolsRules;
    @Autowired
    IRuleService ruleTempOnceDrlImpl;
    @Autowired
    IRuleService ruleTempDurationDrlImpl;
    @Autowired
    IRuleService ruleVIPFreeDrlImpl;
    @Autowired
    IRuleService ruleVIPMonthDrlImpl;
    //    @Autowired
//    RedisTemplate<String,BigDecimal> redisTemplate;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @PostConstruct
    public void init() {
        // 1. 按次收费
        redisTemplate.opsForValue().set(RuleCacheConstants.RULE_COUNT_DRL, new BigDecimal(222).toString(), 30L, TimeUnit.MINUTES);
        // 2. 按时间计费
        // 3. 按照时间段计费

        // 10. 会员
        // 11. 免费
        // 12. 包天
        // 13. 包月
        // 14. 包季度
        // 15. 包年
    }

    /**
     * 测试drools
     */
    @GetMapping("/park")
    public void tDrools() {
        KieSession kieSession = KieUtils.getKieSession();
        PCarInfo pCarInfo = new PCarInfo();
        pCarInfo.setCarType(1);
        pCarInfo.setVipLevel(0);

        kieSession.insert(pCarInfo);
        kieSession.setGlobal("ruleTempOnceDrlImpl", ruleTempOnceDrlImpl);
        kieSession.setGlobal("ruleTempDurationDrlImpl", ruleTempDurationDrlImpl);
        kieSession.setGlobal("ruleVIPFreeDrlImpl", ruleVIPFreeDrlImpl);
        kieSession.setGlobal("ruleVIPMonthDrlImpl", ruleVIPMonthDrlImpl);
//        kieSession.insert(ruleCountDrlImpl);
//        kieSession.insert(ruleVIPFreeDrlImpl);
        // 监听器
        kieSession.addEventListener(new AgendaEventListener() {
            @Override
            public void matchCreated(MatchCreatedEvent event) {

            }

            @Override
            public void matchCancelled(MatchCancelledEvent event) {

            }

            @Override
            public void beforeMatchFired(BeforeMatchFiredEvent event) {

            }

            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {

            }

            @Override
            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {

            }

            @Override
            public void agendaGroupPushed(AgendaGroupPushedEvent event) {

            }

            @Override
            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

            }

            @Override
            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

            }

            @Override
            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

            }

            @Override
            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

            }
        });
        int ruleFiredCount = kieSession.fireAllRules();
        log.info("触发了" + ruleFiredCount + "条规则");
    }

    /**
     * 重新加载drl规则
     */
    @GetMapping("/reloaddb")
    public void reloadDB() {
        String drlName = "park";
        reloadDBDroolsRules.reload(drlName);
    }

    @GetMapping("/reloadfile")
    public void reloadFile() {
        String drlName = "park";
        reloadFileDroolsRules.reload(drlName);
    }

}
