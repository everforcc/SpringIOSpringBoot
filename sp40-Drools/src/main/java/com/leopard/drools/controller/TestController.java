package com.leopard.drools.controller;

import com.leopard.drools.common.utils.KieUtils;
import com.leopard.drools.pojo.QueryParam;
import com.leopard.drools.service.ReloadDroolsRules;
import com.leopard.drools.service.RuleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description : API调用实现
 * @Author : GKL
 * @Date: 2024-04-26 13:35
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    private RuleEngineService ruleEngineService;

    @Autowired
    private ReloadDroolsRules reloadDroolsRules;

    @RequestMapping("/param")
    public void param (){
        QueryParam queryParam1 = new QueryParam() ;
        queryParam1.setParamId("1");
        queryParam1.setParamSign("+");
        QueryParam queryParam2 = new QueryParam() ;
        queryParam2.setParamId("2");
        queryParam2.setParamSign("-");
        QueryParam queryParam3 = new QueryParam() ;
        queryParam3.setParamId("3");
        queryParam3.setParamSign("-");
        // 入参
        KieUtils.getKieSession().insert(queryParam2) ;
        KieUtils.getKieSession().insert(queryParam3) ;
        KieUtils.getKieSession().insert(queryParam1) ;
        KieUtils.getKieSession().insert(this.ruleEngineService) ;
        // 返参
        KieUtils.getKieSession().fireAllRules() ;
    }

    @RequestMapping("/reload")
    public String reload (String ruleName) throws Exception {
        // 返参
        reloadDroolsRules.reload(ruleName);
        return "新规则重载成功";
    }

}