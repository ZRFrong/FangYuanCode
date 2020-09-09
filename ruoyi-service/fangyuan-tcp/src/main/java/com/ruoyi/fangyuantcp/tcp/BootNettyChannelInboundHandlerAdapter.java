package com.ruoyi.fangyuantcp.tcp;


import com.ruoyi.fangyuantcp.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.utils.ReceiveUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * I/O数据读写处理类
 */
@Slf4j
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {


    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     * *识别到dapeng标识码确认为心跳名称   收到心跳更新心跳时间添加到在线设备表 更改在线状态
     * *识别到05开头的是操作指令返回的   寻找相同的操作指令更改返回状态（redis中）
     * *识别到03开头的则为状态查询返回   添加到type表或者更新type表中的数据
     * *
     *
     * @param ctx
     * @param msg
     */
    @Override

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {

        ReceiveUtil receiveUtil = new ReceiveUtil();
        /*
         *心跳查询
         * */
//        接收到的消息格式
        String s = msg.toString();

        if (s.contains("dapeng")) {
            DbTcpClient dbTcpClient = getIp(ctx);
            dbTcpClient.setHeartName(msg.toString());
            receiveUtil.heartbeatChoose(dbTcpClient,ctx);
        } else {
            //        前两位标识符
            String charStic = s.substring(0,2);
            if (charStic.equals("03")){

            };
        }


    }

    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
//        System.out.println("接受成功");
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();

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
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();

        ctx.close();//抛出异常，断开与客户端的连接
    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {


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
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
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
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close();//超时时断开连接
        System.out.println("userEventTriggered:" + clientIp);
    }


    private DbTcpClient getIp(ChannelHandlerContext ctx) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        DbTcpClient dbTcpClient = new DbTcpClient();
        dbTcpClient.setIp(clientIp);
        dbTcpClient.setPort(port + "");
        return dbTcpClient;
    }


}
