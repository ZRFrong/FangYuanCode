package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.fangyuantcp.tcp.NettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class InitController {

    @Value("${person.listen-port}")
    private int port;


    @PostConstruct
    public void listen() throws Exception {

        new Thread(){
            public void run(){
                System.out.println("启动监听");//这里是线程需要做的事情
                try {
                    NettyServer.bind(port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
