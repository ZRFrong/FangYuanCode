package com.ruoyi.fangyuantcp.conf.redis;

import com.ruoyi.fangyuantcp.processingCode.SocketSendUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @Description: redis过期key监听
 * @Author zheng
 * @Date 2021/5/31 11:18
 * @Version 1.0
 */
@Log4j2
@Component
public class RedisExpireKeyListener extends KeyExpirationEventMessageListener {

    private final SocketSendUtil socketSendUtil;
    /* key分割标识 */
    private static final String split = ":";
    public RedisExpireKeyListener(RedisMessageListenerContainer listenerContainer,SocketSendUtil socketSendUtil) {
        super(listenerContainer);
        this.socketSendUtil = socketSendUtil;
    }

    /**
     * 监听redis过期key
     * @param message key
     */
    @Override
    public void onMessage(Message message, byte[] pattern){
        String key = message.toString();
        log.info("RedisExpireKeyListener.onMessage 失效key：[{}]",key);
        if(key.startsWith(SocketSendUtil.SOCKET_ORDER_LOCK_PRE)){
            // 唤醒对应设备等待下发指令执行
            socketSendUtil.notifyHeartName(key.substring(key.lastIndexOf(split) + 1,key.length()));
        }
    }

}
