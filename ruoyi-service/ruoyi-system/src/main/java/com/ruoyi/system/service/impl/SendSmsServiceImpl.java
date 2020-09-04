package com.ruoyi.system.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.ruoyi.common.json.JSONUtils;
import com.ruoyi.common.utils.sms.NumberUtils;
import com.ruoyi.system.service.SendSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SendSmsServiceImpl implements SendSmsService {

    @Autowired
    private JSONUtils jsonUtils;

    @Override
    public String sendSms(String phone, String signName) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FqBPzpcNbCX4CbrkKRG", "Y7xFKankkQau5v98TfCPlQUGwVOY3G");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "方圆社区");
        request.putQueryParameter("TemplateCode", "SMS_183825098");
        HashMap<String, String> hashMap = new HashMap<>();
        String s = NumberUtils.generateCode(4);
        hashMap.put("code", s);
        request.putQueryParameter("TemplateParam", jsonUtils.mapToString(hashMap));
        String message = null;
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map<String,String> map = jsonUtils.stringToMap(data);
            if(map != null){
                message = map.get("Message");
                return map.get("Code");
            }
        } catch (ClientException e) {
            e.printStackTrace();
            log.error("请求出错: "+ message );
        }
        return null;
    }

    //LTAI4GKgwtvuAiRAuVKXhYSY
    //5KKPLPvGrhevKExHi8HSkDbgl7zq0f
    //飞天遁地旅游网
//    //SMS_173252813
//    public static void main(String[] args) {
//        //DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", SmsData.ACCESS_KEY_ID, "<accessSecret>");
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GKgwtvuAiRAuVKXhYSY", "5KKPLPvGrhevKExHi8HSkDbgl7zq0f");
//
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com");
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", "15135006102");
//        request.putQueryParameter("SignName", "飞天遁地旅游");
//        request.putQueryParameter("TemplateCode", "SMS_173252813");
//        request.putQueryParameter("TemplateParam", "{\"code\":\"1544\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            String data = response.getData();
//            System.out.println(response.getData());
//
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }

}

