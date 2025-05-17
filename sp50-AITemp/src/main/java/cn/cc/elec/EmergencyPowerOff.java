package cn.cc.elec;

import lombok.Data;

/**
 * 紧急断电类，用于管理紧急断电状态。
 */
@Data
public class EmergencyPowerOff {
    /**
     * 当前状态（通电或断电）
     */
    private String status; // "通电" 或 "断电"
}