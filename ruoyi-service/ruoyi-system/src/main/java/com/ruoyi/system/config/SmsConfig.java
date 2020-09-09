package com.ruoyi.system.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
public class SmsConfig {
    @Value("${al.sms.codeLength}")
    private Integer  codeLength;
    @Value("${al.sms.regionId}")
    private String  regionId;
    @Value("${al.sms.accessKey}")
    private String  accessKey;
    @Value("${al.sms.accessKeySecret}")
    private String  accessKeySecret;
    @Value("${al.sms.domain}")
    private String  domain;
    @Value("${al.sms.sysActionSendSms}")
    private String  sysActionSendSms;
    @Value("${al.sms.sysActionSendBatchSms}")
    private String  sysActionSendBatchSms;
    @Value("#{${al.sms.signName}}")
    private Map<String,String> signName;
    @Value("#{${al.sms.templateCode}}")
    private Map<String,String> templateCode;

}
