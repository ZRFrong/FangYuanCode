package com.ruoyi.system.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.ruoyi.common.json.JSONUtils;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.CategoryType;
import com.ruoyi.common.utils.sms.NumberUtils;
import com.ruoyi.system.config.SmsConfig;
import com.ruoyi.system.mapper.DbUserMapper;
import com.ruoyi.system.service.SendSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SendSmsServiceImpl implements SendSmsService {

    @Autowired
    private JSONUtils jsonUtils;

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private IAcsClient client;

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private DbUserMapper dbUserMapper;

    @Override
    public String sendSms(String phone, String signName,String templateCode) {
        CommonRequest request = getCommonRequest();
        request.setSysAction(smsConfig.getSysActionSendSms());
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", smsConfig.getSignName().get(signName));
        request.putQueryParameter("TemplateCode", smsConfig.getTemplateCode().get(templateCode));
        HashMap<String, String> hashMap = new HashMap<>();
        String s = NumberUtils.generateCode(smsConfig.getCodeLength());
        hashMap.put("code", s);
        request.putQueryParameter("TemplateParam", jsonUtils.mapToString(hashMap));
        String message = null;
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map<String,String> map = jsonUtils.stringToMap(data);
            message=map.get("Message");
            if("OK".equals(map.get("Message"))){
                redisUtils.set(CategoryType.USER_IDENTIFYING_CODE_+phone,s,RedisTimeConf.FIVE_MINUTE);//后台纪录验证码2分钟不过其
                return map.get("Code");
            }
            log.error("请求出错："+message);
        } catch (ClientException e) {
            e.printStackTrace();
            log.error("请求出错: "+ message );
        }
        return null;
    }

    @Override
    public String sendBatchSms(String signName, String templateCode) {
        CommonRequest request = getCommonRequest();
        Boolean flag = true;
        String is_susses = null;
        Integer incremental = 0;
        while (flag){
            ArrayList<String> phones = new ArrayList<>();
            ArrayList<String> signNames = new ArrayList<>();

            for (int i = 0; i <100 ; i++) {
                String phone = dbUserMapper.queryUserPhone(incremental++,1);
                if (StringUtils.isNotEmpty(phone)){
                    phones.add(phone);
                    signNames.add(signName);
                }
                flag = false;
                break;
            }
            request.putQueryParameter("PhoneNumberJson",jsonUtils.listToJsonArray(phones));
            request.putQueryParameter("SignNameJson",jsonUtils.listToJsonArray(signNames));
            request.putQueryParameter("SignNameJson",smsConfig.getTemplateCode().get(templateCode));
            request.setSysAction(smsConfig.getSysActionSendBatchSms());
            Map<String,String> map = null;
            try {
                CommonResponse response = client.getCommonResponse(request);
                map = jsonUtils.stringToMap(response.getData());
                is_susses = map.get("Code");
                if(!"OK".equals(is_susses)){
                    log.error(map.get("BizId")+"发送失败："+  map.get("Message"));
                }
            } catch (ClientException e) {
                log.error(map.get("BizId")+"发送失败："+  map.get("Message"));
                e.printStackTrace();
            }

        }
        String phone = dbUserMapper.queryUserPhone(0,1);
        return is_susses;
    }

    //LTAI4GKgwtvuAiRAuVKXhYSY
    //5KKPLPvGrhevKExHi8HSkDbgl7zq0f
    //飞天遁地旅游网
//    //SMS_173252813


    public CommonRequest getCommonRequest(){
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(smsConfig.getDomain());
        request.setSysVersion("2017-05-25");
        request.putQueryParameter("RegionId", smsConfig.getRegionId());
        return request;
    }
    public static void main(String[] args){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("15135006102");
        strings.add("15135002102");
        System.out.println();
    }
}

