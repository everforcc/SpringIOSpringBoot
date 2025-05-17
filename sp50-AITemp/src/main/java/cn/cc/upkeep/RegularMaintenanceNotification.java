package cn.cc.upkeep;

import lombok.Data;

/**
 * 定期保养通知类，用于管理定期保养的通知信息。
 */
@Data
public class RegularMaintenanceNotification {
    /**
     * 通知ID
     */
    private int id;
    /**
     * 通知周期（例如：一年、两年、三年、四年）
     */
    private String cycle;
    /**
     * 通知内容
     */
    private String content;
    /**
     * 通知状态（例如：未发送、已发送）
     */
    private String status;
}