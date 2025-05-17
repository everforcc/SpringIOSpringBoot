// 补充包名
package cn.cc.sap;

// 引入Lombok依赖
import lombok.Data;

import java.util.Date;

/**
 * 工作日志条目类，用于记录系统的工作日志信息。
 */
@Data
public class WorkLogEntry {
    /**
     * 日志序号
     */
    private int sequence;
    /**
     * 日志名称（关联的设备或模块名称）
     */
    private String name;
    /**
     * 日志描述（事件详情）
     */
    private String description;
    /**
     * 日志记录时间
     */
    private Date date;
    /**
     * 日志状态（例如：未解决、已解决）
     */
    private String logStatus; // 未解决、已解决
    /**
     * 操作（相关操作记录）
     */
//    private String operation;
}