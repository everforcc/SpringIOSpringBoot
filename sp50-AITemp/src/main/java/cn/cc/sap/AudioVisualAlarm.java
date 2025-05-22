package cn.cc.sap;

// 引入Lombok依赖
import lombok.Data;

/**
 * 
security and protection

 * 声光报警器类，用于管理声光报警器的状态和信息。
 */
@Data
public class AudioVisualAlarm {
    /**
     * 声光报警器唯一标识符
     */
    private String id;
    /**
     * 声光报警器名称
     */
    private String name;
    /**
     * 声光报警器状态（例如：工作中、停用中）
     */
    private String status; // 工作中、停用中
}