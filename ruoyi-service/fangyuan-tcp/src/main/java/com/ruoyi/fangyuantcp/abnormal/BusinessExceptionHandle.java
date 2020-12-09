package com.ruoyi.fangyuantcp.abnormal;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbAbnormalInfoService1;
import com.ruoyi.system.domain.DbAbnormalInfo;
import com.ruoyi.system.feign.RemoteApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class BusinessExceptionHandle {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private  final  String DROPS="掉线异常";
    private  final  String FAULT="故障";
    private  final  String OPERATIONEXCEPTIONS="操作返回异常";
    private RemoteApiService remoteApiService = SpringUtils.getBean(RemoteApiService.class);
    private IDbAbnormalInfoService1 abnormalInfoSave = SpringUtils.getBean(IDbAbnormalInfoService1.class);

    /**
     * 掉线异常处理
     */
    @ExceptionHandler({DropsExceptions.class})
    public R dropsExceptions(DropsExceptions e) {

        DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
        dbAbnormalInfo.setAlarmTime(new Date());

        dbAbnormalInfo.setAlarmExplain(e.getMessage());

        dbAbnormalInfo.setObjectType(e.getCode());
        dbAbnormalInfo.setDbEquipmentId(Long.valueOf(e.getEquipmentId()));

        dbAbnormalInfo.setFaultType(DROPS);

//        abnormalInfoSave.insertDbAbnormalInfo(dbAbnormalInfo);
        remoteApiService.abnormalInfoSave(dbAbnormalInfo);

        return R.error(e.getMessage() + "设备掉线异常");
    }

    /**
     * 设备故障处理
     */
    @ExceptionHandler({FaultExceptions.class})
    public R faultExceptions(FaultExceptions e) {
        DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
        dbAbnormalInfo.setAlarmTime(new Date());
        dbAbnormalInfo.setAlarmExplain(e.getMessage());
        dbAbnormalInfo.setAlarmExplain(e.getCode());
        dbAbnormalInfo.setFaultType(FAULT);
        dbAbnormalInfo.setText(e.getEquipmentId());
//        abnormalInfoSave.saveEquimentOperation(dbAbnormalInfo);
        remoteApiService.saveEquimentOperation(dbAbnormalInfo);
        return R.error(e.getMessage() + "设备故障异常");
    }

    /**
     * 设备操作处理
     */
    @ExceptionHandler({OperationExceptions.class})
    public R operationExceptions(OperationExceptions e) {
        DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
        dbAbnormalInfo.setAlarmTime(new Date());
        dbAbnormalInfo.setAlarmExplain(e.getMessage());
        dbAbnormalInfo.setObjectType(e.getCode());

        dbAbnormalInfo.setAlarmExplain(OPERATIONEXCEPTIONS);
        dbAbnormalInfo.setText(e.getEquipmentId());
//        abnormalInfoSave.saveEquimentOperation(dbAbnormalInfo);
        remoteApiService.saveEquimentOperation(dbAbnormalInfo);

        return R.error(e.getMessage() + "设备操作异常");
    }


}
