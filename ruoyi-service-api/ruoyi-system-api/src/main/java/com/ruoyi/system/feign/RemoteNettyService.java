package com.ruoyi.system.feign;


import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.feign.factory.RemoteTcpFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * netty服务接口 处理设备下发指令
 * */
@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANNETTY, fallbackFactory = RemoteTcpFallbackFactory.class)
public interface RemoteNettyService {

    /*
     * netty服务接收下发指令
     * */
    @PostMapping(value = "receive/allOrder")
    public R receiveAllOrder(@RequestBody DbOperationVo dbOperationVo);
}
