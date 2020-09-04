package com.ruoyi.common.utils.sms;

import lombok.Getter;

@Getter
public class SmsData {
    /**
     * 阿里云短信服务accessKeyId
     */
    public static final String  ACCESS_KEY_ID = "LTAI4FqBPzpcNbCX4CbrkKRG";
    /**
     * 限制每日短信的消耗量
     */
    public static final Integer SMS_NUM = 1000;
    /**
     * 单个用户每日发送短信受到的限制量
     */
    public static final Integer USER_DAY_NUM = 10;
    /**
     * 单个ip每日发送短信的量
     */
    public static final Integer IP_DAY_NUM = 500;
    /**
     * 单个用户每小时发送次数不得超过5
     */
    public static final Integer USER_HOUR_NUM = 5;

}
