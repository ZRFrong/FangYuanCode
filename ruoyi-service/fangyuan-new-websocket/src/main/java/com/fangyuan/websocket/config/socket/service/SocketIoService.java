package com.fangyuan.websocket.config.socket.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fangyuan.websocket.config.socket.listener.SocketIoListenerHandle;
import com.fangyuan.websocket.constant.SocketListenerEventConstant;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.aes.TokenUtils;

import com.ruoyi.system.domain.socket.PushMessageVO;
import com.ruoyi.system.domain.socket.ReceiveMsgPo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: socket接口逻辑处理类
 * @Author zheng
 * @Date 2021/6/8 19:07
 * @Version 1.0
 */
@Component("SocketIoService")
@Log4j2
public class SocketIoService implements ApplicationContextAware{

    private final RedisTemplate<String, String> redisTemplate;
    /** redis缓存用户session集合 */
    private BoundHashOperations<String, String, String> userRefSession ;
    @Value("${com.fangyuan.token.aes.accessTokenKey}")
    private String accessTokenKey;
    // 用户session缓存key
    private static final String USER_SESSION_CACHE_KEY = "socket_user_session:";
    private ApplicationContext applicationContext;
    private SocketIoListenerHandle socketIoListenerHandle;


    public SocketIoService(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.userRefSession = redisTemplate.boundHashOps(USER_SESSION_CACHE_KEY);
    }

    /**
     * 鉴权通道TOKEN
     * @param data 消息内容
     * @return 返回用户ID
     */
    public String checkToken(String sessionId,ReceiveMsgPo data){
        String userId = "";
        Map<String, Object> userMap = TokenUtils.verifyToken(data.getToken(), accessTokenKey);
        if(CollectionUtil.isNotEmpty(userMap)){
            Object id = userMap.get("id");
            if(id != null){
                String cacheToken = redisTemplate.opsForValue().get(RedisKeyConf.APP_ACCESS_TOKEN_.name() + id.toString());
                if(StringUtils.equals(data.getToken(),cacheToken)){
                    userRefSession.put(userId,sessionId);
                    userId = id.toString();
                }
            }
        }
        return userId;
    }


    /**
     * 删除用户绑定session
     * @param sessionId 通道sessionId
     * @return 返回用户ID
     */
    public void removeUserSession(String sessionId){
        Map<String, String> entries = userRefSession.entries();
        if(CollectionUtil.isNotEmpty(entries)){
            entries.entrySet().forEach( v -> {
                if(StringUtils.equals(v.getValue(),sessionId) ){
                    userRefSession.delete(v.getKey());
                }
            });
        }
    }

    /**
     * 推送消息
     * @param messageInfo 服务端推送消息
     */
    public void pushMessage(PushMessageVO messageInfo){
        if(socketIoListenerHandle == null){
            socketIoListenerHandle = applicationContext.getBean(SocketIoListenerHandle.class);
        }
        // 确定推送类型
        final String eventKey = ObjectUtil.equal(
                messageInfo.getMessageType(),SocketListenerEventConstant.IM_EVENT
        ) ? SocketListenerEventConstant.IM_EVENT : SocketListenerEventConstant.DEVICE_EVENT;
        String[] userArr = messageInfo.getMessageTarget().split(",");
        for (String userId : userArr) {
            final String sessionId = userRefSession.get(userId);
            if(StringUtils.isNotBlank(sessionId)){
                socketIoListenerHandle.pushMessage(sessionId,
                        eventKey,
                        R.ok(messageInfo.getMessageInfo()));
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
