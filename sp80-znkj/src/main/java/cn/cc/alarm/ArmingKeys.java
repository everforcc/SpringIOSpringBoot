package cn.cc.alarm;

/**
 * Redis 键名约定。
 */
public final class ArmingKeys {

	private ArmingKeys() {}

	/**
 	 * 设备→组映射 Hash：field=deviceId, value=groupId。
 	 */
	public static String deviceGroupKey() {
		return "dev:gid";
	}

	/**
 	 * 组的周位图：42字节（336比特）。
 	 */
	public static String groupWeekBitmapKey(long groupId) {
		return "arm:sch:g:" + groupId;
	}

	/**
 	 * 今日撤防掩码：6字节（48比特），TTL 到当日午夜。
 	 */
	public static String groupTodayMaskKey(long groupId) {
		return "arm:mask:today:g:" + groupId;
	}
}


