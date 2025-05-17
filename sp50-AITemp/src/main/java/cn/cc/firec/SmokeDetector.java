package cn.cc.firec;

// 引入Lombok依赖
import lombok.Data;

/**
 * 烟感类，用于管理烟感的状态和信息。
 */
@Data
public class SmokeDetector {
    /**
     * 烟感唯一标识符
     */
    private String id;
    /**
     * 烟感名称
     */
    private String name;
    /**
     * 烟感状态（例如：工作中、故障中）
     */
    private String status; // 工作中、故障中
}