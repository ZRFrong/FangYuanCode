package com.ruoyi.fangyuantcp.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.stereotype.Component;

@Component
public class BootNettyChannelInitializer<SocketChannel> extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
//         ChannelOutboundHandler，依照逆序执行
//        ch.pipeline().addLast("encoder", new StringEncoder());
//
//        // 属于ChannelInboundHandler，依照顺序执行
//        ch.pipeline().addLast("decoder", new StringDecoder());
        ch.pipeline().addLast("decoder", new MyDecoder());
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 4, 4, -8, 0));

        /**
         * 自定义ChannelInboundHandlerAdapter
         */
        ch.pipeline().addLast(new BootNettyChannelInboundHandlerAdapter());

    }
}
