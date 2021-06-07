package com.fangyuan.netty.controller;

import com.fangyuan.netty.service.IReceiveOrderService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.tcp.DbOperationByteOrderVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接收设备指令控制层
 */
@RestController
@RequestMapping("receive")
@Log4j2
public class ReceiveOrderController {

    @Autowired
    private IReceiveOrderService receiveOrderService;

    /**
     * 接收所有指令入口
     * @param orderMessagePo 消息实体信息
     * @return 接收状态
     */
    @PostMapping("allOrder")
    public R receiveOrder(@RequestBody /*@Validated*/ DbOperationByteOrderVo orderMessagePo){
        log.info("netty下发层接收消息【{}】",orderMessagePo);
        receiveOrderService.sendMessage(orderMessagePo);
        return R.ok();
    }
}
