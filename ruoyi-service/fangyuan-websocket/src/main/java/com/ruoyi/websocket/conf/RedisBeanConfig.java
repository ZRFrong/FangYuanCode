package com.ruoyi.websocket.conf;

import com.ruoyi.common.redis.wsmsg.WSTypeEnum;
import com.ruoyi.websocket.utils.MessageConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisBeanConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter channelOneAdapter, MessageListenerAdapter channelTwoAdapter) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        //监听频道1
        listenerContainer.addMessageListener(channelOneAdapter,new PatternTopic(WSTypeEnum.SYSTEM.name()));
        listenerContainer.addMessageListener(channelOneAdapter,new PatternTopic(WSTypeEnum.FANGYUANAPI.name()));

        return listenerContainer;
    }

    @Bean
    public MessageListenerAdapter channelOneAdapter(MessageConsumer messageConsumer) {
        return new MessageListenerAdapter(messageConsumer,"receiveMessage");
    }

    @Bean
    public MessageConsumer messageConsumer(){
        return new MessageConsumer();
    }
}
