package cn.cc.sap;

// 引入Lombok依赖
import lombok.Data;

/**
 * 门磁传感器类，用于管理门磁的状态和信息。
 */
@Data
public class MagneticSensor {
    /**
     * 门磁唯一标识符
     */
    private String id;
    /**
     * 门磁名称
     */
    private String name;
    /**
     * 门磁状态（例如：工作中、停用中）
     */
    private String status; // 工作中、停用中
}