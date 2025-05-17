package cn.cc.firec;

// 引入Lombok依赖
import lombok.Data;

/**
 * 灭火器类，用于管理灭火器的状态和信息。
 */
@Data
public class FireExtinguisher {
    /**
     * 灭火器唯一标识符
     */
    private String id;
    /**
     * 灭火器名称
     */
    private String name;
    /**
     * 灭火器状态（例如：正常、已使用）
     */
    private String status; // 正常、已使用
    /**
     * 灭火器有效期
     */
    private String validityPeriod;
    /**
     * 启动灭火的温度阈值
     */
    private int triggerTemperature;
}