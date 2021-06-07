package com.fangyuan.netty.config.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 初始化netty服务
 * @Author zheng
 * @Date 2021/6/7 13:50
 * @Version 1.0
 */
@Component
@Log4j2
public class InitNettyServer implements InitializingBean {

    @Value("${person.listen-port}")
    private int port;


    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> initServer()).start();
    }

    private void initServer(){
        /*
         * 配置服务端的NIO线程组
         * NioEventLoopGroup 是用来处理I/O操作的Reactor线程组
         * bossGroup：用来接收进来的连接，workerGroup：用来处理已经被接收的连接,进行socketChannel的网络读写，
         * bossGroup接收到连接后就会把连接信息注册到workerGroup
         * workerGroup的EventLoopGroup默认的线程数是CPU核数的二倍
         */
        EventLoopGroup bossGroup = bossLoopGroupAdapter(1);
        EventLoopGroup workerGroup = workLoopGroupAdapter();
        try {
            /*
             * ServerBootstrap 是一个启动NIO服务的辅助启动类
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            /*
             * 设置group，将bossGroup， workerGroup线程组传递到ServerBootstrap
             */
            serverBootstrap = serverBootstrap.group(bossGroup, workerGroup);
            /*
             * ServerSocketChannel是以NIO的selector为基础进行实现的，用来接收新的连接，这里根据运行环境来选择NioServerSocketChannel或EpollServerSocketChannel获取新的连接
             */
            serverBootstrap = serverBootstrap.channel(isWin() ? NioServerSocketChannel.class : EpollServerSocketChannel.class);
            /*
             * option是设置 bossGroup，childOption是设置workerGroup
             * netty 默认数据包传输大小为1024字节, 设置它可以自动调整下一次缓冲区建立时分配的空间大小，避免内存的浪费    最小  初始化  最大 (根据生产环境实际情况来定)
             * 使用对象池，重用缓冲区
             */
            serverBootstrap = serverBootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 10496, 1048576));
            serverBootstrap = serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 10496, 1048576));
            /*
             * 设置 I/O处理类,主要用于网络I/O事件，记录日志，编码、解码消息
             */
            serverBootstrap = serverBootstrap.childHandler(new InitNettyChannel<Channel>());
            /*
             * 绑定端口，同步等待成功
             */
            ChannelFuture f = serverBootstrap.bind(port).sync();
            log.info("端口监听启动成功");
            /*
             * 等待服务器监听端口关闭
             */
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("netty服务初始化异常:",e);
        } finally {
            //退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    /**
     * 获取work线程组
     * @return 工作组
     */
    private EventLoopGroup  workLoopGroupAdapter(){
        return bossLoopGroupAdapter(0);
    }

    /**
     * 获取boss线程组
     * @param threadNum 线程数量
     * @return 工作组
     */
    private EventLoopGroup  bossLoopGroupAdapter(int threadNum){
        if(isWin()){
            return new NioEventLoopGroup(threadNum);
        }
        return new EpollEventLoopGroup(threadNum);
    }

    /**
     * 判断运行环境
     * @return 是否为win环境
     */
    private boolean isWin(){
        return System.getProperty("os.name").toLowerCase().contains("win");
    }




}
