package com.fangyuan.websocket.config.redis;

import com.fangyuan.websocket.config.socket.service.SocketIoService;
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

    private final SocketIoService socketIoService;
    /* key分割标识 */
    private static final String split = ":";
    public RedisExpireKeyListener(RedisMessageListenerContainer listenerContainer,SocketIoService socketIoService) {
        super(listenerContainer);
        this.socketIoService = socketIoService;
    }

    /**
     * 监听redis过期key
     * @param message key
     */
    @Override
    public void onMessage(Message message, byte[] pattern){
        String key = message.toString();
        log.info("RedisExpireKeyListener.onMessage 失效key：[{}]",key);
        if(key.startsWith(SocketIoService.USER_SESSION_KEEP_CACHE_KEY)){
            // 主动关闭已建立连接但未认证的通道
            socketIoService.closeConnectBySessionId(key.substring(key.lastIndexOf(split) + 1,key.length()));
        }
    }

}
