package cn.cc.alarm.dto;

/**
 * 布防组周位图数据传输对象
 * 
 * 用途：
 * - 用于布防组周计划的数据传输
 * - 对应数据库表arming_group_schedule
 * - 存储一周的布防计划信息
 * 
 * 数据说明：
 * - 周位图是布防系统的核心数据
 * - 336位位图表示一周的布防计划
 * - 7天×48槽位 = 336位
 * - 1表示布防，0表示撤防
 * 
 * 业务规则：
 * - 每个布防组只能有一个周计划
 * - 周计划按周循环执行
 * - 周计划可以被今日撤防覆盖
 * - 今日撤防过期后恢复周计划
 */
public class ArmingGroupScheduleDto {

	/**
	 * 布防组ID
	 * 
	 * 说明：
	 * - 关联arming_group表的id字段
	 * - 表示该周计划属于哪个布防组
	 * - 作为主键，确保每个组只有一个周计划
	 */
	private Long groupId;

	/**
	 * 周位图数据
	 * 
	 * 数据格式：
	 * - 42字节的二进制数据
	 * - 336位位图，表示一周的布防计划
	 * - 7天×48槽位 = 336位
	 * - 1表示布防，0表示撤防
	 * 
	 * 位图结构：
	 * - 第0-47位：周一（00:00-23:59）
	 * - 第48-95位：周二（00:00-23:59）
	 * - 第96-143位：周三（00:00-23:59）
	 * - 第144-191位：周四（00:00-23:59）
	 * - 第192-239位：周五（00:00-23:59）
	 * - 第240-287位：周六（00:00-23:59）
	 * - 第288-335位：周日（00:00-23:59）
	 * 
	 * 每天48个槽位：
	 * - 每个槽位代表30分钟
	 * - 槽位0：00:00-00:30
	 * - 槽位1：00:30-01:00
	 * - ...
	 * - 槽位47：23:30-24:00
	 */
	private byte[] weekBits;

	/**
	 * 获取布防组ID
	 * 
	 * @return 布防组ID
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * 设置布防组ID
	 * 
	 * @param groupId 布防组ID
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * 获取周位图数据
	 * 
	 * @return 42字节的周位图数据
	 */
	public byte[] getWeekBits() {
		return weekBits;
	}

	/**
	 * 设置周位图数据
	 * 
	 * @param weekBits 42字节的周位图数据
	 */
	public void setWeekBits(byte[] weekBits) {
		this.weekBits = weekBits;
	}
}


