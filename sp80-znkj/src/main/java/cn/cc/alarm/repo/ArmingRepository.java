package cn.cc.alarm.repo;

import cn.cc.alarm.dto.ArmingGroupScheduleDto;
import cn.cc.alarm.mapper.ArmingMapper;
import org.springframework.stereotype.Repository;

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

	public byte[] findTodayMask(long groupId, java.time.LocalDate date) {
		return mapper.findTodayMask(groupId, java.sql.Date.valueOf(date));
	}

	public void upsertTodayMask(long groupId, java.time.LocalDate date, byte[] maskBits) {
		mapper.upsertTodayMask(groupId, java.sql.Date.valueOf(date), maskBits);
	}
}


