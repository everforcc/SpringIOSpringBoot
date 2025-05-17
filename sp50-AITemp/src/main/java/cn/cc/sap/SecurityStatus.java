package cn.cc.sap;

// 引入Lombok依赖
import lombok.Data;

/**
 * 安防状态类，用于管理安防系统的布防/撤防状态。
 */
@Data
public class SecurityStatus {
    /**
     * 状态唯一标识符
     */
    private String id;

    /**
     * 当前状态（例如：布防、撤防）
     */
    private String status; // 布防、撤防
}