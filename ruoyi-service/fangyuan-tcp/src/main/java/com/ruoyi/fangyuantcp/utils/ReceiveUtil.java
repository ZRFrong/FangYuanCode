package com.ruoyi.fangyuantcp.utils;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;

/*
 *接收信息处理
 * */


@Log4j2
public class ReceiveUtil {
    private IDbTcpClientService tcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbTcpOrderService tcpOrderService = SpringUtils.getBean(IDbTcpOrderService.class);
    private IDbTcpTypeService tcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);

    /*
     * 心跳添加或者更改状态处理
     * */
    public void heartbeatChoose(DbTcpClient dbTcpClient, ChannelHandlerContext ctx) {
        int i = tcpClientService.heartbeatChoose(dbTcpClient);
        if (i == 1) {
//            不存在 新建，添加map管理
            NettyServer.map.put(dbTcpClient.getHeartName(), ctx);
        }
    }

    /*
     *传感器状态接收处理
     * */
    public void stateRead(String type) {
        char[] chars = type.toCharArray();
        /*
         * 0307
         * */

    }
    /*
    *
    * */




}
