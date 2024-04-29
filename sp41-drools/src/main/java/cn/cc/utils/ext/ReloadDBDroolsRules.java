package cn.cc.utils.ext;

import cn.cc.constant.RuleEngineConstants;
import cn.cc.utils.ReloadDroolsRules;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.builder.KieFileSystem;
import org.springframework.stereotype.Component;

/**
 * @Description : 加载数据库的数据
 * @Author : GKL
 * @Date: 2024-04-29 10:16
 */
@Slf4j
@Component
public class ReloadDBDroolsRules extends ReloadDroolsRules {

    private static final String rule = "package drl\n" +
            "import cn.cc.sp41drools.dto.PCarInfo\n" +
            "import cn.cc.sp41drools.service.IRuleService\n" +
            "\n" +
            "global cn.cc.sp41drools.service.service.RuleCountDrlImpl ruleCountDrlImpl\n" +
            "global cn.cc.sp41drools.service.service.RuleDurationDrlImpl ruleDurationDrlImpl\n" +
            "global cn.cc.sp41drools.service.service.RuleVIPFreeDrlImpl ruleVIPFreeDrlImpl\n" +
            "global cn.cc.sp41drools.service.service.RuleVIPMonthDrlImpl ruleVIPMonthDrlImpl\n" +
            "\n" +
            "dialect  \"java\"\n" +
            "\n" +
            "rule \"特殊车辆\"\n" +
            "    when p : PCarInfo(0==carType)\n" +
            "    then\n" +
            "        ruleVIPFreeDrlImpl.dealFee(p);\n" +
            "end\n" +
            "\n" +
            "rule \"普通车辆\"\n" +
            "    when p : PCarInfo(1==carType && 0==vipLevel)\n" +
            "    then\n" +
            "        ruleDurationDrlImpl.dealFee(p);\n" +
            "end\n";

    @Override
    protected void loadRule(String drlName, KieFileSystem kfs) {
        //        String path = "src/main/resources/rules/address.drl";
        String path = "src/main/resources/" + RuleEngineConstants.RULES_PATH + drlName + ".drl";
        // 从数据库加载的规则
//        kfs.write(path, "package plausibcheck.adress\n\n import com.neo.drools.model.Address;\n import com.neo.drools.model.fact.AddressCheckResult;\n\n rule \"Postcode 6 numbers\"\n\n    when\n  then\n        System.out.println(\"打印日志：更新rules成功!\");\n end");
        kfs.write(path, rule);
        log.info("从数据库加载drl规则");
    }
}
