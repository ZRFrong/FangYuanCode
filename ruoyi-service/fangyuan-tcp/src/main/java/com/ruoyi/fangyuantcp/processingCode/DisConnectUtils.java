package com.ruoyi.fangyuantcp.processingCode;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.system.domain.DbEquipment;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/*
 *断开连接处理类
 * */
@Log4j2
public class DisConnectUtils {

    /*
     * 在线map
     * */
    public static Map<String, ChannelHandlerContext> map = NettyServer.map;

    private IDbTcpClientService tcpClientService= SpringUtils.getBean(IDbTcpClientService.class);
    private IDbEquipmentService equipmentService= SpringUtils.getBean(IDbEquipmentService.class);
    private IDbTcpTypeService tcpTypeService= SpringUtils.getBean(IDbTcpTypeService.class);

    /*
     *正常断开连接
     * */
    public void normalClose(ChannelHandlerContext ctx) {
//        String s = deleteCtx(ctx);
//        log.error(DateUtils.getDate()+s+"断开连接");

    }

    /*
     *异常断开连接
     * */
    public void errorClose(ChannelHandlerContext ctx) {
//        String s = deleteCtx(ctx);
//        log.error(DateUtils.getDate()+s+"异常断开连接");

    }
    /*
     *超时断开连接
     * */
    public void timeoutClose(ChannelHandlerContext ctx) {
        String s = deleteCtx(ctx);
        log.error(DateUtils.getDate()+s+"超时断开连接");

    }

    /*
     * 循环map寻找符合的key  然后删除map
     * */
    private String deleteCtx(ChannelHandlerContext ctx) {
        for (String s : map.keySet()) {
            if (map.get(s).equals(ctx)) {
                map.remove(s);
                ctx.close();
                deleteClient(s);
                return s;
            }
        }
        return null;

    }

    /*
    * 删除指定在线表
    * */

    /*
     * 更改设备状态
     * */
    private  void deleteClient(String heartbeatName){
        tcpClientService.deleteDbtcpHeartbeatName(heartbeatName);
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(heartbeatName);
        DbEquipment dbEquipment1 = equipmentService.selectDbEquipmentList(dbEquipment).get(0);
        dbEquipment1.setIsFault(1);
        int i = equipmentService.updateDbEquipment(dbEquipment1);

        tcpTypeService.updateByHeartbeat(heartbeatName);

    }




}
