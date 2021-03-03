package com.ruoyi.fangyuantcp.service;

import com.ruoyi.common.core.domain.R;

public interface OperateVentilateService {

    R operateTongFengHand(String heartbeatText, String equipmentNo, Integer i);

    R operateTongFengType(String heartbeatText, String equipmentNo, Integer i, String hex);

}
