package cn.cc.utils.ext;

import cn.cc.dto.RuleDrlDto;
import cn.cc.mapper.RuleDrlMapper;
import cn.cc.utils.ReloadDroolsRules;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.builder.KieFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
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
            "import cn.cc.dto.PCarInfo\n" +
            "import cn.cc.service.IRuleService\n" +
            "\n" +
            "global cn.cc.service.service.RuleTempOnceDrlImpl ruleTempOnceDrlImpl\n" +
            "global cn.cc.service.service.RuleTempDurationDrlImpl ruleTempDurationDrlImpl\n" +
            "global cn.cc.service.service.RuleVIPFreeDrlImpl ruleVIPFreeDrlImpl\n" +
            "global cn.cc.service.service.RuleVIPMonthDrlImpl ruleVIPMonthDrlImpl\n" +
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
            "        ruleTempDurationDrlImpl.dealFee(p);\n" +
            "end\n";

    @Autowired
    RuleDrlMapper ruleDrlMapper;

    @Override
    protected void loadRule(RuleDrlDto ruleDrlDto, KieFileSystem kfs) {
        ruleDrlDto = ruleDrlMapper.selectRule(ruleDrlDto.getRuleId());
        //        String path = "src/main/resources/rules/address.drl";
//        String path = "src/main/resources/" + RuleEngineConstants.RULES_PATH + drlName + ".drl";
        String path = ruleDrlDto.getPackages() + ruleDrlDto.getFileName();
        // 从数据库加载的规则
//        kfs.write(path, "package plausibcheck.adress\n\n import com.neo.drools.model.Address;\n import com.neo.drools.model.fact.AddressCheckResult;\n\n rule \"Postcode 6 numbers\"\n\n    when\n  then\n        System.out.println(\"打印日志：更新rules成功!\");\n end");
        kfs.write(path, ruleDrlDto.getRule());
        log.info("从数据库加载drl规则");
    }
}
