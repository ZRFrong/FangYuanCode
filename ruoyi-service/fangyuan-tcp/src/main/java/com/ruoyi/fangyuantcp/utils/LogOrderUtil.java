package com.ruoyi.fangyuantcp.utils;

import com.ruoyi.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description: 日志指令记录工具类
 * @Author zheng
 * @Date 2021/6/9 9:17
 * @Version 1.0
 */
@Log4j2
@Component
public class LogOrderUtil {


    /**
     * 记录指令发送信息
     * @param heartName 心跳
     * @param orderMsg 指令
     */
    public void recordSend(String heartName,String orderMsg,byte []byteOrder){
        log.info("LogOrderUtil.recordSend 发送指令 heartName:【{}】 orderMsg:【{}】",heartName,orderMsg);
    }

    /**
     * 记录指令返回信息
     * @param heartName 心跳
     * @param orderMsgBack 返回指令
     */
    public void recordBack(String heartName,String orderMsgBack){
        log.info("LogOrderUtil.recordBack 返回指令 heartName:【{}】 orderMsgBack:【{}】",heartName,orderMsgBack);
    }

    /**
     * 记录指令返回信息
     * @param heartName 心跳
     * @param orderMsgBack 返回指令
     */
    public void recordFollowBack(String heartName, String orderMsgBack, String method){
        try {
            method = StringUtils.isNotBlank(method) ? method : getInvokeMethod();
        }catch (Exception e){
            log.error(e);
        }
        log.info("LogOrderUtil.recordFollowBack 指令执行路径追踪 heartName:【{}】 orderMsgBack:【{}】 method:【{}】",heartName,orderMsgBack,method);
    }

    public String getInvokeMethod(){
        StackTraceElement[] st = new Throwable().getStackTrace();
        StringBuilder methodName = new StringBuilder(st[st.length - 1].getClassName());
        for (StackTraceElement stackTraceElement : st) {
            methodName.append("-->").append(stackTraceElement.getMethodName());
        }
        return methodName.toString();
    }

}
