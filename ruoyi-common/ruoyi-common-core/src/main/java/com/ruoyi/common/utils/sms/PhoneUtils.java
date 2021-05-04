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

    /**
     * 对字符串指定位置 替换指定字符
     * @since: 1.0.0
     * @param phone 手机号
     * @param beginIndex 开始替换位置
     * @param endIndex 结束位置
     * @param replaceStr 要替换的字符类型
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/4/12 14:17
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public static String replacePhone(String phone,int beginIndex,int endIndex,String replaceStr){
        if (StringUtils.isEmpty(phone)){
            return null;
        }
        String str = "";
        for (int i = 0; i < endIndex - beginIndex; i++) {
            str = str + replaceStr;
        }
        return phone.substring(0,beginIndex)+str+phone.substring(endIndex,phone.length());
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