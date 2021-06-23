package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.MqExchangeConstant;
import com.ruoyi.common.constant.MqMessageConstant;
import com.ruoyi.common.constant.SocketListenerEventConstant;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbUser;
import com.ruoyi.system.domain.socket.PushMessageVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public R amqpTest(String phone,String nickname){
        ArrayList<String> list = new ArrayList<>();
        list.add("345");
        amqpTemplate.convertAndSend(MqExchangeConstant.SOCKET_MESSAGE_EXCHANGE,"socket_message_routing",JSONObject.toJSONString(PushMessageVO.builder()
                .messageInfo(new cn.hutool.json.JSONObject(JSONObject.toJSONString(DbUser.builder()
                        .phone(phone)
                        .nickname(nickname)
                        .build())))
                .messageTarget("345")
                .messageType(SocketListenerEventConstant.DEVICE_EVENT.toString())
                .build()));
        return R.ok();
    }

    public static void main(String[] args){
        ArrayList<String> list = new ArrayList( );
        list.add("1");
        list.add("2");
        String[] array = list.toArray(new String[list.size()]);
        System.out.println(Arrays.toString(array));

    }

}
