package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.fangyuanapi.service.IDbUserService;
import com.ruoyi.system.domain.DbQrCode;
import com.ruoyi.system.domain.DbUser;
import com.ruoyi.system.feign.SendSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;

import java.util.List;

/**
 * 设备 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("dbEquipment")
@RequestMapping("dbEquipment")
public class DbEquipmentController extends BaseController {

    @Autowired
    private IDbEquipmentService dbEquipmentService;


    @Autowired
    private IDbUserService userService;


    @Autowired
    private SendSmsClient sendSmsClient;


    @Autowired
    private RedisUtils redisUtils;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{equipmentId}")
    @ApiOperation(value = "根据id查询", notes = "查询${tableComment}")
    public DbEquipment get(@ApiParam(name = "id", value = "long", required = true) @PathVariable("equipmentId") Long equipmentId) {
        return dbEquipmentService.selectDbEquipmentById(equipmentId);
    }

    /**
     * 查询设备列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询设备列表", notes = "设备列表")
    public R list(@ApiParam(name = "DbEquipment", value = "传入json格式", required = true) DbEquipment dbEquipment) {
        startPage();
        return result(dbEquipmentService.selectDbEquipmentList(dbEquipment));
    }


    /**
     * 新增保存设备
     */
    @PostMapping("save")
    @ApiOperation(value = "新增保存设备", notes = "新增保存设备")
    public R addSave(@ApiParam(name = "DbEquipment", value = "传入json格式", required = true) DbEquipment dbEquipment) {
        return toAjax(dbEquipmentService.insertDbEquipment(dbEquipment));
    }

    /**
     * 修改保存设备
     */
    @PostMapping("update")
    @ApiOperation(value = "修改保存设备", notes = "修改保存设备")
    public R editSave(@ApiParam(name = "DbEquipment", value = "传入json格式", required = true) DbEquipment dbEquipment) {
        return toAjax(dbEquipmentService.updateDbEquipment(dbEquipment));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    @ApiOperation(value = "删除设备", notes = "删除设备")
    public R remove(@ApiParam(name = "删除的id子串", value = "已逗号分隔的id集", required = true) String ids) {
        return toAjax(dbEquipmentService.deleteDbEquipmentByIds(ids));
    }


    /*
     *扫码链接    重定向单独的页面
     * scan
     * */
    @GetMapping("scanPage")
    public R scanPage(@ApiParam(name = "设备id", value = "设备id", required = true) String equipmentId) {


        return R.error();
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
     * 生成二维码   指定网页，内部选择土地，
     * */
    @PostMapping("qrCodeGenerate")
    @ApiOperation(value = "修改保存设备", notes = "修改保存设备")
    public R qrCodeGenerate(@ApiParam(name = "DbEquipment", value = "传入json格式", required = true)  DbEquipment equipment) throws Exception {
        /*
         * 指定网址     拼接一个参数（设备id）
         * */
        int i = dbEquipmentService.qrCodeGenerate(equipment);
        return toAjax(i);
    }



    /*
    * 同步设备列表到redis
    * */
    @GetMapping("syncEquipmentList")
    @ApiOperation(value = "同步设备列表到redis", notes = "同步设备列表到redis")
    public R syncList() {

        List<DbEquipment> dbEquipments = dbEquipmentService.selectDbEquipmentList(new DbEquipment(0, 0));
        for (DbEquipment dbEquipment : dbEquipments) {
            toRedis(dbEquipment);
        }
        return toAjax(0);
    }




    public  void toRedis (DbEquipment dbEquipment){
        String s = JSON.toJSONString(dbEquipment);
        String key = RedisKeyConf.EQUIPMENT_LIST+":"+dbEquipment.getHeartbeatText()+"_"+dbEquipment.getEquipmentNo();
        redisUtils.set(key,s,redisUtils.NOT_EXPIRE);
    }








}