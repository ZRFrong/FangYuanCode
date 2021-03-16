package com.ruoyi.common.utils.sms;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author just myself
 * @create 2020-03-12-20:30
 */
public class PhoneUtils {


    /**
     * 检查是否为正确的手机号格式
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone){
        if (StringUtils.isEmpty(phone)){
            return false;
        }
        //手机号正则
        String regex = "^(1)\\d{10}$";
        //正确的手机号应为11位,正则匹配相对较慢,应避免频繁进行正则验证
        if (phone.trim().length() != 11){//去空格后在进行判断
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(phone);
        boolean b = matcher.matches();
        return b;
    }


    public static void main(String[] args) {
        String phone = "114982325281";
        String regex = "^(1)\\d{10}$";
        if (phone.length() != 11) {
            System.out.println("手机号应为11位数");
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            System.out.println(isMatch);
            if (isMatch) {
                System.out.println("您的手机号" + phone + "是正确格式@——@");
            } else {
                System.out.println("您的手机号" + phone + "是错误格式！！！");
            }
        }
    }
}