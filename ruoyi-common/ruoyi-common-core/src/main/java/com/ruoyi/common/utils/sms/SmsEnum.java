package com.ruoyi.common.utils.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  SmsEnum {
    /**
     * 登录短信模板
     */
    REGISTER_TEMPLATE_CODE("1","SMS_183825098"),
    /**
     * 登录短信签名
     */
    REGISTER_SIGN_NAME("1","方圆社区"),

            ;

    private String describe;
    private String value;

}
