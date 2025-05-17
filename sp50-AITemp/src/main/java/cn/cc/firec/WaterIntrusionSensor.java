package cn.cc.firec;

// 引入Lombok依赖
import lombok.Data;

/**
 * 水浸类，用于管理水浸传感器的状态和信息。
 */
@Data
public class WaterIntrusionSensor {
    /**
     * 水浸传感器唯一标识符
     */
    private String id;
    /**
     * 水浸传感器名称
     */
    private String name;
    /**
     * 水浸传感器状态（例如：工作中、故障中）
     */
    private String status; // 工作中、故障中
}