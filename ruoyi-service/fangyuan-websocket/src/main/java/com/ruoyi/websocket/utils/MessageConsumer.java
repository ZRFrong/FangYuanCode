package com.ruoyi.websocket.utils;


import com.alibaba.fastjson.JSON;
import com.ruoyi.common.redis.wsmsg.SocketMsg;
import com.ruoyi.websocket.conf.WebSocketServer;

import java.io.IOException;

public class MessageConsumer{

    public void receiveMessage(String message) throws IOException {
        System.out.println(message);
        SocketMsg socketMsg = JSON.parseObject(JSON.parse(message).toString(), SocketMsg.class);
        WebSocketServer.sendInfo(socketMsg,socketMsg.getUserId());
        System.out.println(message+"--------------------------------------------------");
    }

    public static void main(String[] args){
        String str = "{\"msg\":\"Hello World!\",\"msgType\":\"INFO\",\"userId\":\"100\"}";
        System.out.println(JSON.parseObject(str, SocketMsg.class).toString());
    }
}
