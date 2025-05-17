package cn.cc.elec;

import lombok.Data;

/**
 * 电表类，用于记录电表的相关信息。
 */
@Data
public class ElectricMeter {
    /**
     * 总用电量（单位：KWH）
     */
    private String totalElectricity;
    
    /**
     * 月用电量（单位：KWH）
     */
    private String monthlyElectricity;
    
    /**
     * 周用电量（单位：KWH）
     */
    private String weeklyElectricity;
    
    /**
     * 日用电量（单位：KWH）
     */
    private String dailyElectricity;
}