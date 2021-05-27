package com.ruoyi.fangyuantcp.service;

import com.ruoyi.common.core.domain.R;

public interface OperateVentilateService {

    R operateTongFengHand(String heartbeatText, String equipmentNo, Integer i);

    R operateTongFengType(String heartbeatText, String equipmentNo, Integer i, String hex);

    /**
     * 补光定时，或者取消
     * @since: 2.0.0
     * @param heartbeatText
     * @param equipmentNo
     * @param flag
     * @param startTime
     * @param stopTime
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/5/17 11:38
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    int operateLight(String heartbeatText, String equipmentNo, Integer flag, String startTime, String stopTime);

    /**
     * 按照百分比操控卷膜
     * @since: 2.0.0
     * @param heartbeatText 心跳
     * @param equipmentNo 设备唯一编码
     * @param percentage 百分比
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/5/17 15:46
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    int percentageOperate(String heartbeatText, String equipmentNo, Integer percentage,String operationText);
}
