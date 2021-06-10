package com.ruoyi.fangyuanapi.listener;

import cn.hutool.core.thread.ThreadUtil;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.service.IDbMonitorService;
import com.ruoyi.fangyuanapi.utils.MonitorCloudRequestUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * @Description: redis过期key监听
 * @Author zheng
 * @Date 2021/5/31 11:18
 * @Version 1.0
 */
@Log4j2
@Component
public class RedisExpireKeyListener extends KeyExpirationEventMessageListener {

    // redis缓存前缀
    private static final String streamCachePrefix = "VIDEO_STREAM_CACHE:";
    // redis缓存有效时间 在心跳时间的基础上增加一分钟
    @Value("${video.heart}")
    private long streamExpireTime;
    // 定时器执行周期 60分钟
    private static final long execPeriod = 60 * 1;
    // 定时器开关
    private static volatile boolean timerSwitch = true;
    // 视频流标识集合 设备序列号:通道号:视频流ID
    private static final Set<String> videoStreamSignCollect = new CopyOnWriteArraySet<>();
    private final IDbMonitorService monitorService;
    private final RedisUtils redisUtils;

    public RedisExpireKeyListener(RedisMessageListenerContainer listenerContainer,IDbMonitorService monitorService
            ,RedisUtils redisUtils) {
        super(listenerContainer);
        this.monitorService = monitorService;
        this.redisUtils = redisUtils;
        this.doCheck();
    }

    /**
     * 监听redis过期key
     * @param message key
     */
    @Override
    public void onMessage(Message message, byte[] pattern){
        String key = message.toString();
        log.info("RedisExpireKeyListener.onMessage 失效key：[{}]",key);
        if(key.startsWith(streamCachePrefix)){
            stopVideoStream(key);
            videoStreamSignCollect.remove(key);
        }
    }

    /**
     * 循环定时校验
     * 循环遍历存在的视屏流ID是否在redis中缓存， redis宕机后可能造成key已失效但视屏流仍然开启
     */
    private void doCheck(){
        ThreadUtil.execute(() -> {
            while (timerSwitch){
                // 遍历停流
                log.info("Start exec RedisExpireKeyListener.doCheck...");
                Iterator<String> iterator = videoStreamSignCollect.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    try{
                        if(StringUtils.isEmpty(redisUtils.get(next))){
                            videoStreamSignCollect.remove(next);
                            stopVideoStream(next);
                        }
                    }catch (Exception e){
                        log.error("RedisExpireKeyListener.doCheck 异常:[{}]",e);
                    }
                }

                // 睡眠
                try {
                    TimeUnit.SECONDS.sleep(execPeriod);
                } catch (InterruptedException e) { }
            }
        });
    }

    /**
     * 停止视屏流
     * @param redisKey 流缓存key前缀
     */
    private void stopVideoStream(String redisKey){
        String streamId = getVideoStreamId(redisKey);
        try{
            MonitorCloudRequestUtils.stopVideo(streamId);
            monitorService.syncVideoPlay(streamId,0);
        }catch (Exception e){
            log.error("RedisExpireKeyListener.stopVideoStream 异常:[{}]",e);
            // 失败的重新放入集合进行定时调用
            videoStreamSignCollect.add(redisKey);
        }

    }

    /**
     * 获取视频流ID
     * @return 视频流ID
     */
    private String getVideoStreamId(String redisKey){
        // key分割标识
        final String videoStreamSplit = ":";
        return redisKey.substring(redisKey.lastIndexOf(videoStreamSplit)+1,redisKey.length());
    }

    /**
     * 添加心跳缓存
     * @param redisKey 视频流字符
     */
    public void addHeartbeat(String redisKey){
        videoStreamSignCollect.add(redisKey = streamCachePrefix + redisKey);
        // 有效时间
        redisUtils.set(redisKey,System.currentTimeMillis(),streamExpireTime);
    }

    /**
     * 删除心跳缓存
     * @param redisKey 视频流字符
     */
    public void removeHeartbeat(String redisKey){
        videoStreamSignCollect.remove(redisKey = streamCachePrefix + redisKey);
        // 清除redis缓存
        redisUtils.delete(redisKey);
    }

}
