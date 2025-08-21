package cn.cc.alarm;

/**
 * Redis 键名约定。
 * 
 * 键名设计原则：
 * - 使用冒号分隔的层次结构
 * - 前缀表示业务模块
 * - 包含数据类型和ID信息
 * - 便于管理和监控
 * 
 * 键名规范：
 * - dev:gid：设备组映射Hash
 * - arm:sch:g:{groupId}：布防组周位图
 * - arm:mask:today:g:{groupId}：今日撤防掩码
 */
public final class ArmingKeys {

	private ArmingKeys() {}

	/**
	 * 设备→组映射 Hash：field=deviceId, value=groupId。
	 * 
	 * 数据结构：
	 * - 类型：Redis Hash
	 * - 键名：dev:gid
	 * - 字段：设备ID（字符串）
	 * - 值：布防组ID（字符串）
	 * - 过期时间：无（永久有效）
	 * 
	 * 用途：
	 * - 快速查询设备所属的布防组
	 * - 支持设备组关系的动态调整
	 * 
	 * @return Redis Hash键名
	 */
	public static String deviceGroupKey() {
		return "dev:gid";
	}

	/**
	 * 组的周位图：42字节（336比特）。
	 * 
	 * 数据结构：
	 * - 类型：Redis String
	 * - 键名：arm:sch:g:{groupId}
	 * - 值：42字节的二进制数据
	 * - 过期时间：无（永久有效）
	 * 
	 * 数据格式：
	 * - 336位位图，表示一周的布防计划
	 * - 7天×48槽位 = 336位
	 * - 1表示布防，0表示撤防
	 * 
	 * @param groupId 布防组ID
	 * @return Redis String键名
	 */
	public static String groupWeekBitmapKey(long groupId) {
		return "arm:sch:g:" + groupId;
	}

	/**
	 * 今日撤防掩码：6字节（48比特），TTL 到当日午夜。
	 * 
	 * 数据结构：
	 * - 类型：Redis String
	 * - 键名：arm:mask:today:g:{groupId}
	 * - 值：6字节的二进制数据
	 * - 过期时间：到当日午夜（自动清理）
	 * 
	 * 数据格式：
	 * - 48位位图，表示今天的撤防状态
	 * - 1天×48槽位 = 48位
	 * - 1表示需要撤防，0表示按周计划执行
	 * 
	 * 生命周期：
	 * - 创建：用户点击撤防按钮时
	 * - 过期：当日午夜自动过期
	 * - 恢复：第二天自动恢复周计划
	 * 
	 * @param groupId 布防组ID
	 * @return Redis String键名
	 */
	public static String groupTodayMaskKey(long groupId) {
		return "arm:mask:today:g:" + groupId;
	}
}


