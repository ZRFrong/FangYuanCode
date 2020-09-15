package com.ruoyi.fangyuantcp.utils;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
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

    /*
     *正常断开连接
     * */
    public void normalClose(ChannelHandlerContext ctx) {
        String s = deleteCtx(ctx);
        log.error(DateUtils.getDate()+s+"断开连接");

    }

    /*
     *异常断开连接
     * */
    public void errorClose(ChannelHandlerContext ctx) {
        String s = deleteCtx(ctx);
        log.error(DateUtils.getDate()+s+"异常断开连接");

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
        final String[] str = {null};
        map.keySet().forEach(item -> {
            if (map.get(item).equals(ctx)) {
                map.remove(item);
                ctx.close();
                str[0] = item;
            }
        });
        deleteClient(str[0]);
        return str[0];
    }

    /*
    * 删除指定在线表
    * */
    private  void deleteClient(String heartbeatName){
        tcpClientService.updateByHeartbeatName(heartbeatName);

    }




}
