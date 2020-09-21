package com.ruoyi.common.utils.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 定义返回状态码和返回信息
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultEnum {
    /**
     *
     */
    PARAMETERS_ERROR(0,"参数异常"),
    /**
     *
     */
    IMPOSE_ERROR(1,"今日发送已超过限制请明天再来！"),
    /**
     *
     */
    SMS_DAY_ERROR(2,"您的操作太过频繁，请明天在来！"),
    /**
     *
     */
    SMS_HOUR_ERROR(3,"您的操作太过频繁，请稍后再来！"),
    /**
     *
     */
    SERVICE_BUSY(4,"服务繁忙！"),
    /**
     *
     */
    CODE_LOSE(5,"验证码已失效，请重新发送！"),
    /**
     *
     */
    CODE_ERROR(6,"验证码错误,请重新输入！"),
    /**
     *
     */
    CODE_SUCCESS(7,"参数错误，请从新发送验证码！"),
    /**
     *
     */
    TEXT_ILLEGAL(8,"内容包含敏感词，请核对后在试！"),
    /**
     *
     */
    IMAGES_AND_VIDOE_ILLEGAL(9,"！"),
    /**
     *
     */
    RESULT_REVIEW(10,"该内容正在人工审核中！"),
    /**
     *
     */
    RESULT_BLOCK(11,"该文件存在违规行为，请更换后再试！");
    ;
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
}
