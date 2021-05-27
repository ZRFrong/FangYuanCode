package com.ruoyi.fangyuanapi.aspect;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuanapi.dto.OperateVO;
import com.ruoyi.fangyuanapi.service.IDbEquipmentComponentService;
import com.ruoyi.fangyuanapi.service.IDbOperationRecordService;
import com.ruoyi.system.domain.DbEquipmentComponent;
import com.ruoyi.system.domain.DbOperationRecord;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.aspect.OperationAspect.java
 * @Description 操作切面 做记录
 * @createTime 2021年05月02日 22:30:00
 */
@Aspect
@Component
@Slf4j
public class OperationAspect {

    private IDbEquipmentComponentService dbEquipmentComponentService = SpringUtils.getBean(IDbEquipmentComponentService.class);

    private IDbOperationRecordService dbOperationRecordService = SpringUtils.getBean(IDbOperationRecordService.class);



    @Autowired
    private OperationLogUtils operationLogUtils;
    /**
     * 操作记录切面
     * @since: 2.0.0
     * @param point
     * @param operation
     * @return: java.lang.Object
     * @author: ZHAOXIAOSI
     * @date: 2021/5/6 21:59
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @Around("@annotation(operation)")
    public Object processAuthority(ProceedingJoinPoint point, Operation operation) throws Throwable {
        //参数
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = request.getHeader(Constants.CURRENT_ID);
        ArrayList<DbOperationRecord> records = new ArrayList<>();
        R r = (R) point.proceed();
        if (r.get("code").equals(HttpStatus.BAD_REQUEST.value())){
            return r;
        }
        if (operation.OperationLogType()){
            //批量
            List<OperateVO> list = (List<OperateVO>) OperationLogAspect.getMap(point).get("operateVOS");
            list.forEach( e -> records.add(getDbOperationRecord(e, userId, "200".equals(r.get("code") + "") ? 0 : 1)));
        }else {
            Map<String, Object> map = OperationLogAspect.getMap(point);
            OperateVO operateVO = (OperateVO) map.get("operateVO");
            records.add(getDbOperationRecord(operateVO, userId, "200".equals(r.get("code") + "") ? 0 : 1));
        }
        dbOperationRecordService.batchInsert(records);
        return r;
    }


    /**
     * 转换操作记录对象
     * @since: 2.0.0
     * @param operateVO
     * @param userId
     * @param isComplete
     * @return: com.ruoyi.system.domain.DbOperationRecord
     * @author: ZHAOXIAOSI
     * @date: 2021/5/6 21:59
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    private DbOperationRecord getDbOperationRecord(OperateVO operateVO,String userId,Integer isComplete  ){
        DbEquipmentComponent component = dbEquipmentComponentService.selectDbEquipmentComponentById(operateVO.getOperationId());
        return DbOperationRecord.builder()
                .dbUserId(Long.valueOf(userId))
                .landId(operateVO.getLandId())
                .isComplete(isComplete)
                .operationTime(new Date())
                .operationText(component.getEquipmentName()+operationLogUtils.toOperationText(operateVO.getOperationType(),operateVO.getHandleName()))
                .operationObjectType(Integer.valueOf(operateVO.getOperationType()))
                .landId(operateVO.getLandId())
                .build();
    }
}
