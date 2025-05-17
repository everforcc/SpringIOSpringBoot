package cn.cc.upkeep;

import lombok.Data;

/**
 * 联系工作人员类，用于存储联系工作人员的信息。
 */
@Data
public class ContactStaff {
    /**
     * 工作人员ID
     */
    private int id;
    /**
     * 工作人员姓名
     */
    private String name;
    /**
     * 工作人员联系方式
     */
    private String contactInfo;
    /**
     * 工作时间
     */
    private String workingHours;
    /**
     * 客服电话
     */
    private String customerServicePhone;
}