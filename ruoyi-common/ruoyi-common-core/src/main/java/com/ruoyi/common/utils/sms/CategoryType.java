package com.ruoyi.common.utils.sms;

/**
 * 存放常量的类
 */

public enum  CategoryType {
    /**
     *单日发送短信总条数
     */
    SMS_NUM,
    /**
     * 用户单日发送短信总条数
     */
    USER_DAY_NUM_,
    /**
     * 单个ip发送短信总条数
     */
    IP_DAY_NUM_,
    /**
     * 一个小时内发送短信的总条数
     */
    USER_HOUR_NUM_,
    /**
     * 用戶验证码
     */
    USER_IDENTIFYING_CODE_,
    /**
     *
     */
    USER_CODE_SUCCESS_,
    /**
     *  验证码验证次数
     */
    USER_CODE_CHECK_SUM_,
    /**
     * redis存放的token
     */
    USER_TOKEN_,
    /**
     *手机号登录次数
     */
    PHONE_LOGIN_NUM_
}
