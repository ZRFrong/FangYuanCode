package com.ruoyi.fangyuanapi.aspect;


import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.fangyuanapi.service.IDbOperationRecordService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbOperationRecord;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//aop注解
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    private  static IDbLandService landService=SpringUtils.getBean(IDbLandService.class);
    private  static IDbEquipmentService dbEquipmentService=SpringUtils.getBean(IDbEquipmentService.class);


//    //切入点
//    @Pointcut(value = "@annotation(com.ruoyi.fangyuanapi.abnormal.OperationLog)")
//    private void pointcut( ) {
//
//    }


    /*
     * 方法执行后执行保存记录  环绕增强
     * 判断是否批量   ->   判断是设备，土地操作    ->添加操作记录
     * */
    @Around("@annotation(operationLog)")
    public Object processAuthority(ProceedingJoinPoint point, OperationLog operationLog) throws Throwable {
        String name = operationLog.OperationLogNmae();

        boolean type = operationLog.OperationLogType();
        String source = operationLog.OperationLogSource();
        //        处理参数名称以及参数值  成map
        Map<String, Object> maps = getMap(point);

//        判断选择存储类型  Determine type   返回操作记录类

        DbOperationRecord dbOperationRecord = determineType(name, type, source, maps);

        IDbOperationRecordService bean = SpringUtils.getBean(IDbOperationRecordService.class);
//        用户id
        dbOperationRecord.setDbUserId(Long.valueOf(ServletUtils.getRequest().getHeader(Constants.CURRENT_ID)));


        //调用目标方法
        R r = (R) point.proceed();
//        判断返回值是否成功
        if (r.get("code").equals("200")) {
            dbOperationRecord.setIsComplete(0);
        } else {
            dbOperationRecord.setIsComplete(1);
        }
        dbOperationRecord.setOperationTime(new Date());

        bean.insertDbOperationRecord(dbOperationRecord);
        logger.info("操作记录记录完成"+new Date() );
        return  r;
    }

    private DbOperationRecord determineType(String name, boolean type, String source, Map<String, Object> maps) {
        DbOperationRecord dbOperationRecord = new DbOperationRecord();


        if (name.equals(OperationLogType.EQUIPMENT)) {
            dbOperationRecord = fillInEquipment(maps, type);
        } else {
            dbOperationRecord = fillInLand(maps, type);
        }
        switch (source) {
            case OperationLogType.WEchat:
                dbOperationRecord.setOperationSource(0);

                break;
            case OperationLogType.WEB:
                dbOperationRecord.setOperationSource(2);

                break;
            case OperationLogType.APP:
                dbOperationRecord.setOperationSource(1);

                break;
        }
        return dbOperationRecord;
    }

    private DbOperationRecord fillInEquipment(Map<String, Object> maps, boolean type) {
        DbOperationRecord dbOperationRecord = new DbOperationRecord();
        dbOperationRecord.setOperationObjectType(1);
        DbEquipment id = dbEquipmentService.selectDbEquipmentById(Long.valueOf(maps.get("id").toString()));
        dbOperationRecord.setOperationObject(id.getEquipmentName());
        if (type) {
            //   是批量
            String text = OperationLogUtils.toOperationText(maps.get("type").toString(), maps.get("handleName").toString());
            dbOperationRecord.setOperationText(text);

        } else {
//            单独
            dbOperationRecord.setOperationText(maps.get("name").toString()+OperationLogUtils.toOperationText(maps.get("type").toString(), maps.get("handleName").toString()));
        }
        return dbOperationRecord;

    }
    private DbOperationRecord fillInLand(Map<String, Object> maps, boolean type) {
        DbOperationRecord dbOperationRecord = new DbOperationRecord();
        dbOperationRecord.setOperationObjectType(0);
        String ids = maps.get("ids").toString();
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : ids.split(",")) {

            DbLand dbLand = landService.selectDbLandById(Long.valueOf(s));
            stringBuilder.append(dbLand.getNickName()+"/n");
        }
        dbOperationRecord.setOperationObject(stringBuilder.toString());
        if (type) {
            //   是批量
            String text = OperationLogUtils.toOperationText(maps.get("type").toString(), maps.get("handleName").toString());
            dbOperationRecord.setOperationText(text);

        } else {
//            单独
            dbOperationRecord.setOperationText(maps.get("name").toString() + maps.get("handleName").toString());
        }
        return dbOperationRecord;
    }

    private Map<String, Object> getMap(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //参数名称
        String[] parameterNames = methodSignature.getParameterNames();
//        参数值
        Object[] args = point.getArgs();

        Map<String, Object> stringObjectHashMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            stringObjectHashMap.put(parameterNames[i], args[i]);
        }

        return stringObjectHashMap;
    }


}
