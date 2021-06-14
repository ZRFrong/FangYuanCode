package com.fangyuan.websocket.config.socket.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fangyuan.websocket.config.socket.service.SocketIoService;
import com.fangyuan.websocket.constant.SocketListenerEventConstant;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.socket.ReceiveMsgPo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: SocketIo服务端监听实现类
 * @Author zheng
 * @Date 2021/6/8 19:05
 * @Version 1.0
 */
@Component(value = "socketIOListenerHandle")
@Log4j2
public class SocketIoListenerHandle {
    /**
     * socketIo的对象
     */
    @Autowired
    private SocketIOServer socketioServer;

    @Autowired
    @Lazy
    private SocketIoService socketIoService;

    // 在线用户session缓存集合 (包括之前在线 后续掉线的用户) key-value: 用户名-sessionId
    private final Map<String,SocketIOClient> onlineUserSessionMap = new ConcurrentHashMap<>(500);


    /**
     * bean初始化执行
     */
    @PostConstruct
    private void autoStartUp() {
        start();
    }

    /**
     * 开启socket服务
     */
    private void start(){
        socketioServer.start();
        log.info("SocketIoListenerHandle server start success ......");
    }

    /**
     * 客户端建立连接
     * @param client 客户端通道
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        log.info("客户端:{}建立连接 " , client.getSessionId());
    }

    /**
     * 关闭连接时触发
     * @param client 客户端连接
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        socketIoService.removeUserSession(client.getSessionId().toString());
        onlineUserSessionMap.remove(client.getSessionId().toString());
        log.info("客户端:{}断开连接" , client.getSessionId() );
    }

    /**
     * 鉴权通道
     * @param client 通道连接
     * @param data 消息内容
     */
    @OnEvent(SocketListenerEventConstant.AUTH_EVENT)
    public void authentication(SocketIOClient client,  ReceiveMsgPo data){
        String sessionId = client.getSessionId().toString();
        if(StringUtils.isEmpty(socketIoService.checkToken(sessionId,data))){
            log.warn("客户端通道鉴权失败");
            client.disconnect();
            return;
        }
        // 记录用户信息和socket信息
        onlineUserSessionMap.put(sessionId,client);
        pushMessage(client.getSessionId().toString(),SocketListenerEventConstant.AUTH_EVENT,R.ok());
    }

    /**
     * 监听设备消息
     * @param client 通道连接
     * @param data 消息内容
     */
    @OnEvent(SocketListenerEventConstant.DEVICE_EVENT)
    public void deviceEvent(SocketIOClient client,  ReceiveMsgPo data){
        log.info("deviceEvent:{}",data);
        client.sendEvent(SocketListenerEventConstant.DEVICE_EVENT,data);
    }

    /**
     * 监听通用指令消息
     * @param client 通道连接
     * @param msg 消息内容
     */
    @OnEvent(SocketListenerEventConstant.GENERAL_EVENT)
    public void generalEvent(SocketIOClient client,  String msg){
        log.info("generalEvent:{}",msg);
        client.sendEvent(SocketListenerEventConstant.GENERAL_EVENT,msg);
    }

    /**
     * 向客户端推送消息
     * @param sessionId 用户session
     * @param eventKey 监听事件名称
     * @param data 推送数据
     */
    public void pushMessage(String sessionId,String eventKey,R data){
        final SocketIOClient socketIOClient = onlineUserSessionMap.get(sessionId);
        if(socketIOClient != null)
            socketIOClient.sendEvent(eventKey,data);
    }


    /**
     * 系统停止前执行 关闭socket服务
     */
    @PreDestroy
    private void autoStop() {
        log.info("SocketIoListenerHandle server stop ......");
        if (socketioServer != null) {
            socketioServer.stop();
            socketioServer = null;
        }
        log.info("SocketIoListenerHandle server stop success ......");
    }

}
