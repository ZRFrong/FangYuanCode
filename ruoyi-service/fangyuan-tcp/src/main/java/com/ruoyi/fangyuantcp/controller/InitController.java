package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.fangyuantcp.timing.TaskHeartbeat;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@Log4j2
public class InitController {

    @Value("${person.listen-port}")
    private int port;

    /*
    *加载bean的时候初始化一些服务
    * */


    @PostConstruct
    public void listen() throws Exception {

        /*
         * 开启端口监听
         * */
        new Thread() {
            public void run() {
                System.out.println("启动监听");//这里是线程需要做的事情
                try {
                    NettyServer.bind(port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();




        /*
        * 开启手动 自动装填查询
        * */



    }
}
