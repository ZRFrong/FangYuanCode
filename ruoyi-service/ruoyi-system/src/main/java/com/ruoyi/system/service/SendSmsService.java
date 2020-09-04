package com.ruoyi.system.service;


public interface SendSmsService {
    /**
     * 发送短信
     * @param phone 手机号
     * @param signName 短信签名
     * @return string
     */
    String sendSms(String phone, String signName);
}
