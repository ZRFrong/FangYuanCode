package com.ruoyi.system.feign;

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.factory.RemoteApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ZHAOXIAOSI
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.feign.DbTcpCilentService.java
 * @Description
 * @createTime 2021年04月28日 11:10:00
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANTCP, fallbackFactory = RemoteApiFallbackFactory.class)
public interface DbTcpClientService {
    @GetMapping("/client/queryOne")
    public R queryOne(String heartName);
}
