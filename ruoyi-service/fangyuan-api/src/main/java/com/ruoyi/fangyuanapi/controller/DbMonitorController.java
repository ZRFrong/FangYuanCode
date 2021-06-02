package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.fangyuanapi.service.IDbMonitorService;
import com.ruoyi.fangyuanapi.utils.MonitorCloudRequestUtils;
import com.ruoyi.system.domain.monitor.DbMonitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 视频监控设备 提供者
 * 
 * @author zheng
 * @date 2021-05-25
 */
@RestController
@Api("monitor")
@RequestMapping("monitor")
public class DbMonitorController extends BaseController
{
	
	@Autowired
	private IDbMonitorService dbMonitorService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{deviceRegisterCode}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbMonitor get(@ApiParam(name="id",value="视屏设备ID",required=true)  @PathVariable("deviceRegisterCode") String deviceRegisterCode)
	{
		return dbMonitorService.selectDbMonitorById(deviceRegisterCode);
	}

	/**
	 * 查询视频监控设备类型列表
	 */
	@GetMapping("listMonitorType")
	@ApiOperation(value = "查询视频监控设备类型列表" , notes = "视频监控设备类型列表")
	public R listMonitorType()
	{
		MonitorCloudRequestUtils.getDevice("11");
		return result(dbMonitorService.selectDbMonitorTypeList());
	}

	/**
	 * 查询视频监控设备列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询视频监控设备列表" , notes = "视频监控设备列表")
	public R list(@ApiParam(name="DbMonitor",value="传入json格式",required=true) DbMonitor dbMonitor)
	{
		startPage();
        return result(dbMonitorService.selectDbMonitorList(dbMonitor));
	}

	/**
	 * 根据大棚ID询视频通道列表
	 */
	@GetMapping("listVideoChannel/{landId}")
	@ApiOperation(value = "根据大棚ID询视频通道列表" , notes = "视频监控设备列表")
	public R listVideoChannel(@ApiParam(name="landId",value="大棚ID",required=true) @PathVariable Long landId)
	{
		return R.rows(dbMonitorService.selectVideoChannel(landId));
	}
	
	
	/**
	 * 新增保存视频监控设备
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存视频监控设备" , notes = "新增保存视频监控设备")
	public R addSave(@ApiParam(name="DbMonitor",value="传入json格式",required=true) @RequestBody DbMonitor dbMonitor)
	{		
		return toAjax(dbMonitorService.insertDbMonitor(dbMonitor));
	}

	/**
	 * 同步视频通道
	 */
	@PostMapping("syncChannel/{monitorId}")
	@ApiOperation(value = "同步视频通道" , notes = "同步视频通道")
	public R syncChannel(@ApiParam(name="monitorId",value="视频设备ID",required=true) @PathVariable Long monitorId)
	{
		return toAjax(dbMonitorService.syncChannel(monitorId));
	}

	/**
	 * 修改保存视频监控设备
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存视频监控设备" , notes = "修改保存视频监控设备")
	public R editSave(@ApiParam(name="DbMonitor",value="传入json格式",required=true) @RequestBody DbMonitor dbMonitor)
	{		
		return toAjax(dbMonitorService.updateDbMonitor(dbMonitor));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除视频监控设备" , notes = "删除视频监控设备")
	public R remove(@ApiParam(name="ids",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbMonitorService.deleteDbMonitorByIds(ids));
	}

	/**
	 * 获取设备通道列表
	 */
	@PostMapping("listChannel")
	@ApiOperation(value = "获取设备通道列表" , notes = "获取设备通道列表")
	public R listChannel(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId
			,@ApiParam(name="pageNo",value="页数",required=true)@PathVariable Integer pageNo
			,@ApiParam(name="pageSize",value="页大小",required=true)@PathVariable Integer pageSize)
	{
		return R.data(dbMonitorService.listChannel(monitorId,pageNo,pageSize));
	}

	/**
	 * 设备抓图
	 */
	@PostMapping("getCapture/{monitorId}/{channel}")
	@ApiOperation(value = "删除视频监控设备" , notes = "删除视频监控设备")
	public R getCapture(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId,@ApiParam(name="channel",value="通道",required=true)@PathVariable Integer channel)
	{
		return R.ok(dbMonitorService.getCapture(monitorId,channel));
	}

	/**
	 * 开启云平台控制
	 * @param monitorId 设备ID
	 * @param channel 通道
	 * @param command 操作命令
	 * @param speed 云台转速
	 */
	@PostMapping("startPtz/{monitorId}/{channel}/{command}/{speed}")
	@ApiOperation(value = "开启云平台控制" , notes = "开启云平台控制")
	public R startPtz(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId,
			@ApiParam(name="channel",value="通道编号",required=true)@PathVariable Integer channel
			,@ApiParam(name="command",value="操作命令",required=true)@PathVariable Integer command
			,@ApiParam(name="speed",value="云台转速",required=true)@PathVariable Integer speed)
	{
		dbMonitorService.startPtz(monitorId,channel,command,speed);
		return R.ok();
	}

	/**
	 * 停止云平台控制
	 * @param monitorId 设备ID
	 * @param channel 通道
	 * @param command 操作命令
	 */
	@PostMapping("stopPtz/{monitorId}/{channel}/{command}/{speed}")
	@ApiOperation(value = "停止云平台控制" , notes = "停止云平台控制")
	public R stopPtz(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId,
					  @ApiParam(name="channel",value="通道编号",required=true)@PathVariable Integer channel
			,@ApiParam(name="command",value="操作命令",required=true)@PathVariable Integer command) {

		dbMonitorService.stopPtz(monitorId,channel,command);
		return R.ok();
	}

