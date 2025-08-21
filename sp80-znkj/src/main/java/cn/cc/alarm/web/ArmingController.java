package cn.cc.alarm.web;

import cn.cc.alarm.service.ArmingService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 布防撤防REST API控制器
 *
 * 功能说明：
 * - 提供设备事件上报判定接口
 * - 提供今日撤防操作接口
 * - 提供设备组关系管理接口
 * - 提供布防计划管理接口
 *
 * 安全说明：
 * - 该控制器仅提供演示级接口，真实生产环境应加入鉴权、审计、参数校验与幂等控制
 * - 周位图提交时建议前端以 BASE64 传输 42 字节，后端校验长度与合法性
 * - 撤防接口根据当前时间自动计算撤防区间，无需额外传参
 *
 * API设计原则：
 * - 返回统一的Map格式，便于前端处理
 * - 包含操作状态和结果信息
 * - 支持不同格式的数据返回
 */
@RestController
@RequestMapping("/arming")
public class ArmingController {

	/** 布防撤防服务 */
	private final ArmingService armingService;

	/**
	 * 构造函数：注入布防撤防服务
	 * 
	 * @param armingService 布防撤防服务实例
	 */
	public ArmingController(ArmingService armingService) {
		this.armingService = armingService;
	}

	/**
	 * 1. 判定设备的事件是否需要上报-ok
	 *
	 * 接口说明：
	 * - 根据当前时间和设备的布防配置，判断是否需要上报事件
	 * - 考虑周计划和今日临时撤防的影响
	 * - 返回设备ID和判定结果
	 *
	 * 判定逻辑：
	 * 1. 查询设备所属的布防组
	 * 2. 检查当前时间是否在周计划中布防
	 * 3. 检查是否被今日临时撤防覆盖
	 * 4. 综合判断是否需要上报
	 *
	 * @param deviceId 设备ID（路径参数）
	 * @return 包含设备ID和判定结果的Map
	 */
	@GetMapping("/shouldReport/{deviceId}")
	public Map<String, Object> shouldReport(@PathVariable("deviceId") long deviceId) {
		// 调用服务层进行事件上报判定
		boolean shouldReport = armingService.shouldReport(deviceId);
		
		// 构建返回结果
		Map<String, Object> result = new HashMap<>();
		result.put("deviceId", deviceId);
		result.put("shouldReport", shouldReport);
		return result;
	}

	/**
	 * 2. 执行今日撤防操作
	 *
	 * 接口说明：
	 * - 根据当前时间智能计算撤防区间
	 * - 支持重复撤防检测，避免无效操作
	 * - 撤防操作仅影响当天，第二天自动恢复
	 *
	 * 撤防规则：
	 * - 如果当前在布防段：撤防包含当前时间的连续布防区间
	 * - 如果当前在撤防段：撤防下一个连续布防区间
	 * - 同一布防段内重复撤防无效
	 *
	 * @param groupId 布防组ID（路径参数）
	 * @return 包含操作结果的Map
	 */
	@PostMapping("/defuse/{groupId}")
	public Map<String, Object> defuse(@PathVariable("groupId") long groupId) {
		// 调用服务层执行撤防操作
		boolean effective = armingService.defuseToday(groupId);
		
		// 构建返回结果
		Map<String, Object> result = new HashMap<>();
		result.put("groupId", groupId);
		result.put("status", "OK");
		result.put("effective", effective); // true=本次有效，false=同一布防段内重复点击无效
		return result;
	}

	/**
	 * 2.1 指定当日槽位执行撤防（测试/联调用）
	 *
	 * 说明：
	 * - daySlot 取值 0..47，对应 00:00-00:30 为 0，依次递增；
	 * - 仅作用于“今天”的掩码，TTL 到午夜；
	 * - 语义同 /defuse/{groupId}，唯一区别是从当前时间改为由调用方指定槽位。
	 */
	@PostMapping("/defuse/{groupId}/slot/{daySlot}")
	public Map<String, Object> defuseAtSlot(@PathVariable("groupId") long groupId,
										   @PathVariable("daySlot") int daySlot) {
		boolean effective = armingService.defuseAtSlot(groupId, daySlot);
		Map<String, Object> result = new HashMap<>();
		result.put("groupId", groupId);
		result.put("daySlot", daySlot);
		result.put("status", "OK");
		result.put("effective", effective);
		return result;
	}

	/**
	 * 3. 更新或新增设备与布防组的关联关系
	 *
	 * 接口说明：
	 * - 支持设备组关系的动态调整
	 * - 采用写通模式，确保数据一致性
	 * - 设备只能归属一个布防组
	 *
	 * 数据一致性：
	 * - 先写入MySQL数据库
	 * - 再写入Redis缓存
	 * - 确保读操作能立即获取最新数据
	 *
	 * @param deviceId 设备ID（请求参数）
	 * @param groupId 布防组ID（请求参数）
	 * @return 包含操作结果的Map
	 */
	@PostMapping("/upsertDeviceGroup")
	public Map<String, Object> upsertDeviceGroup(@RequestParam("deviceId") long deviceId,
												 @RequestParam("groupId") long groupId) {
		// 调用服务层更新设备组关系
		armingService.upsertDeviceGroup(deviceId, groupId);
		
		// 构建返回结果
		Map<String, Object> result = new HashMap<>();
		result.put("deviceId", deviceId);
		result.put("groupId", groupId);
		result.put("status", "OK");
		return result;
	}

