package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.core.domain.R;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Zhao
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.controller.AmqpTestController.java
 * @Description
 * @createTime 2021年06月17日 15:56:00
 */
@RestController
@RequestMapping("amqp")
public class AmqpTestController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("sendTest")
    public R amqpTest(){
        //amqpTemplate.convertAndSend();
        return R.ok();
    }

}
