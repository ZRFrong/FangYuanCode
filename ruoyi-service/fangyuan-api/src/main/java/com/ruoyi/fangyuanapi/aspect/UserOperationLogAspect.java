package com.ruoyi.fangyuanapi.aspect;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbUserRecordService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbUserRecord;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class UserOperationLogAspect {


    private  static IDbEquipmentService dbEquipmentService=SpringUtils.getBean(IDbEquipmentService.class);

    @Autowired
    private OperationLogUtils operationLogUtils;


    @Around("@annotation(UserOperationLog)")
    public Object processAuthority (ProceedingJoinPoint point)throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        IDbUserRecordService bean = SpringUtils.getBean(IDbUserRecordService.class);
        Map<String, Object> map = OperationLogAspect.getMap(point);
        System.out.println(map.toString());
        DbUserRecord userRecord = new DbUserRecord();
        String userId = request.getHeader(Constants.CURRENT_ID);
        String parameter = URLDecoderString(request.getHeader("userOperate"));
        Map<String,String> data = (Map<String, String>) JSONObject.parse(parameter);
        String text = operationLogUtils.toOperationText(map.get("type").toString(), map.get("handleName").toString());
        R r = (R) point.proceed();
        DbEquipment equipment = dbEquipmentService.selectDbEquipmentById(Long.valueOf(map.get("id") + ""));
        String operate =  equipment.getEquipmentName()+">"+map.get("name")+">"+map.get("handleName")+">";
        userRecord.setDbUserId(Long.valueOf(userId));
        userRecord.setCreateTime(new Date());
        userRecord.setIp(data.get("ip"));
        userRecord.setNetworkState(data.get("networkState"));
        userRecord.setPhoneModel(data.get("phoneModel"));
        userRecord.setOperator(data.get("operator"));
        userRecord.setIpAddress(data.get("ipAddress"));
        if ("200".equals(r.get("code")+"")){
            operate = operate+"成功";
            userRecord.setOperate(operate);
        }else  if ("500".equals(r.get("code")+"")){
            operate = operate+"失败";
        userRecord.setOperate(operate);
        }
        bean.insertDbUserRecord(userRecord);
        return r;
    }

    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args){
        String s = URLDecoderString("%E5%B1%B1%E8%A5%BF%E7%9C%81%E5%A4%AA%E5%8E%9F%E5%B8%82%E5%B0%8F%E5%BA%97%E5%8C%BA%E6%98%8C%E7%9B%9B%E8%A5%BF%E8%A1%9719\",\"phoneModel\":\"%E8%AE%BE%E5%A4%87%E5%93%81%E7%89%8Cdevtools%E8%AE%BE%E5%A4%87%E5%9E%8B%E5%8F%B7iPhone%206%2F7%2F8");
        System.out.println(s);
    }
}
