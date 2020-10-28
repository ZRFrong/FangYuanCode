package com.ruoyi.fangyuantcp.tcp;


import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.utils.DisConnectUtils;
import com.ruoyi.fangyuantcp.utils.ReceiveUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 * I/O数据读写处理类
 */
@Slf4j
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private DisConnectUtils disConnectUtils = new DisConnectUtils();

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
//            心跳处理
            DbTcpClient dbTcpClient = getIp(ctx);
            dbTcpClient.setHeartName(msg.toString());
            receiveUtil.heartbeatChoose(dbTcpClient, ctx);
//            log.info("时间："+new Date()+"心跳处理："+msg);
        } else {
            //       前两位是设备号   然后是标识符 03状态返回  05操作响应
            String charStic = s.substring(2, 4);
            if (charStic.equals("03")) {
                log.info("时间：" + new Date() + "状态返回：" + msg);
//                状态处理  返回几位处理
                String charStic2 = s.substring(4, 6);

                switch(charStic2){
                    case "02":
                        //手动自动返回    01 03 02  05 06
                        receiveUtil.sinceOrHandRead(s,ctx);
                    case "0A":
//                        状态查询返回
                        receiveUtil.stateRead(s,ctx);

                }

                if (charStic2.equals("02")) {


                }
                receiveUtil.stateRead(msg.toString(), ctx);
            } else if (charStic.equals("05")) {
                log.info("时间：" + new Date() + "操作响应返回：" + msg);
//               操作响应
                receiveUtil.stateRespond(ctx, msg.toString());
            }
        }

    }


    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
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
        disConnectUtils.errorClose(ctx);
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
        disConnectUtils.normalClose(ctx);
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
        disConnectUtils.timeoutClose(ctx);
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
