package com.ruoyi.system.feign;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ruoyi-system", fallbackFactory = RemoteUserFallbackFactory.class)
public interface SendSmsClient {


    @GetMapping("sms/sendSms/{phone}/{signName}/{templateCode}")
    R sendSms(@PathVariable(value = "phone") String phone, @PathVariable(value = "signName") String signName , @PathVariable(value = "templateCode") String templateCode);

    @GetMapping("sms/checkCode/{phone}/{code}")
    R checkCode(@PathVariable(value = "phone") String phone,@PathVariable(value = "code") String code);
}
