package cn.cc.alarm.mapper;

import cn.cc.alarm.dto.ArmingGroupScheduleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArmingMapper {

	@Select("select group_id from device where id = #{deviceId} limit 1")
	Long findGroupIdByDeviceId(@Param("deviceId") long deviceId);

	@Select("select group_id as groupId, week_bits as weekBits from arming_group_schedule where group_id = #{groupId}")
	ArmingGroupScheduleDto findGroupSchedule(@Param("groupId") long groupId);

	@Update("INSERT INTO device(id, group_id) VALUES(#{deviceId}, #{groupId}) ON DUPLICATE KEY UPDATE group_id = VALUES(group_id)")
	void upsertDeviceGroup(@Param("deviceId") long deviceId, @Param("groupId") long groupId);

	@Update("INSERT INTO arming_group_schedule(group_id, week_bits) VALUES(#{groupId}, #{weekBits}) ON DUPLICATE KEY UPDATE week_bits = VALUES(week_bits)")
	void upsertGroupSchedule(@Param("groupId") long groupId, @Param("weekBits") byte[] weekBits);

	@Select("select mask_bits from arming_group_today_mask where group_id = #{groupId} and biz_date = #{bizDate}")
	byte[] findTodayMask(@Param("groupId") long groupId, @Param("bizDate") java.sql.Date bizDate);

	@Update("INSERT INTO arming_group_today_mask(group_id, biz_date, mask_bits) VALUES(#{groupId}, #{bizDate}, #{maskBits}) ON DUPLICATE KEY UPDATE mask_bits = VALUES(mask_bits)")
	void upsertTodayMask(@Param("groupId") long groupId, @Param("bizDate") java.sql.Date bizDate, @Param("maskBits") byte[] maskBits);
}


