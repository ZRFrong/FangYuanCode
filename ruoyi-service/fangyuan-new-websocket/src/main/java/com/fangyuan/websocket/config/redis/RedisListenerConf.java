package com.fangyuan.websocket.config.redis;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @Description: redis监听配置类
 * @Author zheng
 * @Date 2021/5/31 11:29
 * @Version 1.0
 */
@Configuration
public class RedisListenerConf {

    @Bean
    public RedisMessageListenerContainer messageListener(RedisConnectionFactory connectionFactory){
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer ();
        redisMessageListenerContainer.setConnectionFactory(connectionFactory);
        return redisMessageListenerContainer;
    }
}
