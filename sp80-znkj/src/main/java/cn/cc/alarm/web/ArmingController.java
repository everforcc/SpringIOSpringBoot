package cn.cc.alarm.web;

import cn.cc.alarm.service.ArmingService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST 示例：事件判定、今日撤防、写通更新。
 *
 * 说明：
 * - 该控制器仅提供演示级接口，真实生产环境应加入鉴权、审计、参数校验与幂等控制；
 * - 周位图提交时建议前端以 BASE64 传输 42 字节，后端校验长度与合法性；
 * - 撤防接口根据当前时间自动计算撤防区间，无需额外传参。
 */
@RestController
@RequestMapping("/arming")
public class ArmingController {

	private final ArmingService armingService;

	public ArmingController(ArmingService armingService) {
		this.armingService = armingService;
	}

	@GetMapping("/shouldReport/{deviceId}")
	public Map<String, Object> shouldReport(@PathVariable("deviceId") long deviceId) {
		boolean ok = armingService.shouldReport(deviceId);
		Map<String, Object> map = new HashMap<>();
		map.put("deviceId", deviceId);
		map.put("shouldReport", ok);
		return map;
	}

	@PostMapping("/defuse/{groupId}")
	public Map<String, Object> defuse(@PathVariable("groupId") long groupId) {
		boolean effective = armingService.defuseToday(groupId);
		Map<String, Object> map = new HashMap<>();
		map.put("groupId", groupId);
		map.put("status", "OK");
		map.put("effective", effective); // true=本次有效，false=同一布防段内重复点击无效
		return map;
	}

	@PostMapping("/upsertDeviceGroup")
	public Map<String, Object> upsertDeviceGroup(@RequestParam("deviceId") long deviceId,
												 @RequestParam("groupId") long groupId) {
		armingService.upsertDeviceGroup(deviceId, groupId);
		Map<String, Object> map = new HashMap<>();
		map.put("deviceId", deviceId);
		map.put("groupId", groupId);
		map.put("status", "OK");
		return map;
	}


	@PostMapping("/upsertGroupSchedule/{groupId}")
	public Map<String, Object> upsertGroupSchedule(@PathVariable("groupId") long groupId,
												  @RequestBody ArmingDto body) {
		byte[] weekBits = body == null ? null : body.getWeekBits();
		armingService.upsertGroupSchedule(groupId, weekBits);
		Map<String, Object> map = new HashMap<>();
		map.put("groupId", groupId);
		map.put("bytes", weekBits == null ? 0 : weekBits.length);
		map.put("status", "OK");
		return map;
	}

	@GetMapping("/schedule/{groupId}")
	public Map<String, Object> getSchedule(@PathVariable("groupId") long groupId,
										  @RequestParam(value = "format", required = false, defaultValue = "base64") String format) {
		String data = armingService.getGroupWeekSchedule(groupId, format);
		Map<String, Object> map = new HashMap<>();
		map.put("groupId", groupId);
		map.put("format", format);
		map.put("data", data);
		return map;
	}
}


