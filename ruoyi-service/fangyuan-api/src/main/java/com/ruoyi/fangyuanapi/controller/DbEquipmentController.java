package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.service.*;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.RemoteTcpService;
import com.ruoyi.system.feign.SendSmsClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据心跳名查询站号
     * @since: 2.0.0
     * @param heartbeatText
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/5/20 21:11
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("selectByHeartbeatText/{heartbeatText}")
    public String selectByHeartbeatText(@PathVariable("heartbeatText") String heartbeatText){
        return dbEquipmentService.selectByHeartbeatText(heartbeatText);
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

    /**
     * 修改设备的在线状态
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/7 17:28
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PutMapping("updateEquipmentIsOnline")
    public void updateEquipmentIsOnline(String heartbeat,Integer isOnline){
        dbEquipmentService.updateEquipmentIsOnline(heartbeat,isOnline);
    }

    /**
     * 批量生成设备接口  完成心跳例子： pisitai-00032-dapeng
     * @param prefix  心跳前缀 例; pisitai-
     * @param suffix  心跳后缀 例: -dapeng
     * @param openInterval 心跳中间数字开区间：00032
     * @param closedInterval 心跳中间数字闭区间: 00050
     * @author: ZHAOXIAOSI
     * @return R
     */
    @PostMapping("batchEquipment")
    public R batchEquipment(String prefix,String suffix,String openInterval,String closedInterval){
        if (StringUtils.isEmpty(prefix) || StringUtils.isEmpty(suffix) || StringUtils.isEmpty(openInterval) || StringUtils.isEmpty(closedInterval)){
            return R.error();
        }
        Integer rows = 0;
        try {
            rows = dbEquipmentService.batchInsertEquipment(prefix,suffix,openInterval,closedInterval);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows > 0 ? R.ok(rows+"") : R.error(500,"该区间所有的设备已存在！");
    }

    //产品批次
    @Autowired
    private IDbProductBatchService dbProductBatchService;

    //设备模板
    @Autowired
    private IDbEquipmentTempService dbEquipmentTempService;


    @Autowired
    private IDbEquipmentComponentService dbEquipmentComponentService;

    @Autowired
    private IDbFunctionDisplayService dbFunctionDisplayService;

    /***
     * 1.0数据向2.0数据转换
     * @since: 2.0.0
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/16 9:40
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("updateEquipmentData")
    @Transactional
    public R updateEquipmentData(){
        List<DbEquipment> dbEquipments =  dbEquipmentService.selectAllDbEquipment();
        for (DbEquipment equipment : dbEquipments) {
            String text = equipment.getHandlerText();
            if (StringUtils.isEmpty(text)){
                continue;
            }
            List<Map<String,Object>> o = (List<Map<String, Object>>) JSON.parse(text);
            if (o.size()<=1 || o == null ){
                continue;
            }
            for (Map<String, Object> map : o) {
                DbEquipmentComponent component = DbEquipmentComponent.builder()
                        .equipmentName(map.get("checkName") + "")
                        .createTime(new Date())
                        .heartbeatText(equipment.getHeartbeatText())
                        .equipmentNo(equipment.getEquipmentNo()+"")
                        .equipmentId(equipment.getEquipmentId())
                        .build();
                int i = dbEquipmentComponentService.insertDbEquipmentComponent(component);
                //功能id
                map.put("functionId", component.getId());
                //开启时间
                map.put("startDate",null);
                //开关状态
                map.put("switchState",1);
                //允许定时
                map.put("isScheduled",1);
                //在线状态 放到dto中
                //map.put("isOline",1);
                //进度量
                if (map.get("checkCode").equals("1") || map.get("checkCode").equals("2") ){
                    component.setFunctionLogo("1,2,3");
                    //进度条
                    map.put("percentage",500);
                    //是否允许滑动
                    map.put("isSlide", 1);
                }else {
                    component.setFunctionLogo("2,3");
                }
                if (map.get("checkCode").equals("3")){
                    map.put("isScheduled",0);
                }
                component.setSpList(JSON.toJSONString(map,SerializerFeature.WriteMapNullValue));
                dbEquipmentComponentService.updateDbEquipmentComponent(component);
            }
            String s = JSON.toJSONString(o,SerializerFeature.WriteMapNullValue);
            System.out.println("Json序列化之后:  "+s);
            equipment.setHandlerText(s);
            dbEquipmentService.updateDbEquipment(equipment);
        }
        return R.ok("数据转换已完成！");
    }

    public static void main(String[] args){
        List<Map<String,Object>> s = (List<Map<String, Object>>) JSON.parse("[{\"spList\":[{}]}]");
        System.out.println(s);
    }

}