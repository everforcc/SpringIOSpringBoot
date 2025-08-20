package cn.cc.alarm.web;

/**
 * 简单请求体：用于提交 42 字节的周位图。
 */
public class ArmingDto {

	private byte[] weekBits;

	public byte[] getWeekBits() {
		return weekBits;
	}

	public void setWeekBits(byte[] weekBits) {
		this.weekBits = weekBits;
	}
}


