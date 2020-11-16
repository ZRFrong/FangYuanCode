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
    RESULT_BLOCK(11,"该文件存在违规行为，请更换后再试！"),
    /**
     *
     */
    VIOLATION_FAILURE(12,"人工审核不通过！"),
    /**
     *
     */
    UNDER_REVIEW(13,"正在审核中！"),
    /**
     *
     */
    NULL_ATTENTION(14,"您当前还没有关注的人！"),
    /**
     *
     */
    NULL_FANS(15,"您暂时没有粉丝！"),
    /**
     *
     */
    PHONE_ERROR(16,"手机号错误！"),
    /**
     *
     */
    PHONE_NOT_REGISTER(17,"手机号尚未注册，请先去注册！"),
    /**
     *
     */
    LOGIN_HOUR_ERROR(18,"登录太过频繁请稍后再试！"),
    /**
     *
     */
    PASSWORD_ERROE(19,"用户名或者密码输入错误！"),
    /**
     *
     */
    PASSWORD_NOT_RULE(20,"您的密码不符合规则，请重新再试！"),
    /**
     *
     */
    WE_CHAT_PAY_ERROR(21,"下单失败！"),
    /**
     *
     */
    GIVE_LIKE_ERROR(22,"对不起您反悔的太快了，请稍后再试！"),
    /**
     *
     */
    LOGIN_ERROR(23,"您已经在其他设备登陆了，该设备禁止登陆！"),
    /**
     *
     */
    DYNAMIC_IS_NULL(24,"已经到底部了！"),
    /**
     *
     */
    REFRESH_TOKEN_LOSE(401,"")
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
