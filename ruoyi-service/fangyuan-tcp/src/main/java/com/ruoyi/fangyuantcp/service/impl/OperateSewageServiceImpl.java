package com.ruoyi.fangyuantcp.service.impl;

import com.ruoyi.fangyuantcp.service.OperateSewageService;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbSewage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateSewageServiceImpl implements OperateSewageService {


    /*
    * 水肥更改施肥量 和 稀释度
    *
    * 施肥量和稀释度正常是一位小数，乘10后传输
        施肥量设定（地址200）
        发送：01 06 00 C8 01 2C 08 79
        01站号，06功能码，00C8施肥量地址，012C设定值（300）
        返回：01 06 00 C8 01 2C 08 79
        稀释度设定（地址201）
    * */

    @Override
    public int turnOperateSewage(String operateSewageOff, List<DbEquipment> dbEquipments) {
        for (DbEquipment dbEquipment : dbEquipments) {
            DbOperationVo dbOperationVo = new DbOperationVo();
            dbOperationVo.setHeartName(dbEquipment.getHeartbeatText()); 
            dbEquipment.getEquipmentNoString();
        }

        return 0;
    }

    @Override
    public int updateDilutability(String dilutability, DbSewage dbSewage,String type) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setOperationText(type);
        dbOperationVo.setHeartName(dbSewage.getHeartName());
        dbOperationVo.setFacility(dbSewage.getEquipmentNo());
        return SendCodeUtils.queryType(dbOperationVo);
    }
}
