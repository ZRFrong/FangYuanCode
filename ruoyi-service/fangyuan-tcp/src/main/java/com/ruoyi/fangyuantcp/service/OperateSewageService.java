package com.ruoyi.fangyuantcp.service;

import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbSewage;

import java.util.List;

public interface OperateSewageService {



    int updateDilutability(String dilutability,DbSewage dbSewage,String type);

    int turnOperateSewage(String operateSewageOff, List<DbEquipment> dbEquipments);
}
