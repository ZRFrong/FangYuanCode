package com.ruoyi.fangyuantcp.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.processingCode.ReceiveUtils;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/*
* 调试使用接口
*
* 心跳模糊查询

不指定类型发送指令

tcporder表实时回写 根据心跳
* */
@RestController
@Api("operateDebug")
@RequestMapping("operateDebug")
public class OperateDebugController extends BaseController {


    private SendCodeUtils sendCodeUtils = new SendCodeUtils();


    @Autowired
    private IDbTcpClientService tcpClientService;

    @Autowired
    private IDbTcpOrderService tcpOrderService;

    @Autowired
    private IDbTcpTypeService dbTcpTypeService;

    /*
     * 随意发送指令
     * */
    @PostMapping("operation")
    @ApiOperation(value = "操作设备无操作类型", notes = "操作设备无操作类型")
    public R operation(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true) DbOperationVo dbOperationVo) {
        dbOperationVo.setOperationName("调试");
        int query = SendCodeUtils.query(dbOperationVo);
        return toAjax(query);
    }


    /*
     * 心跳名称模糊查询
     * */
    @GetMapping("heartBeatFuzzy")
    @ApiOperation(value = "心跳名称模糊查询", notes = "心跳名称模糊查询")
    public R heartBeatDFuzzy(@ApiParam(name = "heartBeat", value = "string", required = true) @PathVariable("heartBeat") String heartBeat) {
        List<String> lists = tcpClientService.heartBeatDFuzzy(heartBeat);

        return R.data(lists);
    }

    /*
     *操作记录查询
     * */
    @GetMapping("orderQuery/{heartBeat}")
    @ApiOperation(value = "操作记录查询", notes = "操作记录查询")
    public R orderQuery(@ApiParam(name = "heartBeat", value = "string", required = true) @PathVariable("heartBeat") String heartBeat) {
        DbTcpOrder dbTcpOrder = new DbTcpOrder();
        dbTcpOrder.setHeartName(heartBeat);
        List<DbTcpOrder> dbTcpOrders = tcpOrderService.selectDbTcpOrderList(dbTcpOrder);
        if (dbTcpOrders.size() > 10) {
            List<DbTcpOrder> rankPersonVoListNow = dbTcpOrders.subList(0, 10);
            return R.data(rankPersonVoListNow);
        } else {
            return R.data(dbTcpOrders);
        }
    }

    /*
     *状态同步调试指令
     * */
    @PostMapping("querySync")
    @ApiOperation(value = "状态同步调试指令", notes = "状态同步调试指令测试接口")
    public R querySync(@ApiParam(name = "heartName", value = "传入String类型", required = true) String heartName) {
        long time = new Date().getTime();
        List<DbOperationVo> dbOperationVoList = new ArrayList<>();
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(heartName);
        dbOperationVo.setFacility("01");
        dbOperationVo.setOperationTextType("9");
        dbOperationVoList.add(dbOperationVo);
        R r = dbTcpTypeService.querySync(dbOperationVoList);
        R r1 = new R();
        r1.put("heartName", heartName);
        Long whenTimeTotal = new Date().getTime() - time;
        r1.put("whenTimeTotal", whenTimeTotal);
        HashMap<String, String> data1 = (HashMap<String, String>) r.get("data");
        for (String s : data1.keySet()) {
            if (data1.get(s).contains("故障")) {
                return r1.put("code",500);
            }
        }
        DbTcpOrder dbTcpOrder = new DbTcpOrder();
        dbTcpOrder.setHeartName(heartName);
        List<DbTcpOrder> collect = tcpOrderService.selectDbTcpOrderList(dbTcpOrder).stream().limit((4 - data1.size())).collect(Collectors.toList());
        collect.forEach(ite -> tcpOrderService.deleteDbTcpOrderById(ite.getTcpOrderId()));
        if (collect.size() < 4&&collect.size()!=0) {
            r1.put("data", collect);
            r1.put("code", 502);
            return r1;
        } else if (collect.size() == 0) {
            r1.put("code",501);
            r1.put("msg","全部响应超时");
            return r1;
        } else {
            return r1.put("data", collect);
        }
    }


    /*
     * 添加心跳
     * */
    @GetMapping("progress/{heartName}")
    @ApiOperation(value = "状态同步调试指令", notes = "状态同步调试指令测试接口")
    public R progress(@PathVariable("heartName") String heartName) {
        ReceiveUtils.code = heartName;
        return R.ok();
    }
}
