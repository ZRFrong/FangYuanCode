package com.ruoyi.common.utils;

import java.util.regex.Pattern;

/**
 * @author Mr.ZHAO
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.common.utils.HeartbeatUtils.java
 * @Description Mr.ZHAO
 * @createTime 2021年06月09日 13:59:00
 */
public class HeartbeatUtils {

    /**
     * 以 pisitai- 开头 +  五位数字 + -dapeng 结尾
     * */
    private static final String HEARTBEAT_RULE = "^pisitai-\\d{5}-dapeng_0\\d{1}$";

    /**
     * ^[a-z]{5,9}-\d{5,9}-[a-z]{5,7}_\d{2}$
     * 以 5到9位小写字母开头 +  - 中划线 +  五到 9 位数字  +  -  + 以 5到7位小写字符结尾
     * */
    private static final String HEARTBEAT_RULE2 = "^[a-z]{5,9}-\\d{5,9}-[a-z]{5,7}_\\d{2}$";

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
