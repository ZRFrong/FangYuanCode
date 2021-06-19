package com.fangyuan.websocket.config.socket.listener;

import cn.hutool.core.util.ObjectUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fangyuan.websocket.config.socket.service.SocketIoService;
import com.ruoyi.common.constant.SocketListenerEventConstant;
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
    // 新建立通道缓存集合
    private final Map<String,SocketIOClient> tempUserSessionMap = new ConcurrentHashMap<>(500);

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
        String sessionId = client.getSessionId().toString();
        log.info("客户端:{}建立连接 " , sessionId);
        socketIoService.saveTempConnectSessionCache(sessionId);
        tempUserSessionMap.put(sessionId,client);
    }

    /**
     * 关闭连接时触发
     * @param client 客户端连接
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        final String sessionId = client.getSessionId().toString();
        socketIoService.removeUserSession(sessionId);
        socketIoService.removeConnectSessionCache(sessionId);
        onlineUserSessionMap.remove(sessionId);
        tempUserSessionMap.remove(sessionId);
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
        if(ObjectUtil.isNull(data) || StringUtils.isEmpty(socketIoService.checkToken(sessionId,data))){
            log.warn("authentication session:{} 鉴权失败 ",sessionId);
            client.disconnect();
            return;
        }
        // 记录用户信息和socket信息
        onlineUserSessionMap.put(sessionId,client);
        // 清除临时通道缓存
        tempUserSessionMap.remove(sessionId);
        socketIoService.removeConnectSessionCache(sessionId);
        pushMessage(sessionId,SocketListenerEventConstant.AUTH_EVENT,R.ok());
        log.info("authentication session:{} 鉴权成功 ",sessionId);
    }

    /**
     * 监听设备消息
     * @param client 通道连接
     * @param data 消息内容
     */
    @OnEvent(SocketListenerEventConstant.DEVICE_EVENT)
    public void deviceEvent(SocketIOClient client,  ReceiveMsgPo data){
        log.info("deviceEvent:{}",data);
    }

    /**
     * 监听通用指令消息
     * @param client 通道连接
     * @param msg 消息内容
     */
    @OnEvent(SocketListenerEventConstant.GENERAL_EVENT)
    public void generalEvent(SocketIOClient client,  String msg){
        log.info("generalEvent:{}",msg);
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
     * 获取在线session集合
     * @return session关系
     */
    public Map<String,SocketIOClient> getOnLineUserSessionMap(){
        return onlineUserSessionMap;
    }

    /**
     * 获取已建立未认证session集合
     * @return session集合
     */
    public Map<String,SocketIOClient> getTempUserSessionMap(){
        return tempUserSessionMap;
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
