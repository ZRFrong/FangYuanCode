package com.ruoyi.auth.service;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.thread.ThreadUtil;
import com.ruoyi.system.feign.RemoteSocketIoClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.redis.annotation.RedisEvict;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.system.domain.SysUser;

import cn.hutool.core.util.IdUtil;

@Service("accessTokenService")
@Log4j2
public class AccessTokenService
{
    @Autowired
    private RedisUtils     redis;
    /** redis缓存用户session集合 */
    private final BoundHashOperations<String, String, String> userRefSession ;
    @Autowired
    private RemoteSocketIoClient remoteSocketIoClient;

    /**
     * 12小时后过期
     */
    private final static long   EXPIRE        = 12 * 60 * 60;

    private final static String ACCESS_TOKEN  = Constants.ACCESS_TOKEN;

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    public AccessTokenService(RedisTemplate<String, String> redisTemplate){
        this.userRefSession = redisTemplate.boundHashOps("socket_user_session:");
    }

    public SysUser queryByToken(String token)
    {
        return redis.get(ACCESS_TOKEN + token, SysUser.class);
    }

    @RedisEvict(key = "user_perms", fieldKey = "#sysUser.userId")
    public Map<String, Object> createToken(SysUser sysUser)
    {
        // 生成token
        String token = IdUtil.fastSimpleUUID();
        // 保存或更新用户token
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", sysUser.getUserId());
        map.put("token", token);
        map.put("expire", EXPIRE);
        // expireToken(userId);
        redis.set(ACCESS_TOKEN + token, sysUser, EXPIRE);
        redis.set(ACCESS_USERID + sysUser.getUserId(), token, EXPIRE);
        closeSocketClient(sysUser.getUserId());
        return map;
    }

    /**
     * 关闭socket连接
     * @param userId 用户ID
     */
    private void closeSocketClient(Long userId){
        String sessionId = userRefSession.get(String.valueOf(userId));
        log.info("远程调用关闭socket连接 userId:{} sessionId:{}",userId,sessionId);
        if(StringUtils.isNotBlank(sessionId)){
            ThreadUtil.execAsync(() -> {
                remoteSocketIoClient.closeBySessionId(sessionId);
            });
        }
    }

    public void expireToken(long userId)
    {
        String token = redis.get(ACCESS_USERID + userId);
        if (StringUtils.isNotBlank(token))
        {
            redis.delete(ACCESS_USERID + userId);
            redis.delete(ACCESS_TOKEN + token);
            closeSocketClient(userId);
        }
    }
}