package com.ruoyi.system.feign.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.feign.RemoteRoleService;
import com.ruoyi.system.feign.SendSmsClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteSmsFallbackFactory  implements FallbackFactory<SendSmsClient> {
    @Override
    public SendSmsClient create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new SendSmsClient() {
            @Override
            public R sendSms(String phone, String signName, String templateCode) {
                return null;
            }

            @Override
            public R checkCode(String phone, String code) {
                return null;
            }
        };
    }
}
