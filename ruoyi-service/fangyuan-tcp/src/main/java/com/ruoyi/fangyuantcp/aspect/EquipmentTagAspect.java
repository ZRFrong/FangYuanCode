package com.ruoyi.fangyuantcp.aspect;

/**
 * @NAME: EquipmentTagAspect
 * @USER: chensy
 * @DATE: 2021/6/14
 * @PROJECT_NAME: ruoyi-cloud
 * @Description 设备tag数据处理
 **/

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.fangyuantcp.mapper.DbTcpTypeMapper;
import com.ruoyi.system.domain.DbTcpType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class EquipmentTagAspect {

    @Autowired
    private DbTcpTypeMapper dbTcpTypeMapper;
    // 默认类型标识为tag 为设备传感器数据做展示
    protected static final String DEFAULT_TYPE = "tag";

    /**
     * 判断传感器数据是否为空 , 为空则查询对应tag数据
     * @param proceedingJoinPoint  切点
     * @param equipmentTag 注解
     * @return 传感器数据
     */
    @Around("@annotation(equipmentTag)")
    public Object doProccess(ProceedingJoinPoint proceedingJoinPoint, EquipmentTag equipmentTag) throws Throwable {
        final List<DbTcpType> dbTcpTypeList = (List<DbTcpType>)proceedingJoinPoint.proceed();
        log.info("EquipmentTagAspect.doProccess dbTcpTypeList:{}",dbTcpTypeList);
        if(DEFAULT_TYPE.equals(equipmentTag.type()) && CollectionUtil.isEmpty(dbTcpTypeList)){
            Map<String, Object> params = FeedbackInterceptAspect.getMap(proceedingJoinPoint);
            DbTcpType dbTcpType = (DbTcpType) params.get("dbTcpType");
            try{
                List<DbTcpType> dbTcpTypeTagList = dbTcpTypeMapper.selectDbTcpTypeTagList(dbTcpType);
                log.info("EquipmentTagAspect.doProccess dbTcpTypeTagList:{}",dbTcpTypeTagList);
                if(CollectionUtil.isNotEmpty(dbTcpTypeTagList))
                    return dbTcpTypeTagList;
            }catch (Exception e){
                log.error("EquipmentTagAspect.doProccess error:{}",e);
            }
        }
        return dbTcpTypeList;
    }
}
