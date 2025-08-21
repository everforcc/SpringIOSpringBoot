package cn.cc.alarm.mapper;

import cn.cc.alarm.dto.ArmingGroupScheduleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 布防系统 MyBatis 映射接口。
 *
 * 说明：
 * - 提供设备与组、周计划、今日掩码相关的查询与写通操作
 * - 供 `cn.cc.alarm.repo.ArmingRepository` 调用
 */
@Mapper
public interface ArmingMapper {

	/**
	 * 查询设备所属布防组ID
	 * @param deviceId 设备ID
	 * @return 组ID，未配置返回null
	 */
	@Select("select group_id from arming_device where id = #{deviceId} limit 1")
	Long findGroupIdByDeviceId(@Param("deviceId") long deviceId);

	/**
	 * 查询布防组周位图
	 */
	@Select("select group_id as groupId, week_bits as weekBits from arming_group_schedule where group_id = #{groupId}")
	ArmingGroupScheduleDto findGroupSchedule(@Param("groupId") long groupId);

	/**
	 * 写通：新增或更新设备归属组
	 */
	@Update("INSERT INTO arming_device(id, group_id) VALUES(#{deviceId}, #{groupId}) ON DUPLICATE KEY UPDATE group_id = VALUES(group_id)")
	void upsertDeviceGroup(@Param("deviceId") long deviceId, @Param("groupId") long groupId);

	/**
	 * 写通：新增或更新周位图
	 */
	@Update("INSERT INTO arming_group_schedule(group_id, week_bits) VALUES(#{groupId}, #{weekBits}) ON DUPLICATE KEY UPDATE week_bits = VALUES(week_bits)")
	void upsertGroupSchedule(@Param("groupId") long groupId, @Param("weekBits") byte[] weekBits);

	/**
	 * 查询当日撤防掩码
	 */
	@Select("select mask_bits from arming_group_today_mask where group_id = #{groupId} and biz_date = #{bizDate}")
	byte[] findTodayMask(@Param("groupId") long groupId, @Param("bizDate") java.sql.Date bizDate);

	/**
	 * 写通：新增或更新当日撤防掩码
	 */
	@Update("INSERT INTO arming_group_today_mask(group_id, biz_date, mask_bits) VALUES(#{groupId}, #{bizDate}, #{maskBits}) ON DUPLICATE KEY UPDATE mask_bits = VALUES(mask_bits)")
	void upsertTodayMask(@Param("groupId") long groupId, @Param("bizDate") java.sql.Date bizDate, @Param("maskBits") byte[] maskBits);

	/**
	 * 查询所有设备组映射关系（用于数据预热）
	 * 
	 * @return 设备组映射列表
	 */
	@Select("select id as deviceId, group_id as groupId from arming_device")
	List<DeviceGroupMapping> findAllDeviceGroupMappings();

	/**
	 * 查询所有布防组的周位图（用于数据预热）
	 * 
	 * @return 组周位图列表
	 */
	@Select("select group_id as groupId, week_bits as weekBits from arming_group_schedule")
	List<GroupWeekBitmap> findAllGroupWeekBitmaps();

	/**
	 * 查询指定日期的所有撤防掩码（用于数据预热）
	 * 
	 * @param bizDate 业务日期
	 * @return 撤防掩码列表
	 */
	@Select("select group_id as groupId, mask_bits as maskBits from arming_group_today_mask where biz_date = #{bizDate}")
	List<GroupTodayMask> findTodayMasks(@Param("bizDate") java.sql.Date bizDate);

	/**
	 * 设备组映射数据传输对象
	 */
	class DeviceGroupMapping {
		private Long deviceId;
		private Long groupId;

		public Long getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(Long deviceId) {
			this.deviceId = deviceId;
		}

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}
	}

	/**
	 * 组周位图数据传输对象
	 */
	class GroupWeekBitmap {
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

	/**
	 * 组今日掩码数据传输对象
	 */
	class GroupTodayMask {
		private Long groupId;
		private byte[] maskBits;

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

		public byte[] getMaskBits() {
			return maskBits;
		}

		public void setMaskBits(byte[] maskBits) {
			this.maskBits = maskBits;
		}
	}
}


