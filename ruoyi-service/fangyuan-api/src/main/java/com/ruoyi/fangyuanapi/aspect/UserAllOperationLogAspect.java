package com.ruoyi.fangyuanapi.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.dto.OperateVO;
import com.ruoyi.fangyuanapi.dto.UserLogDto;
import com.ruoyi.fangyuanapi.mapper.DbUserLogMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class UserAllOperationLogAspect {

    @Autowired
    private DbUserLogMapper dbUserLogMapper;

    /**
     * 用户所有操作记录切面
     * @param point 切点
     * @return: 响应结果
     */
    @Around("@annotation(apiOperation)")
    public Object doProccess(ProceedingJoinPoint point, ApiOperation apiOperation) throws Throwable {
        try{
            //参数
            Map<String, Object> param = OperationLogAspect.getMap(point);
            log.info("UserAllOperationLogAspect.doProccess param:{}",param);
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String userId = request.getHeader(Constants.CURRENT_ID);
            String notes = apiOperation.notes();
            String value = apiOperation.value();
            if("App2.0单操作接口".equals(notes)){
                OperateVO operateVO = (OperateVO)param.get("operateVO");
                Object operationType = operateVO.getOperationType();
                Object handleName = operateVO.getHandleName();
                notes += "--" + handleName + "--" + operationType;
            }
            else if("批量操作接口".equals(notes)){
                List<OperateVO> operateVOS = (List<OperateVO>) param.get("operateVOS");
                if(CollectionUtil.isNotEmpty(operateVOS)){
                    StringBuilder notesStrs = new StringBuilder();
                    operateVOS.forEach(v -> {
                        notesStrs.append(v.getHandleName())
                                .append("--")
                                .append(v.getOperationType())
                                .append( ", ");
                    });
                    notes += "--" + notesStrs.toString();
                }
            }
            else if("2.0操作自动通风是否开启自动".equals(notes)){
                Object switchStatus = param.get("switchStatus");
                notes += "--" + switchStatus ;
            }
            else if("2.0操作自动通风开关温度".equals(notes)){
                Object startTemperature = param.get("startTemperature");
                Object stopTemperature = param.get("stopTemperature");
                notes += "--" + startTemperature + "--" + stopTemperature ;
            }
            log.info("UserAllOperationLogAspect.doProccess userId:{}, notes:{}, value:{}",userId,notes,value);
            dbUserLogMapper.insertDbUserLog(new UserLogDto().setUserId(StringUtils.isNotEmpty(userId)? Long.valueOf(userId): null)
                    .setActive(notes));
        }catch (Exception e){
            log.error( "UserAllOperationLogAspect.doProccess exception:{}",e);
        }catch (Error e){
            log.error( "UserAllOperationLogAspect.doProccess error:{}",e);
        }

        return point.proceed();
    }

}
