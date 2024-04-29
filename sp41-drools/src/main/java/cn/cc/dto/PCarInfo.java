package cn.cc.dto;

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
     * 0 特殊
     * 1 私有
     */
    private Integer carType;

    /**
     * 车牌号
     */
    private String carNum;

    /**
     * 会员类型
     * 0 免费
     * 1 天
     * 2 月
     * 3 季
     * 4 年
     * <p>
     * 9 非会员
     */
    private Integer vipLevel;

    /**
     * 临时计费规则
     * 1 按次
     * 2 时段
     * 3 时长
     * 4 分时？
     */
    private Integer tempRule;

    /**
     * 入场时间
     */
    private Date inTime;
    /**
     * 出厂时间
     */
    private Date outTime;

}
