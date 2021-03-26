package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.redis.util.RedisMqUtils;
import com.ruoyi.common.redis.wsmsg.MsgType;
import com.ruoyi.common.redis.wsmsg.SocketMsg;
import com.ruoyi.common.redis.wsmsg.WSTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class RedisTest {

    @Autowired
    private RedisMqUtils redisTemplate;

    @GetMapping("ceshi/{userId}")
    public String test(@PathVariable String userId){
        SocketMsg msg = new SocketMsg("Hello World!",MsgType.INFO.name(),userId);
        redisTemplate.publishTopic(msg,WSTypeEnum.FANGYUANAPI);
        return "Hello World!";
    }

}
