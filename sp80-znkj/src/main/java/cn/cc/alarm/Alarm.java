package cn.cc.alarm;

/**
 * 布防模块根包标记类与文档入口。
 *
 * 模块组成：
 * - keys：`ArmingKeys` 定义Redis键名规范；
 * - time：`TimeSlotUtils` 提供半小时槽与周偏移计算；
 * - bitmap：`cn.cc.alarm.bitmap.Bitmaps` 位图转换与掩码构建；
 * - repo：`cn.cc.alarm.repo.ArmingRepository` 数据访问封装；
 * - mapper：`cn.cc.alarm.mapper.ArmingMapper` MyBatis SQL映射；
 * - service：`ArmingService` 布防判定/撤防，`ArmingDataWarmupService` 启动预热；
 * - web：`ArmingController` REST接口。
 *
 * 设计约束：
 * - 时区统一 Asia/Shanghai；
 * - 周位图336位、今日掩码48位，与 BitSet 序列化保持一致；
 * - Redis缺失时具备DB回填与TTL到午夜的容灾能力。
 */
public final class Alarm {
	private Alarm() {}
}
