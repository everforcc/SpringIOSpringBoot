package cn.cc.alarm.dto;

import java.time.LocalDate;

/**
 * 布防组今日撤防掩码数据传输对象
 * 
 * 用途：
 * - 用于数据库表arming_group_today_mask的数据传输
 * - 存储每日的撤防掩码信息，用于Redis宕机后的数据恢复
 * - 支持按日期查询和更新撤防状态
 * 
 * 数据说明：
 * - 每个布防组每天只能有一条撤防记录
 * - 掩码数据为6字节（48位），表示当天的撤防状态
 * - 1表示需要撤防，0表示按周计划执行
 * 
 * 生命周期：
 * - 创建：用户点击撤防按钮时
 * - 更新：同一布防段内重复撤防时更新掩码
 * - 清理：可定期清理历史数据（建议保留30天）
 */
public class ArmingGroupTodayMaskDto {

	/**
	 * 主键ID
	 * 
	 * 说明：
	 * - 自增主键
	 * - 用于唯一标识每条撤防记录
	 */
	private Long id;

	/**
	 * 布防组ID
	 * 
	 * 说明：
	 * - 关联arming_group表的id字段
	 * - 表示该撤防记录属于哪个布防组
	 */
	private Long groupId;

	/**
	 * 业务日期
	 * 
	 * 说明：
	 * - 撤防记录对应的日期
	 * - 格式：YYYY-MM-DD
	 * - 与groupId组成唯一约束
	 */
	private LocalDate bizDate;

	/**
	 * 撤防掩码数据
	 * 
	 * 数据格式：
	 * - 6字节的二进制数据
	 * - 48位位图，表示当天的撤防状态
	 * - 1表示需要撤防，0表示按周计划执行
	 * 
	 * 掩码计算：
	 * - 根据周计划和当前时间计算
	 * - 支持连续布防区间的撤防
	 * - 多次撤防做并集操作
	 */
	private byte[] maskBits;

	/**
	 * 创建时间
	 * 
	 * 说明：
	 * - 记录首次创建的时间戳
	 * - 用于数据审计和清理
	 */
	private LocalDate createdAt;

	/**
	 * 获取主键ID
	 * 
	 * @return 主键ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键ID
	 * 
	 * @param id 主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
	 * 获取业务日期
	 * 
	 * @return 业务日期
	 */
	public LocalDate getBizDate() {
		return bizDate;
	}

	/**
	 * 设置业务日期
	 * 
	 * @param bizDate 业务日期
	 */
	public void setBizDate(LocalDate bizDate) {
		this.bizDate = bizDate;
	}

	/**
	 * 获取撤防掩码数据
	 * 
	 * @return 6字节的撤防掩码数据
	 */
	public byte[] getMaskBits() {
		return maskBits;
	}

	/**
	 * 设置撤防掩码数据
	 * 
	 * @param maskBits 6字节的撤防掩码数据
	 */
	public void setMaskBits(byte[] maskBits) {
		this.maskBits = maskBits;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return 创建时间
	 */
	public LocalDate getCreatedAt() {
		return createdAt;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createdAt 创建时间
	 */
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
}
