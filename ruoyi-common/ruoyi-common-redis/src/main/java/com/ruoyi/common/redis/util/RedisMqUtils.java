package com.ruoyi.common.redis.util;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.redis.wsmsg.SocketMsg;
import com.ruoyi.common.redis.wsmsg.WSTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMqUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发布主题
     * @param socketMsg 包含userid 发送内容 消息类型
     * @param wsTypeEnum
     */
    public void publishTopic(SocketMsg socketMsg, WSTypeEnum wsTypeEnum){
        redisTemplate.convertAndSend(wsTypeEnum.name(),JSONObject.toJSONString(socketMsg));
    }
}
