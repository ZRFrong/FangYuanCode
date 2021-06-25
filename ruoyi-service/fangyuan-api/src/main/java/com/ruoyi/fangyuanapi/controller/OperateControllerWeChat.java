package com.ruoyi.fangyuanapi.controller;

/*
 *
 * 微信小程序操作类
 * */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.aspect.*;
import com.ruoyi.fangyuanapi.dto.OperateVO;
import com.ruoyi.fangyuanapi.service.IDbEquipmentComponentService;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.fangyuanapi.service.IDbOperationRecordService;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@Api("operateWeChat")
@RequestMapping("operateWeChat")
@Slf4j
public class OperateControllerWeChat extends BaseController {


    @Autowired
    private RemoteTcpService remoteTcpService;

    @Autowired
    private IDbLandService dbLandService;

    @Autowired
    private IDbEquipmentService equipmentService;

    @Autowired
    private OperationLogUtils operationLogUtils;

    @Autowired
    private IDbEquipmentComponentService dbEquipmentComponentService;

    /*
     *列表回写    当前用户下边所有土地
     * */
    @GetMapping("getList")
    @ApiOperation(value = "查询当前用户下的操作列表", notes = "查询当前用户下的操作列表")
    public R getList() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbLand dbLand = new DbLand();
        dbLand.setDbUserId(Long.valueOf(userId));
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        /*
         * 状态查询
         * */
        R r = stateAllQuery(dbLands);
        return r.put("data",getOperateWeChatVos(dbLands));

    }

    /*
     *组件切换    当前用户下边所有土地
     * */
    @GetMapping("getListSwitch")
    @ApiOperation(value = "查询当前用户下的操作列表", notes = "查询当前用户下的操作列表")
    public R getListSwitch() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbLand dbLand = new DbLand();
        dbLand.setDbUserId(Long.valueOf(userId));
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        /*
         * 状态查询
         * */
        return R.data(getOperateWeChatVos(dbLands));

    }

    private R stateAllQuery(List<DbLand> dbLands) {
        List<DbOperationVo> operateWeChatVos = new ArrayList<>();
        for (DbLand dbLand : dbLands) {
            String equipmentIds = dbLand.getEquipmentIds();
            if (StringUtils.isEmpty(equipmentIds)) {

                continue;
            }
            for (String s : equipmentIds.split(",")) {
                DbOperationVo dbOperationVo = new DbOperationVo();
                DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(s));
                dbOperationVo.setHeartName(dbEquipment.getHeartbeatText());
                dbOperationVo.setFacility(dbEquipment.getEquipmentNoString());
                dbOperationVo.setOperationName(dbEquipment.getEquipmentName());
                dbOperationVo.setCreateTime(new Date());
                dbOperationVo.setOperationId(s);
                operateWeChatVos.add(dbOperationVo);
            }

        }
        return remoteTcpService.stateAllQuery(operateWeChatVos);
    }



    /**
     * @Version 2.0.0
     * @Author ZHAOXIAOSI
     * @Description 单操作接口
     * @Date 20:56 2021/5/2
     * @return com.ruoyi.common.core.domain.R
     * @sign 他日若遂凌云志,敢笑黄巢不丈夫!
     **/
    @PostMapping("singleOperation")
    @ApiOperation(value = "App2.0单操作接口",notes = "App2.0单操作接口",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "landId",value = "大棚id",required = true),
            @ApiImplicitParam(name = "operationText",value = "指令",required = true),
            @ApiImplicitParam(name = "operationType",value = "操作类型",required = true),
            @ApiImplicitParam(name = "handleName",value = "指令对应名称: 放下",required = true),
            @ApiImplicitParam(name = "operationId",value = "功能id",required = true),
            @ApiImplicitParam(name = "startTime",value = "开始时间",required = false),
            @ApiImplicitParam(name = "stopTime",value = "结束时间",required = false),
            @ApiImplicitParam(name = "flag",value = "定时开关：0开 1取消",required = false),
            @ApiImplicitParam(name = "percentage",value = "卷帘卷膜百分比",required = false)
    })
    @Operation()

    public R SingleOperation(OperateVO operateVO){
        DbEquipmentComponent component = null;
        if (operateVO.getPercentage() != null && operateVO.getPercentage() >= 0 && operateVO.getPercentage() <= 100){
            //百分比开启
            component = dbEquipmentComponentService.selectDbEquipmentComponentById(operateVO.getOperationId());
            Map<String,Object> o = (Map<String, Object>) JSON.parse(component.getSpList());
            List<Map<String,String>> list = (List<Map<String, String>>) o.get("spList");
            R r = remoteTcpService.percentageOperate(component.getHeartbeatText(), component.getEquipmentNo(), operateVO.getPercentage(),component.getPercentage());
            if (r.get("code").equals(HttpStatus.OK.value())){
                //进度值
                o.put("percentage",operateVO.getPercentage());
                dbEquipmentComponentService.updateDbEquipmentComponent(DbEquipmentComponent.builder()
                        .spList(JSON.toJSONString(o,SerializerFeature.WriteMapNullValue))
                        .id(component.getId())
                        .build());
            }
            return r;
        }

        if (operateVO.getOperationType().equals("3") && operateVO.getFlag() != null){
            component = dbEquipmentComponentService.selectDbEquipmentComponentById(operateVO.getOperationId());
            if (operateVO.getFlag() != null) {
                //补光定时
                R r = null;
                if (operateVO.getFlag() == 0) {
                    //开多久停
                    Long stop = (operateVO.getStopTime() - operateVO.getStartTime()) / 1000L / 60L;
                    //多久后开
                    Long start = (operateVO.getStartTime() - System.currentTimeMillis()) / 1000L / 60L;
                    r = remoteTcpService.operateLight(component.getHeartbeatText(), component.getEquipmentNo(), operateVO.getFlag(), start, stop);
                } else {
                    r = remoteTcpService.operateLight(component.getHeartbeatText(), component.getEquipmentNo(), operateVO.getFlag(), null, null);
                }
                if (r.get("code").equals(HttpStatus.OK.value())) {
                    //插入时间值
                    Map<String, Object> o = (Map<String, Object>) JSON.parse(component.getSpList());
                    o.put("startDate", operateVO.getFlag() == 0 ? operateVO.getStartTime() : null);
                    dbEquipmentComponentService.updateDbEquipmentComponent(DbEquipmentComponent.builder()
                            .spList(JSON.toJSONString(o, SerializerFeature.WriteMapNullValue))
                            .id(component.getId())
                            .build());
                    return r;
                }
            }
            R r = remoteTcpService.operation(getDbOperationVo(operateVO));
            if (r.get("code").equals(HttpStatus.OK.value())){
                Map<String, Object> o = (Map<String, Object>) JSON.parse(component.getSpList());
                if (operateVO.getHandleName().contains("stop")){
                        o.put("switchState",1 );
                    }
                o.put("switchState",0 );
                dbEquipmentComponentService.updateDbEquipmentComponent(DbEquipmentComponent.builder()
                        .spList(JSON.toJSONString(o, SerializerFeature.WriteMapNullValue))
                        .id(component.getId())
                        .build());
            }
            return r;
        }
        //return R.ok();
       // 一律为单操作
        return remoteTcpService.operation(getDbOperationVo(operateVO));
    }

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,19);
        long time = calendar.getTimeInMillis();
        long l1 = Calendar.getInstance().getTimeInMillis();
        long i = System.currentTimeMillis();
        System.out.println((time - l1)/1000/60);

    }

    /*
     *页面操作（单项）
     * */
    @GetMapping("operate")
    @ApiOperation(value = "页面操作（单项）", notes = "页面操作（单项）")
    @OperationLog(OperationLogNmae=OperationLogType.EQUIPMENT,OperationLogSource = OperationLogType.WEchat)
    @UserOperationLog()
    public R operate(@ApiParam(name = "id", value = "设备id", required = true)Long id, @ApiParam(name = "text", value = "操作指令", required = true)String text,
                     @ApiParam(name = "name", value = "操作对象", required = true)String name,
                     @ApiParam(name = "type", value = "操作对象类型", required = true)String type,
                     @ApiParam(name = "handleName", value = "开始 ：start，开始暂停：start_stop，结束暂停down_stop，结束down", required = true) String handleName) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(id);
        dbOperationVo.setHeartName(dbEquipment.getHeartbeatText());
        dbOperationVo.setFacility(dbEquipment.getEquipmentNoString());
        dbOperationVo.setOperationText(text);
        dbOperationVo.setOperationTextType("05");
        //                        操作名称
        dbOperationVo.setOperationName(operationLogUtils.toOperationText(name, handleName));
        R operation = remoteTcpService.operation(dbOperationVo);
        return operation;
    }

    /**
     * 拼装发送对象
     * @since: 2.0.0
     * @param operateVO
     * @return: com.ruoyi.system.domain.DbOperationVo
     * @author: ZHAOXIAOSI
     * @date: 2021/5/6 20:42
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    private DbOperationVo getDbOperationVo(OperateVO operateVO){
        DbOperationVo dbOperationVo = new DbOperationVo();
        DbEquipmentComponent component = dbEquipmentComponentService.selectDbEquipmentComponentById(operateVO.getOperationId());
        dbOperationVo.setHeartName(component.getHeartbeatText());
        dbOperationVo.setFacility(component.getEquipmentNoString());
        dbOperationVo.setOperationText(operateVO.getOperationText());
        dbOperationVo.setOperationTextType("05");
        dbOperationVo.setOperationName(dbEquipmentComponentService.selectDbEquipmentComponentById(operateVO.getOperationId()).getEquipmentName());
        dbOperationVo.setLandId(operateVO.getLandId()+"");
        dbOperationVo.setUserId(getRequest().getHeader(Constants.CURRENT_ID));
        return dbOperationVo;
    }

    /**
     * 批量操作
     * @since: 2.0.0
     * @param operateVOS
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/6 20:21
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("batchOperate")
    @ApiOperation(value = "批量操作接口",notes = "批量操作接口",httpMethod = "POST")
    @Operation(OperationLogType = true)
    @ApiImplicitParams({

    })
    public R batchOperate(@RequestBody List<OperateVO> operateVOS){
        if (CollectionUtils.isEmpty(operateVOS)){
            return null;
        }
        if (operateVOS.size() > 5){
            return R.error(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        ArrayList<DbOperationVo> list = new ArrayList<>();
        operateVOS.forEach(e -> list.add(getDbOperationVo(e)));
        return remoteTcpService.operationList(list);
    }

    @PostMapping("operateFillInLight")
    public R operateFillInLight(){
        return null;
    }


    /*
     * 设备页面的操作   具体操作项   ok
     * */
    @GetMapping("oprateEqment")
    @ApiOperation(value = "设备页面操作", notes = "设备页面操作")
    @OperationLog(OperationLogType = true, OperationLogNmae = OperationLogType.EQUIPMENT, OperationLogSource = OperationLogType.WEchat)
    public R oprateEqment(@ApiParam(name = "id", value = "设备id", required = true) Long id, @ApiParam(name = "type"
            , value = "操作单位名称:例如卷帘1", required = true) String type,
                          @ApiParam(name = "handleName", value = "具体操作名称开始 ：start，开始暂停：start_stop，结束down,结束暂停down_stop", required = true) String handleName)
            throws Exception {
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(id);
        DbLandEquipment dbLandEquipment = new DbLandEquipment();
        dbLandEquipment.setDbEquipmentId(dbEquipment.getEquipmentId());

        List<OperatePojo> pojos = JSON.parseArray(dbEquipment.getHandlerText(), OperatePojo.class);
        List<DbOperationVo> vos = new ArrayList<>();

        for (OperatePojo pojo : pojos) {
            if (type.equals(pojo.getCheckCode())) {
                for (OperatePojo.OperateSp operateSp : pojo.getSpList()) {
                    if (operateSp.getHandleName().equals(handleName)) {
                         DbOperationVo dbOperationVo = new DbOperationVo();
//        心跳名称
                        dbOperationVo.setHeartName(dbEquipment.getHeartbeatText());

//        设备号
                        dbOperationVo.setFacility(dbEquipment.getEquipmentNoString());
//        是否完成
                        dbOperationVo.setIsTrue("1");
//                        操作名称
                        dbOperationVo.setOperationName(operationLogUtils.toOperationText(pojo.getCheckCode(), operateSp.getHandleName()));
//        创建时间
                        dbOperationVo.setCreateTime(new Date());
                        dbOperationVo.setOperationText(operateSp.getHandleCode());
                        dbOperationVo.setOperationTextType("05");
                        vos.add(dbOperationVo);
                    }
                }
            }
        }


//        发送接口

//        调用发送模块
        R operation = remoteTcpService.operationList(vos);
        return operation;


    }


    private ArrayList<OperateWeChatVo> getOperateWeChatVos(List<DbLand> dbLands) {
        ArrayList<OperateWeChatVo> operateWeChatVos = new ArrayList<>();
        for (DbLand dbLand : dbLands) {
            OperateWeChatVo operateWeChatVo = new OperateWeChatVo();
            operateWeChatVo.setDbLandId(dbLand.getLandId());
            operateWeChatVo.setNickName(dbLand.getNickName());
            if (StringUtils.isEmpty(dbLand.getEquipmentIds())) {
                operateWeChatVo.setIsBound(0);
                continue;
            }
            ArrayList<DbEquipmentVo> dbEquipmentVos = new ArrayList<>();

            for (String s : dbLand.getEquipmentIds().split(",")) {
                DbEquipmentVo dbEquipmentVo = new DbEquipmentVo();

                DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(s));
                dbEquipmentVo.setEquipment(dbEquipment);
                List<OperatePojo> pojos = JSON.parseArray(dbEquipment.getHandlerText(), OperatePojo.class);
                dbEquipment.setPojos(pojos);
                DbTcpType dbTcpType = new DbTcpType();
                dbTcpType.setHeartName(dbEquipment.getHeartbeatText() + "_" + dbEquipment.getEquipmentNoString());
                List<DbTcpType> list = remoteTcpService.list(dbTcpType);

                if (list.size() != 0 && list != null) {
                    DbTcpType dbTcpType1 = list.get(0);

                    dbEquipmentVo.setDbTcpType(dbTcpType1);
                }
                /*
                 * 剩余时长，到期时长计算
                 * */
//                运行时长
                dbEquipmentVo.setRemaining(DateUtils.getDatePoorDay(dbEquipment.getAllottedTime(), new Date()));
//              剩余时长
                dbEquipmentVo.setRuntime(DateUtils.getDatePoorDay(new Date(), dbEquipment.getCreateTime()));



                dbEquipmentVos.add(dbEquipmentVo);
//                到期
                if (dbEquipment.getIsFault() == 1) {
                    operateWeChatVo.setIsUnusual(3);
                    log.info(dbEquipment.getEquipmentId() + "已经过期");
                    operateWeChatVo.setUnusualText(dbLand.getNickName() + "下的" + dbEquipment.getEquipmentName() + "已经过期");
                    continue;
                } else if (dbEquipment.getIsPause() == 1) {
                    //                故障
                    operateWeChatVo.setIsUnusual(1);
                    operateWeChatVo.setUnusualText(dbLand.getNickName() + "下的" + dbEquipment.getEquipmentName() + "发生故障");
                    log.info(dbEquipment.getEquipmentId() + "发生故障");
                    continue;
                } else if (dbEquipment.getIsOnline() == 1) {
                    //                切换手动
                    operateWeChatVo.setIsUnusual(2);
                    operateWeChatVo.setUnusualText(dbLand.getNickName() + "下的" + dbEquipment.getEquipmentName() + "已经切换手动");
                    log.info(dbEquipment.getEquipmentId() + "已经切换手动");
                    continue;
                } else {
                    operateWeChatVo.setIsUnusual(0);
                }

            }
            operateWeChatVo.setDbEquipmentVos(dbEquipmentVos);
            operateWeChatVos.add(operateWeChatVo);
        }
        return operateWeChatVos;

    }


}
