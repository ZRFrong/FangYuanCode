package com.ruoyi.system.config;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendSmsBeanConfig {

    @Autowired
    private SmsConfig smsConfig;

    /**
     *
     * @return
     */
    @Bean
    public IAcsClient getDefaultProfile(){
        DefaultProfile profile = DefaultProfile.getProfile(smsConfig.getRegionId(), smsConfig.getAccessKey(), smsConfig.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }

}
