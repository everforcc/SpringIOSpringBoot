package cn.cc.alarm.dto;

/**
 * 布防组周位图 DTO。
 */
public class ArmingGroupScheduleDto {

	private Long groupId;
	private byte[] weekBits;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public byte[] getWeekBits() {
		return weekBits;
	}

	public void setWeekBits(byte[] weekBits) {
		this.weekBits = weekBits;
	}
}


