package com.ruoyi.fangyuantcp.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbOrder;
import com.ruoyi.system.domain.DbTcpOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    private SendCodeUtils sendCodeUtils=new SendCodeUtils();


    @Autowired
    private IDbTcpClientService tcpClientService;

    @Autowired
    private IDbTcpOrderService tcpOrderService;
    /*
    * 随意发送指令
    * */
    @PostMapping("operation")
    @ApiOperation(value = "操作设备无操作类型", notes = "操作设备无操作类型")
    public R operation(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true)  DbOperationVo dbOperationVo) {
        dbOperationVo.setOperationName("调试");
        int query = sendCodeUtils.query(dbOperationVo);
        return toAjax(query);
    }


    /*
    * 心跳名称模糊查询
    * */
    @GetMapping("heartBeatFuzzy")
    @ApiOperation(value = "心跳名称模糊查询", notes = "心跳名称模糊查询")
    public R heartBeatDFuzzy(@ApiParam(name = "heartBeat", value = "string", required = true) @PathVariable("heartBeat") String heartBeat) {
        List<String> lists= tcpClientService.heartBeatDFuzzy(heartBeat);

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
        if (dbTcpOrders.size()>10){
        List<DbTcpOrder> rankPersonVoListNow=dbTcpOrders.subList(0,10);
        return R.data(rankPersonVoListNow);
        }else {
        return R.data(dbTcpOrders);
        }
    }


}
