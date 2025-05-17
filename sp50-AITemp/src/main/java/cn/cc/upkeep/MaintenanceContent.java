package cn.cc.upkeep;

import lombok.Data;

/**
 * 保养内容类，用于记录具体的保养内容信息。
 */
@Data
public class MaintenanceContent {
    /**
     * 保养内容ID
     */
    private int id;
    /**
     * 保养内容描述
     */
    private String description;
    /**
     * 保养日期
     */
    private String date;
    /**
     * 保养状态（例如：未完成、已完成）
     */
    private String status;
}