package com.ruoyi.fangyuantcp.processingCode;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.redis.util.RedisLockUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.fangyuantcp.utils.Crc16Util;
import com.ruoyi.fangyuantcp.utils.LogOrderUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @Description: socket消息下发测试
 * @Author zheng
 * @Date 2021/6/18 10:52
 * @Version 1.0
 */
@Component
@Log4j2
public class SocketSendUtil {

    @Autowired
    private LogOrderUtil logOrderUtil;
    @Autowired
    private RedisLockUtil redisLockUtil;
    // 指令缓存集合
    private static final Map<String, CopyOnWriteArrayList<byte[]>> orderCache = new ConcurrentHashMap<>(100);
    public static final String SOCKET_ORDER_LOCK_PRE = "ORDER_SOCKET_LOCK_PRE:";

    /**
     * 下指令到设备
     * 此处进行是否下发逻辑校验，
     * 根据心跳名称到redis缓存查看是否有锁, 无锁则加锁进行指令下发， 有锁则放入本地map缓存等待唤醒
     * @param heartName 心跳名称
     * @param orderByte 指令内容
     */
    public void sendMsgToDevice(String heartName, byte[] orderByte){
        log.info("SocketSendUtil.sendMsgToDevice heartName:{}, orderByte:{}",heartName,byteToOrderText(orderByte));
        if(StringUtils.isNotEmpty(heartName) && ObjectUtil.isNotNull(orderByte)){
            boolean locked = redisLockUtil.tryLock(getCacheKey(heartName), heartName, 500, TimeUnit.MILLISECONDS);
            if(!locked){
                // 加入缓存
                cacheOrderMsg(heartName,orderByte);
                return;
            }
            // 执行下发
            sendToDevice(heartName,orderByte);
       }
    }

    // 下发指令
    @SneakyThrows
    private void sendToDevice(String heartName, byte[] orderByte) {
        ChannelHandlerContext channelHandlerContext = NettyServer.map.get(heartName);
        String orderText = byteToOrderText(orderByte);
        log.info("进入SocketSendUtil.sendToDevice heartName:{},  orderByte:{}, channelHandler:{}"
                ,heartName
                ,orderText
                ,channelHandlerContext);
        if(ObjectUtil.isNotNull(channelHandlerContext)){
            Channel channel = channelHandlerContext.channel();
            logOrderUtil.recordSend(heartName,orderText ,null);
            channel.write(Unpooled.copiedBuffer(orderByte));
            channel.flush();
            channel.read();
        }
    }

    // 缓存下发指令
    private void cacheOrderMsg(String heartName, byte[] orderByte){
        log.info("进入SocketSendUtil.cacheOrderMsg heartName:{}, orderByte:{}",heartName,byteToOrderText(orderByte));
        CopyOnWriteArrayList<byte[]> orderList = orderCache.get(heartName);
        if(CollectionUtil.isEmpty(orderList)){
            orderList = new CopyOnWriteArrayList<byte[]>();
            orderCache.put(heartName,orderList);
        }
        orderList.add(orderByte);
        log.info("退出SocketSendUtil.cacheOrderMsg heartName:{}, orderList:{}",heartName,orderList.size());
    }

    /**
     * 唤醒指定设备等待的指令消息
     * @param heartName 心跳名称
     */
    @Async("threadPoolTaskExecutor")
    public void notifyHeartName(String heartName){
        CopyOnWriteArrayList<byte[]> orderList = orderCache.get(heartName);
        if(CollectionUtil.isNotEmpty(orderList)){
            byte[] order = orderList.get(0);
            sendMsgToDevice(heartName,order);
            // 清除当前执行指令
            orderList.remove(0);
        }
        log.info("退出SocketSendUtil.notifyHeartName heartName:{} ,orderList:{}",heartName,orderList == null ? 0:orderList.size());
    }

    // 获取redis缓存key
    private String getCacheKey(String heartName){
        return SOCKET_ORDER_LOCK_PRE + heartName;
    }


    private String byteToOrderText(byte []orderByte){
        final String emptyStr = " ";
        String s = Crc16Util.byteTo16String(orderByte);
        String[] split = s.split(emptyStr);
        StringBuilder text = new StringBuilder();
        for (String s1 : split) {
            Integer integer = Integer.valueOf(s1, 16);
            if(integer <10 ){
                text.append("0").append(integer).append(emptyStr);
                continue;
            }
            text.append(integer).append(emptyStr);
        }
        return text.toString();
    }


    public static void main(String[] args) {
        byte []b = {1, 1, 1, -12, 0, 1, -67, -60};
        System.out.println(new SocketSendUtil().byteToOrderText(b));
        byte []bb = {1, 3, 0, -56, 0, 2, 69, -11};
        System.out.println(new SocketSendUtil().byteToOrderText(bb));
    }
}
