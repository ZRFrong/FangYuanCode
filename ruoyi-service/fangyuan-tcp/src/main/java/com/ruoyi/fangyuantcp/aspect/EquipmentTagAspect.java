package com.ruoyi.fangyuantcp.aspect;

/**
 * @NAME: EquipmentTagAspect
 * @USER: chensy
 * @DATE: 2021/6/14
 * @PROJECT_NAME: ruoyi-cloud
 * @Description 设备tag数据处理
 **/

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuantcp.mapper.DbTcpTypeMapper;
import com.ruoyi.system.domain.DbTcpType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        List<DbTcpType> dbTcpTypeList = (List<DbTcpType>)proceedingJoinPoint.proceed();
        log.info("EquipmentTagAspect.doProccess dbTcpTypeList:{}", dbTcpTypeList);
        if ("tag".equals(equipmentTag.type()) && CollectionUtil.isEmpty(dbTcpTypeList)) {
            Map<String, Object> params = FeedbackInterceptAspect.getMap(proceedingJoinPoint);
            DbTcpType dbTcpType = (DbTcpType)params.get("dbTcpType");
            try {
                List<DbTcpType> dbTcpTypeTagList = this.dbTcpTypeMapper.selectDbTcpTypeTagList(dbTcpType);
                log.info("EquipmentTagAspect.doProccess dbTcpTypeTagList:{}", dbTcpTypeTagList);
                if (CollectionUtil.isNotEmpty(dbTcpTypeTagList))
                    return assemblyData(dbTcpTypeTagList);
            } catch (Exception e) {
                log.error("EquipmentTagAspect.doProccess error:{}", e);
            }
        }
        return dbTcpTypeList;
    }

    private List<DbTcpType> assemblyData(List<DbTcpType> dbTcpTypeList) {
        dbTcpTypeList.forEach(tcpType -> {
            if (StringUtils.isNotEmpty(tcpType.getTemperatureSoil()))
                tcpType.setTemperatureSoil(randomData(tcpType.getTemperatureSoil()));
            if (StringUtils.isNotEmpty(tcpType.getHumiditySoil()))
                tcpType.setHumiditySoil(randomData(tcpType.getHumiditySoil()));
            if (StringUtils.isNotEmpty(tcpType.getLight()))
                tcpType.setLight(randomData(tcpType.getLight()));
            if (StringUtils.isNotEmpty(tcpType.getCo2()))
                tcpType.setCo2(randomData(tcpType.getCo2()));
            if (StringUtils.isNotEmpty(tcpType.getTemperatureAir()))
                tcpType.setTemperatureAir(randomData(tcpType.getTemperatureAir()));
            if (StringUtils.isNotEmpty(tcpType.getHumidityAir()))
                tcpType.setHumidityAir(randomData(tcpType.getHumidityAir()));
            if (StringUtils.isNotEmpty(tcpType.getConductivity()))
                tcpType.setConductivity(randomData(tcpType.getConductivity()));
            if (StringUtils.isNotEmpty(tcpType.getPh()))
                tcpType.setPh(randomData(tcpType.getPh()));
            if (StringUtils.isNotEmpty(tcpType.getNitrogen()))
                tcpType.setNitrogen(randomData(tcpType.getNitrogen()));
            if (StringUtils.isNotEmpty(tcpType.getPhosphorus()))
                tcpType.setPhosphorus(randomData(tcpType.getPhosphorus()));
            if (StringUtils.isNotEmpty(tcpType.getPotassium()))
                tcpType.setPotassium(randomData(tcpType.getPotassium()));
        });
        return dbTcpTypeList;
    }

    private final DecimalFormat dataFormat = new DecimalFormat("#.0");

    private static final float FLOAT_VOLATILITY = 0.5F;

    private static final int INT_VOLATILITY = 3;

    private String randomData(String dataVale) {
        Random random = new Random();
        if (dataVale.contains(".")) {
            Float aFloat = Float.valueOf(dataVale);
            float f1 = aFloat - FLOAT_VOLATILITY;
            float f2 = aFloat + FLOAT_VOLATILITY;
            if (f1 <= 0.0F)
                f1 = aFloat;
            float f3 = f1 + (f2 - f1) * (new Random()).nextFloat();
            return (f3 < 1.0F) ? String.valueOf(f3).substring(0, 3) : String.valueOf(this.dataFormat.format(f3));
        }
        Integer dataInt = Integer.valueOf(dataVale);
        int minValue = dataInt - INT_VOLATILITY;
        int maxValue = dataInt + INT_VOLATILITY;
        if (minValue <= 0)
            minValue = dataInt;
        int finalValue = random.nextInt(maxValue) % (maxValue - minValue + 1) + minValue;
        return String.valueOf(finalValue);
    }
}
