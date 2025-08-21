package cn.cc.alarm.dto;

/**
 * 布防组数据传输对象
 * 
 * 用途：
 * - 用于布防组信息的数据传输
 * - 对应数据库表arming_group
 * - 支持布防组的增删改查操作
 * 
 * 数据说明：
 * - 布防组是设备的分组单位
 * - 一个设备只能归属一个布防组
 * - 一个布防组可以包含多个设备
 * - 布防组有自己的周计划和撤防状态
 * 
 * 业务规则：
 * - 布防组可以启用或禁用
 * - 禁用的布防组不会影响设备的事件上报判定
 * - 布防组名称应该具有业务含义
 */
public class ArmingGroupDto {

	/**
	 * 布防组ID
	 * 
	 * 说明：
	 * - 主键，唯一标识一个布防组
	 * - 用于关联设备、周计划、撤防记录等
	 * - 建议使用有意义的ID，便于业务理解
	 */
	private Long id;

	/**
	 * 布防组名称
	 * 
	 * 说明：
	 * - 布防组的业务名称
	 * - 应该具有描述性，便于管理和识别
	 * - 建议使用中文名称，如"办公区域"、"仓库区域"等
	 */
	private String name;

	/**
	 * 布防组状态
	 * 
	 * 状态说明：
	 * - 1：启用状态，正常参与布防判定
	 * - 0：禁用状态，不参与布防判定
	 * 
	 * 业务影响：
	 * - 禁用的布防组，其设备不会上报事件
	 * - 禁用的布防组，撤防操作无效
	 */
	private Integer status;

	/**
	 * 获取布防组ID
	 * 
	 * @return 布防组ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置布防组ID
	 * 
	 * @param id 布防组ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取布防组名称
	 * 
	 * @return 布防组名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置布防组名称
	 * 
	 * @param name 布防组名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取布防组状态
	 * 
	 * @return 布防组状态（1=启用，0=禁用）
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置布防组状态
	 * 
	 * @param status 布防组状态（1=启用，0=禁用）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}


