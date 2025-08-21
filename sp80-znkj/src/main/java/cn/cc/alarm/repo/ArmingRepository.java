package cn.cc.alarm.repo;

import cn.cc.alarm.dto.ArmingGroupScheduleDto;
import cn.cc.alarm.mapper.ArmingMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 数据访问（MyBatis 版本）。
 */
@Repository
public class ArmingRepository {

	private final ArmingMapper mapper;

	public ArmingRepository(ArmingMapper mapper) {
		this.mapper = mapper;
	}

	public Optional<Long> findGroupIdByDeviceId(long deviceId) {
		return Optional.ofNullable(mapper.findGroupIdByDeviceId(deviceId));
	}

	public Optional<byte[]> findWeekBitmapByGroupId(long groupId) {
		ArmingGroupScheduleDto dto = mapper.findGroupSchedule(groupId);
		return Optional.ofNullable(dto == null ? null : dto.getWeekBits());
	}

	public void upsertDeviceGroup(long deviceId, long groupId) {
		mapper.upsertDeviceGroup(deviceId, groupId);
	}

	public void upsertGroupSchedule(long groupId, byte[] weekBits) {
		mapper.upsertGroupSchedule(groupId, weekBits);
	}

	public byte[] findTodayMask(long groupId, LocalDate date) {
		return mapper.findTodayMask(groupId, java.sql.Date.valueOf(date));
	}

	public void upsertTodayMask(long groupId, LocalDate date, byte[] maskBits) {
		mapper.upsertTodayMask(groupId, java.sql.Date.valueOf(date), maskBits);
	}

	/**
	 * 查询所有设备组映射关系（用于数据预热）
	 * 
	 * @return 设备组映射列表
	 */
	public List<ArmingMapper.DeviceGroupMapping> findAllDeviceGroupMappings() {
		return mapper.findAllDeviceGroupMappings();
	}

	/**
	 * 查询所有布防组的周位图（用于数据预热）
	 * 
	 * @return 组周位图列表
	 */
	public List<ArmingMapper.GroupWeekBitmap> findAllGroupWeekBitmaps() {
		return mapper.findAllGroupWeekBitmaps();
	}

	/**
	 * 查询指定日期的所有撤防掩码（用于数据预热）
	 * 
	 * @param date 业务日期
	 * @return 撤防掩码列表
	 */
	public List<ArmingMapper.GroupTodayMask> findTodayMasks(LocalDate date) {
		return mapper.findTodayMasks(java.sql.Date.valueOf(date));
	}
}


