package com.ruoyi.fangyuantcp.service;

import org.springframework.stereotype.Service;

public interface OperateVentilateService {

    int operateTongFengHand(String heartbeatText, String equipmentNo, Integer i);

    int operateTongFengType(String heartbeatText, String equipmentNo, Integer i, String hex);

}
