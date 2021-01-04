package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.fangyuanapi.service.IDbUserService;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.RemoteTcpService;
import com.ruoyi.system.feign.SendSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;

import java.util.Date;

/**
 * 设备 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("设备")
@RequestMapping("dbEquipment")
public class DbEquipmentController extends BaseController {

    @Autowired
    private IDbEquipmentService dbEquipmentService;

    @Autowired
    private IDbLandService dbLandService;

    @Autowired
    private IDbUserService userService;


    @Autowired
    private SendSmsClient sendSmsClient;


    @Autowired
    private RemoteTcpService remoteTcpService;
    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{equipmentId}")
    public DbEquipment get(@PathVariable("equipmentId") Long equipmentId) {
        return dbEquipmentService.selectDbEquipmentById(equipmentId);
    }

    /**
     * 查询设备列表
     */
    @GetMapping("list")
    public R list( DbEquipment dbEquipment) {
        startPage();
        return result(dbEquipmentService.selectDbEquipmentList(dbEquipment));
    }

    /**
     * 查询设备列表
     */
    @GetMapping("listOnlyEquipment")
    public R listOnly(  DbEquipment dbEquipment) {
        return R.data(dbEquipmentService.selectDbEquipmentList(dbEquipment));
    }



    /**
     * 新增保存设备
     */
    @PostMapping("save")
    public R addSave(@RequestBody  DbEquipment dbEquipment) {
        dbEquipment.setCreateTime(new Date());
        dbEquipment.setIsPause(0);
        dbEquipment.setIsFault(0);
        return toAjax(dbEquipmentService.insertDbEquipment(dbEquipment));
    }

    /**
     * 修改保存设备
     */
    @PostMapping("update")
    public R editSave(@RequestBody  DbEquipment dbEquipment) {
        return toAjax(dbEquipmentService.updateDbEquipment(dbEquipment));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(dbEquipmentService.deleteDbEquipmentByIds(ids));
    }





    /*
     * 发送短信验证码   设备验证
     * */
    @GetMapping("sendSms")
    public R sendSms(@ApiParam(name = "设备id", value = "设备id", required = true) DbQrCode qrCode) {
        Long adminUserId = qrCode.getAdminUserId();
        DbUser dbUser = userService.selectDbUserById(adminUserId);
        String phone = dbUser.getPhone();
        R r = sendSmsClient.sendSms(phone, "1", "2");
        return r;
    }

    /*
     * 获取当前设备的温湿度变化曲线    24小时   2小时间隔   温湿度空气土壤
     * */

    @GetMapping("getTrend/{intervalTime}/{beforeTime}/{landid}")
    @ApiOperation(value = "获取当前设备的温湿度变化曲线", notes = "获取当前设备的温湿度变化曲线")
    public R getTrend(@ApiParam(name = "间隔时间单位小时") @PathVariable("intervalTime")String intervalTime,
                      @ApiParam(name = "之前多久时间")@PathVariable("beforeTime") String beforeTime,
                      @ApiParam(name = "设备id")@PathVariable("landid") String landid) {
        Date type = DateUtils.getType(DateUtils.HOUR, -Integer.parseInt(beforeTime));
        DbLand dbLand = dbLandService.selectDbLandById(Long.valueOf(landid));
        String equipmentIds = dbLand.getEquipmentIds();
        String equipmentId=null;
        if (StringUtils.isEmpty(equipmentIds)){
            return R.error("当前土地未绑定设备！");
        }
        String[] split = equipmentIds.split(",");
        if (split.length==0){
            equipmentId=equipmentIds;
        }else {
            equipmentId=split[0];
        }

        DbEquipment dbEquipment = dbEquipmentService.selectDbEquipmentById(Long.valueOf(equipmentId));
        String path=dbEquipment.getHeartbeatText()+"_"+dbEquipment.getEquipmentNoString();
        String s = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, type);
        String s1 = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getNowDate());
        R r= remoteTcpService.intervalState(s,s1,intervalTime,path);
        return r;
    }






}