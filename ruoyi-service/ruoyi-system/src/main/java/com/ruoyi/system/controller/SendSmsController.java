package com.ruoyi.system.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.CategoryType;
import com.ruoyi.common.utils.sms.PhoneUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.common.utils.sms.SmsData;
import com.ruoyi.system.service.SendSmsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sms")
public class SendSmsController extends BaseController {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SendSmsService sendSmsService;

    /**
     * 发送短信
     *
     * @param phone 手机号
     */
    @GetMapping("sendSms/{phone}/{signName}")
    @ApiOperation(value = "发送短信接口", notes = "发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号"),
            @ApiImplicitParam(name = "signName", value = "短信模板 0:登录短信 1：注册短信 2：通知短信 3：推广短信 ")
    })
    public R sendSms(@PathVariable String phone, @PathVariable String signName) throws ClientException {

        if (PhoneUtils.checkPhone(phone) && StringUtils.isNotEmpty(phone) && StringUtils.isNotEmpty(signName)) {

            String smsNum = StringUtils.isNotNull(redisUtils.get(CategoryType.SMS_NUM.name()));//单日短信发送总条数
            String dayNum = StringUtils.isNotNull(redisUtils.get(CategoryType.USER_DAY_NUM_.name() + phone));//当用户每日总条数
            String hourNum = StringUtils.isNotNull(redisUtils.get(CategoryType.USER_HOUR_NUM_.name() + phone));
            if (Integer.valueOf(smsNum) < SmsData.SMS_NUM) {
                if (Integer.valueOf(dayNum) < SmsData.USER_DAY_NUM) {
                    if (Integer.valueOf(hourNum) < SmsData.USER_HOUR_NUM) {
                        String result = sendSmsService.sendSms(phone, signName);
                        if ("OK".equals(result)) {
                            redisUtils.set(CategoryType.SMS_NUM.name(), Integer.valueOf(smsNum) + 1, RedisTimeConf.ONE_DAY);
                            redisUtils.set(CategoryType.USER_DAY_NUM_ + phone, Integer.valueOf(dayNum) + 1, RedisTimeConf.ONE_DAY);
                            redisUtils.set(CategoryType.USER_HOUR_NUM_ + phone, Integer.valueOf(hourNum) + 1, RedisTimeConf.ONE_HOUR);
                            return new R();
                        } else {
                            return R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage());
                        }
                    } else {
                        return R.error(ResultEnum.SMS_HOUR_ERROR.getCode(), ResultEnum.SMS_HOUR_ERROR.getMessage());
                    }
                } else {
                    return R.error(ResultEnum.SMS_DAY_ERROR.getCode(), ResultEnum.SMS_DAY_ERROR.getMessage());
                }
            } else {
                return R.error(ResultEnum.IMPOSE_ERROR.getCode(), ResultEnum.IMPOSE_ERROR.getMessage());
            }
        }
        return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
    }
}
