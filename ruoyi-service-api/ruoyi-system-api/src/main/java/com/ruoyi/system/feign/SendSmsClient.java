package com.ruoyi.system.feign;

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "ruoyi-system", fallbackFactory = RemoteUserFallbackFactory.class)
public interface SendSmsClient {


    @GetMapping("sms/sendSms/{phone}/{signName}/{templateCode}")
    R sendSms(@PathVariable String phone, @PathVariable String signName , @PathVariable String templateCode);


}
