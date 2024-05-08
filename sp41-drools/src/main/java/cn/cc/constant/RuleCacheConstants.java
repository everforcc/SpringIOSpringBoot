package cn.cc.constant;

import cn.cc.dto.rule.RuleTempDuration;
import cn.cc.dto.rule.RuleTempOnce;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * @Description : 规则缓存常量
 * @Author : GKL
 * @Date: 2024-04-29 13:56
 */
@NoArgsConstructor
@Getter
@Setter
public class RuleCacheConstants {

    /**
     * 按次数计费
     */
    public static class RTempOnce {
        public static final String KEY = "RULE_ONCE_KEY";
        public static final long TIMEOUT = 30;
        public static final TimeUnit UNIT = TimeUnit.MINUTES;

        public static RuleTempOnce getValue() {
            return new RuleTempOnce();
        }

    }

    /**
     * 按时间计费
     */
    public static class RTempDuration {
        public static final String KEY = "RULE_DURATION_KEY";
        public static final long TIMEOUT = 30;
        public static final TimeUnit UNIT = TimeUnit.MINUTES;

        public static RuleTempDuration getValue() {
            return new RuleTempDuration();
        }
    }

}

