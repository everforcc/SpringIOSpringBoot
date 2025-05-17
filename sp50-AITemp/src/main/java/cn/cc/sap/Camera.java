package cn.cc.sap;

// 引入Lombok依赖
import lombok.Data;

/**
 * 监控摄像头类，用于管理摄像头的状态和信息。
 */
@Data
public class Camera {
    /**
     * 摄像头唯一标识符
     */
    private String id;
    /**
     * 摄像头名称
     */
    private String name;
    /**
     * 摄像头状态（例如：工作中、停用中）
     */
    private String status; // 工作中、停用中
}