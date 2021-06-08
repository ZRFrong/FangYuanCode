package com.fangyuan.netty.config.netty;

import com.ruoyi.system.domain.DbTcpClient;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 * @Description: 通道与消息接收处理器
 * @Author zheng
 * @Date 2021/6/7 14:05
 * @Version 1.0
 */
@Log4j2
public class ChannelInboundHandlerAdapter extends io.netty.channel.ChannelInboundHandlerAdapter {



    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     * *识别到dapeng标识码确认为心跳名称   收到心跳更新心跳时间添加到在线设备表 更改在线状态
     * *识别到05开头的是操作指令返回的   寻找相同的操作指令更改返回状态（redis中）
     * *识别到03开头的则为状态查询返回   添加到type表或者更新type表中的数据
     * @param ctx 通道
     * @param msgObj 消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msgObj) throws InterruptedException {
        String msg = msgObj.toString();
        log.info("ChannelInboundHandlerAdapter.read msg:{} ",msg);
        if (msg.contains("dapeng")) {
            // 心跳处理
            log.warn("来到的心跳名称是：{}",msg);
            DbTcpClient dbTcpClient = getIp(ctx);
            dbTcpClient.setHeartName(msg);//心跳
            InitNettyServer.map.put(dbTcpClient.getHeartName(), ctx);
            log.info("时间：{}, 设备:{}, 心跳处理：{} " ,new Date(), getIp(ctx).getHeartName() , msg);
        } else {

        }
    }


    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)  {
        ctx.flush();
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     * @param ctx 通道
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {
        log.info("客户端建立连接 sid:{}",ctx.name());
    }

    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelInactive(ctx);
        //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
    }

    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception, IOException {
        super.userEventTriggered(ctx, evt);
        ctx.close();
    }

    /* 通过心跳拿到ip 和 端口  */
    private DbTcpClient getIp(ChannelHandlerContext ctx) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        DbTcpClient dbTcpClient = new DbTcpClient();
        for (String key : InitNettyServer.map.keySet()) {
            if (InitNettyServer.map.get(key) != null && InitNettyServer.map.get(key).equals(ctx)) {
                dbTcpClient.setHeartName(key);
            }
        }
        dbTcpClient.setIp(clientIp);
        dbTcpClient.setPort(port + "");
        return dbTcpClient;
    }
}
