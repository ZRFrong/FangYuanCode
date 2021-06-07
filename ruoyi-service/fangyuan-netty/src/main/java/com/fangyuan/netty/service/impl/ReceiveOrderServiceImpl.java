package com.fangyuan.netty.service.impl;

import com.fangyuan.netty.config.netty.InitNettyServer;
import com.fangyuan.netty.service.IReceiveOrderService;
import com.ruoyi.common.config.thread.ThreadLocalUtils;
import com.ruoyi.system.domain.tcp.DbOperationByteOrderVo;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 消息指令接收逻辑处理实现类
 * @Author zheng
 * @Date 2021/6/7 15:26
 * @Version 1.0
 */
@Service
@Log4j2
public class ReceiveOrderServiceImpl implements IReceiveOrderService {

    /** 指令下发失败重发次数 **/
    private static final byte RETRY_COUNT = 1;
    /** 初始调用次数 **/
    private static final byte INIT_CALL_COUNT = 1;
    /** 重发睡眠时间 单位毫秒**/
    private static final short RETRY_SLEEP_TIME = 200;
    /** 指令下发次数缓存标识 **/
    private static final String TEMP_CALL_COUNT_SIGN = "curCallCount";

    /**
     * 异步处理解析和下发,  出现异常默认回调一次
     * @param orderMessage 消息体
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public void sendMessage(DbOperationByteOrderVo orderMessage) {
        // 解析消息到本地socket缓存查找对应连接进行指令下发
        // 当前指令重发次数
        Object curCallCount = ThreadLocalUtils.get(TEMP_CALL_COUNT_SIGN);
        byte callCount = Objects.isNull(curCallCount) ? INIT_CALL_COUNT : (byte)curCallCount;
        try{
            if(callCount != INIT_CALL_COUNT){
                TimeUnit.MILLISECONDS.sleep(RETRY_SLEEP_TIME);
            }
            ChannelHandlerContext no1_1 = InitNettyServer.map.get(orderMessage.getHeartName());
            if(Objects.isNull(no1_1)){
                // TODO 判断通道是否为空 空则通知用户连接中断

            }
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(orderMessage.getByteOrder()));
            channel.flush();
        }catch (Exception e){
            log.error("ReceiveOrderServiceImpl.handleMessage异常:{}, 回调次数为:{}",e,callCount);
            if(callCount > RETRY_COUNT){
                // TODO 通知用户下发异常 网络异常
                throw new RuntimeException("消息下发处理异常:",e);
            }
            // 重试次数加一 进行回调
            ThreadLocalUtils.put(TEMP_CALL_COUNT_SIGN,++callCount);
            sendMessage(orderMessage);
        }finally {
            // 清除当前线程缓存调用次数
            if(!Objects.isNull(curCallCount))
                ThreadLocalUtils.clearContext();
        }
    }



}