	/**
	 * 4. 更新或新增布防组的周计划
	 *
	 * 接口说明：
	 * - 设置布防组的周位图（336位）
	 * - 支持Base64格式的42字节数据传输
	 * - 采用写通模式，确保数据一致性
	 *
	 * 数据格式：
	 * - weekBits：42字节的周位图数据
	 * - 336位表示一周的布防计划
	 * - 1表示布防，0表示撤防
	 *
	 * 安全建议：
	 * - 前端应校验数据长度和格式
	 * - 建议使用Base64编码传输
	 *
	 * @param groupId 布防组ID（路径参数）
	 * @param body 包含周位图数据的请求体
	 * @return 包含操作结果的Map
	 */
	@PostMapping("/upsertGroupSchedule/{groupId}")
	public Map<String, Object> upsertGroupSchedule(@PathVariable("groupId") long groupId,
												  @RequestBody ArmingDto body) {
		// 从请求体中提取周位图数据
		byte[] weekBits = body == null ? null : body.getWeekBits();
		
		// 调用服务层更新周计划
		armingService.upsertGroupSchedule(groupId, weekBits);
		
		// 构建返回结果
		Map<String, Object> result = new HashMap<>();
		result.put("groupId", groupId);
		result.put("bytes", weekBits == null ? 0 : weekBits.length);
		result.put("status", "OK");
		return result;
	}

	/**
	 * 查询布防组的周计划
	 *
	 * 接口说明：
	 * - 支持多种格式的数据返回
	 * - 便于前端展示和调试
	 * - 包含组ID和格式信息
	 *
	 * 返回格式：
	 * - "base64"：42字节的Base64编码字符串（默认格式）
	 * - "01"：336位的01字符串，便于人工查看
	 *
	 * @param groupId 布防组ID（路径参数）
	 * @param format 返回格式，可选值："base64"或"01"，默认为"base64"
	 * @return 包含周计划数据的Map
	 */
	@GetMapping("/schedule/{groupId}")
	public Map<String, Object> getSchedule(@PathVariable("groupId") long groupId,
										  @RequestParam(value = "format", required = false, defaultValue = "base64") String format) {
		// 调用服务层获取周计划
		String data = armingService.getGroupWeekSchedule(groupId, format);
		
		// 构建返回结果
		Map<String, Object> result = new HashMap<>();
		result.put("groupId", groupId);
		result.put("format", format);
		result.put("data", data);
		return result;
	}

	/**
	 * 5. 执行今日“重新布防”（撤销掩码）
	 *
	 * 说明：
	 * - 当前在布防段：恢复包含当前槽位的连续布防区间；
	 * - 当前在撤防段：恢复今天的下一个连续布防区间；
	 * - 仅清除今日掩码中交集部分；目标区间若未被撤防则无效。
	 */
	@PostMapping("/rearm/{groupId}")
	public Map<String, Object> rearm(@PathVariable("groupId") long groupId) {
		boolean effective = armingService.rearmToday(groupId);
		Map<String, Object> result = new HashMap<>();
		result.put("groupId", groupId);
		result.put("status", "OK");
		result.put("effective", effective);
		return result;
	}

	/**
	 * 5.1 指定当日槽位执行“重新布防”（测试/联调用）
	 */
	@PostMapping("/rearm/{groupId}/slot/{daySlot}")
	public Map<String, Object> rearmAtSlot(@PathVariable("groupId") long groupId,
										   @PathVariable("daySlot") int daySlot) {
		boolean effective = armingService.rearmAtSlot(groupId, daySlot);
		Map<String, Object> result = new HashMap<>();
		result.put("groupId", groupId);
		result.put("daySlot", daySlot);
		result.put("status", "OK");
		result.put("effective", effective);
		return result;
	}

	/**
	 * 6. 指定当日槽位查询“是否有效布防”（是否需要上报）
	 *
	 * @param deviceId 设备ID
	 * @param daySlot 当日槽位 0..47
	 */
	@GetMapping("/armedAt/{deviceId}/slot/{daySlot}")
	public Map<String, Object> isArmedAtSlot(@PathVariable("deviceId") long deviceId,
										   @PathVariable("daySlot") int daySlot) {
		boolean armed = armingService.isArmedAtSlot(deviceId, daySlot);
		Map<String, Object> result = new HashMap<>();
		result.put("deviceId", deviceId);
		result.put("daySlot", daySlot);
		result.put("armed", armed);
		result.put("status", "OK");
		return result;
	}
}


