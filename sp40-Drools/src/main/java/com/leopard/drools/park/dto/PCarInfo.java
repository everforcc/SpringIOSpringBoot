package com.leopard.drools.park.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Description : 车辆信息
 * @Author : GKL
 * @Date: 2024-04-26 15:26
 */
@Data
public class PCarInfo {

    /**
     * 车辆类型：
     * 特殊
     * 私有
     */
    private String carType;

    /**
     * 车牌号
     */
    private String carNum;

    /**
     * 会员类型
     * 0 非会员
     * 123 包天，月，季，年
     * 99 永久免费
     */
    private String vipLevel;

    /**
     * 入场时间
     */
    private Date inTime;
    /**
     * 出厂时间
     */
    private Date outTime;

}