	/**
	 * 获取预置位列表
	 * @param monitorId 设备ID
	 * @param channel 通道
	 * @param pageNo 页数
	 * @param pageSize 页大小
	 */
	@PostMapping("listPreset/{monitorId}/{channel}/{pageNo}/{pageSize}")
	@ApiOperation(value = "停止云平台控制" , notes = "停止云平台控制")
	public R listPreset(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId,
					 @ApiParam(name="channel",value="通道编号",required=true)@PathVariable Integer channel
			,@ApiParam(name="pageNo",value="页数",required=true)@PathVariable Integer pageNo
			,@ApiParam(name="pageSize",value="页大小",required=true)@PathVariable Integer pageSize) {

		return R.data(dbMonitorService.listPreset(monitorId,channel,pageNo,pageSize));
	}




	/**
	 * 添加预置位
	 * @param monitorId 设备ID
	 * @param channel 通道
	 * @param presetIndex 预置位索引
	 * @param name 预置位名称
	 */
	@PostMapping("addPreset/{monitorId}/{channel}/{presetIndex/name")
	@ApiOperation(value = "添加预置位" , notes = "添加预置位")
	public R addPreset(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId,
						  @ApiParam(name="channel",value="通道编号",required=true)@PathVariable Integer channel
			,@ApiParam(name="presetIndex",value="预置位索引",required=true)@PathVariable Integer presetIndex
			,@ApiParam(name="name",value="名称",required=true)@PathVariable String name) {
		return R.ok(dbMonitorService.addPreset(monitorId,channel,presetIndex,name));
	}

	/**
	 * 修改预置位
	 * @param monitorId 设备ID
	 * @param channel 通道
	 * @param presetIndex 预置位索引
	 * @param name 预置位名称
	 */
	@PostMapping("updatePreset/{monitorId}/{channel}/{presetIndex/name")
	@ApiOperation(value = "修改预置位" , notes = "修改预置位")
	public R updatePreset(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId,
						@ApiParam(name="channel",value="通道编号",required=true)@PathVariable Integer channel
			,@ApiParam(name="presetIndex",value="预置位索引",required=true)@PathVariable Integer presetIndex
			,@ApiParam(name="name",value="名称",required=true)@PathVariable String name) {
		dbMonitorService.updatePreset(monitorId,channel,presetIndex,name);
		return R.ok();
	}

	/**
	 * 删除预置位
	 * @param monitorId 设备ID
	 * @param channel 通道
	 * @param presetIndex 预置位索引
	 */
	@PostMapping("deletePreset/{monitorId}/{channel}/{presetIndex")
	@ApiOperation(value = "删除预置位" , notes = "删除预置位")
	public R deletePreset(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId,
						@ApiParam(name="channel",value="通道编号",required=true)@PathVariable Integer channel
			,@ApiParam(name="presetIndex",value="预置位索引",required=true)@PathVariable Integer presetIndex) {
		dbMonitorService.deletePreset(monitorId,channel,presetIndex);
		return R.ok();
	}

	/**
	 * 激活预置位
	 * @param monitorId 设备ID
	 * @param channel 通道
	 * @param presetIndex 预置位索引
	 */
	@PostMapping("invokePreset/{monitorId}/{channel}/{presetIndex")
	@ApiOperation(value = "激活预置位" , notes = "激活预置位")
	public R invokePreset(@ApiParam(name="monitorId",value="视屏设备ID",required=true) @PathVariable long monitorId,
			 @ApiParam(name="channel",value="通道编号",required=true)@PathVariable Integer channel
			,@ApiParam(name="presetIndex",value="预置位索引（范围：1-255）",required=true)@PathVariable Integer presetIndex) {
		dbMonitorService.invokePreset(monitorId,channel,presetIndex);
		return R.ok();
	}

	/**
	 *  获取指定设备通道的直播/回放视频地址
	 * @param param 获取视屏通道地址参数
	 */
	@PostMapping("getVideo")
	@ApiOperation(value = "获取指定设备通道的直播" , notes = "获取指定设备通道的直播/回放视频地址")
	public R getVideo(@ApiParam(name="param",value="获取视屏通道地址参数",required=true) JSONObject param) {
		return R.data(dbMonitorService.getVideo(param));
	}

	/**
	 * 开启直播/回放
	 * @param videoUrl 视频播放地址
	 * @return liveId 视频流ID
	 */
	@PostMapping("startVideo")
	@ApiOperation(value = "开启视频流" , notes = "开启直播/回放")
	public R startVideo(@ApiParam(name="videoUrl",value="视频播放地址",required=true) String videoUrl) {
		return R.data(dbMonitorService.startVideo(videoUrl));
	}

	/**
	 * 关闭直播/回放
	 * @param liveId 视频流ID
	 */
	@PostMapping("stopVideo")
	@ApiOperation(value = "关闭视频流" , notes = "关闭直播/回放")
	public R stopVideo(@ApiParam(name="liveId",value="视频流ID") String liveId,@ApiParam(name="videoUrl",value="视频流地址")String videoUrl) {
		dbMonitorService.stopVideo(liveId,videoUrl);
		return R.ok();
	}

	/**
	 * 视频流心跳监测
	 * @param liveId 视频流ID
	 */
	@PostMapping("heartbeatVideoStream/{liveId}")
	@ApiOperation(value = "heartbeatVideoStream" , notes = "流心跳监测")
	public R heartbeatVideoStream(@ApiParam(name="liveId",value="视频流ID",required=true)@PathVariable String liveId) {
		dbMonitorService.heartVideoStream(liveId);

		return R.ok();
	}




}
