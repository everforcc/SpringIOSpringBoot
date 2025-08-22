package cn.cc.alarm.dto.bo;

/**
 * 布防计划数据传输对象
 * 
 * 用途：
 * - 用于接收前端提交的周位图数据
 * - 支持Base64编码的42字节数据传输
 * - 作为REST API的请求体
 * 
 * 数据格式：
 * - weekBits：42字节的周位图数据
 * - 336位表示一周的布防计划（7天×48槽位）
 * - 1表示布防，0表示撤防
 * 
 * 安全建议：
 * - 前端应校验数据长度（42字节）
 * - 建议使用Base64编码传输
 * - 后端应验证数据格式和合法性
 */
public class ArmingDto {

	/**
	 * 周位图数据
	 * 
	 * 数据说明：
	 * - 42字节的二进制数据
	 * - 336位位图，表示一周的布防计划
	 * - 7天×48槽位 = 336位
	 * - 1表示布防，0表示撤防
	 * 
	 * 传输建议：
	 * - 前端使用Base64编码
	 * - 后端解码为字节数组
	 * - 校验数据长度和格式
	 */
	private byte[] weekBits;

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


