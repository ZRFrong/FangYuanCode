package com.ruoyi.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mr.ZHAO
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.common.utils.HeartbeatUtils.java
 * @Description Mr.ZHAO
 * @createTime 2021年06月09日 13:59:00
 */
public class HeartbeatUtils {

    private static final String HEARTBEAT_RULE = "^pisitai-\\d{5,5}-dapeng_\\d{2,2}$";

    /**
     * 设备心跳规则校验
     * @since: 2.0.0
     * @param heartbeat 设备心跳
     * @return: boolean
     * @author: ZHAOXIAOSI
     * @date: 2021/6/9 14:08
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public static boolean checkHeartbeat(String heartbeat){
        return Pattern.compile(HEARTBEAT_RULE).matcher(heartbeat.trim()).matches();
    }

}
