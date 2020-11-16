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
        String regex = "^1(3([0-35-9]\\d|4[1-8])|4[14-9]\\d|5([0125689]\\d|7[1-79])|66\\d|7[2-35-8]\\d|8\\d{2}|9[89]\\d)\\d{7}$";
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
        String phone = "13934154053";
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
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