package com.ruoyi.system.feign;

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.factory.RemoteTcpFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description: socketio远程控制类
 * @Author zheng
 * @Date 2021/6/21 14:40
 * @Version 1.0
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUAN_SOCKET, fallbackFactory = RemoteTcpFallbackFactory.class)
public interface RemoteSocketIoClient {

    @GetMapping("closeByUserId/{userId}")
    public R closeByUserId(@PathVariable("userId") String userId);

    @GetMapping("closeBySessionId/{sessionId}")
    public R closeBySessionId(@PathVariable("sessionId") String sessionId);
}
