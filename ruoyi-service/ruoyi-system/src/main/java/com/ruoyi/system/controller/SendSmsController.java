package com.ruoyi.system.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.exceptions.ClientException;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.md5.ZhaoMD5Utils;
import com.ruoyi.common.utils.sms.*;
import com.ruoyi.system.config.SmsConfig;
import com.ruoyi.system.service.SendSmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@RestController
@RequestMapping("sms")
@Api("关于短信和验证码校验接口")
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
    @GetMapping("sendSms/{phone}/{signName}/{templateCode}")
    @ApiOperation(value = "发送短信接口", notes = "发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号"),
            @ApiImplicitParam(name = "signName", value = "短信签名 1：方圆社区  "),
            @ApiImplicitParam(name = "templateCode", value = "短信模板 1：注册短信 2：设备验证 " )
    })
    public R sendSms(@PathVariable String phone, @PathVariable String signName , @PathVariable String templateCode){

        if (PhoneUtils.checkPhone(phone) && StringUtils.isNotEmpty(phone) && StringUtils.isNotEmpty(signName)) {
            if ("1".equals(templateCode)){//查询该用户手机号是否已经注册

            }
            String smsNum = StringUtils.isNotNull(redisUtils.get(CategoryType.SMS_NUM.name()));//单日短信发送总条数
            String dayNum = StringUtils.isNotNull(redisUtils.get(CategoryType.USER_DAY_NUM_.name() + phone));//当用户每日总条数
            String hourNum = StringUtils.isNotNull(redisUtils.get(CategoryType.USER_HOUR_NUM_.name() + phone));
            if (Integer.valueOf(smsNum) < SmsData.SMS_NUM) {
                if (Integer.valueOf(dayNum) < SmsData.USER_DAY_NUM) {
                    if (Integer.valueOf(hourNum) < SmsData.USER_HOUR_NUM) {
                        String result = sendSmsService.sendSms(phone, signName,templateCode);
                        if (result != null) {
                            redisUtils.set(CategoryType.SMS_NUM.name(), Integer.valueOf(smsNum) + 1, RedisTimeConf.ONE_DAY);
                            redisUtils.set(CategoryType.USER_DAY_NUM_ + phone, Integer.valueOf(dayNum) + 1, RedisTimeConf.ONE_DAY);
                            redisUtils.set(CategoryType.USER_HOUR_NUM_ + phone, Integer.valueOf(hourNum) + 1, RedisTimeConf.ONE_HOUR);
                            return R.data(result);
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

    /**
     * 验证码校验接口
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("checkCode/{phone}/{code}")
    @ApiOperation(value = "验证码校验",notes = "验证码校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "用户手机号！"),
            @ApiImplicitParam(name = "code",value = "发送的验证码！")
    })
    public R checkCode(@PathVariable String phone,@PathVariable String code){
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        String s = redisUtils.get(CategoryType.USER_IDENTIFYING_CODE_ + phone);
        if (StringUtils.isEmpty(s)){
            return R.error(ResultEnum.CODE_LOSE.getCode(),ResultEnum.CODE_LOSE.getMessage());
        }
        if (s.equals(code)){
            redisUtils.delete(CategoryType.USER_IDENTIFYING_CODE_ + phone);
            return R.ok();
        }else {
            String codeSum  = redisUtils.get(CategoryType.USER_CODE_CHECK_SUM_+phone);
            codeSum = codeSum ==null?"0":codeSum;
            if (Integer.valueOf(codeSum) > 5){
                redisUtils.delete(CategoryType.USER_IDENTIFYING_CODE_ + phone);
            }
            redisUtils.set(CategoryType.USER_CODE_CHECK_SUM_+phone,Integer.valueOf(codeSum)+1,RedisTimeConf.FIVE_MINUTE);
        }
        return R.error(ResultEnum.CODE_ERROR.getCode(),ResultEnum.CODE_ERROR.getMessage());
    }

    /**
     *
     * @param signName
     * @param templateCode
     * @return
     */
    @Action
    @ApiOperation(value = "批量短信接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "signName",value = "签名模板： 1："),
            @ApiImplicitParam(name = "templateCode",value = "模板code：1： ")
    })
    @GetMapping("sendBatchSms/{signName}/{templateCode}}")
    public R sendBatchSms(@PathVariable String signName,@PathVariable String templateCode){
        if (StringUtils.isEmpty(signName) || StringUtils.isEmpty(templateCode)){
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage()) ;
        }
        String result = sendSmsService.sendBatchSms(signName,templateCode);
        return new R();
    }
}
