package com.fangyuan.netty.config.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Description: 初始化netty通道
 * @Author zheng
 * @Date 2021/6/7 13:54
 * @Version 1.0
 */
public class InitNettyChannel<C> extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch)  {
        ch.pipeline().addLast("decoder", new DecoderMessage());
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 4, 4, -8, 0));
        //自定义ChannelInboundHandlerAdapter
        ch.pipeline().addLast(new ChannelInboundHandlerAdapter());

    }
}
