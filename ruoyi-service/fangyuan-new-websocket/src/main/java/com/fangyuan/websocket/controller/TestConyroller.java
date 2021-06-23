package com.fangyuan.websocket.controller;

import com.fangyuan.websocket.config.socket.listener.SocketIoListenerHandle;
import com.ruoyi.common.constant.SocketListenerEventConstant;
import com.ruoyi.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author zheng
 * @Date 2021/6/16 20:39
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestConyroller {

    @Autowired
    private SocketIoListenerHandle socketIoListenerHandle;

    @GetMapping("/send/{msg}")
    public void send(@PathVariable String msg){
        socketIoListenerHandle.getOnLineUserSessionMap().entrySet().forEach(v ->{
            socketIoListenerHandle.pushMessage(v.getKey(),SocketListenerEventConstant.GENERAL_EVENT, R.data(msg));
        });
    }

    @GetMapping("/getConnect")
    public R getConnect(){
        return R.data(socketIoListenerHandle.getOnLineUserSessionMap().keySet());
    }
}
