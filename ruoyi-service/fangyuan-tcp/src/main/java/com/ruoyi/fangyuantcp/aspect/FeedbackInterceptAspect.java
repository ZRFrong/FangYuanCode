package com.ruoyi.fangyuantcp.aspect;

import com.alibaba.fastjson.JSON;
import com.github.dadiyang.equator.Equator;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.util.RedisMqUtils;
import com.ruoyi.common.redis.wsmsg.MsgType;
import com.ruoyi.common.redis.wsmsg.PushEffectType;
import com.ruoyi.common.redis.wsmsg.SocketMsg;
import com.ruoyi.common.redis.wsmsg.WSTypeEnum;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.RemoteApiService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author zheng
 * @Date 2021/3/24 9:32
 * @Version 1.0
 */

@Aspect
@Component
public class FeedbackInterceptAspect {

    private RedisMqUtils redisMqUtils = SpringUtils.getBean(RedisMqUtils.class);
    private IDbEquipmentService equipmentService = SpringUtils.getBean(IDbEquipmentService.class);
    private RemoteApiService remoteApiService = SpringUtils.getBean(RemoteApiService.class);


    /**
     * @param point
     * @param feedbackIntercept
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(feedbackIntercept)")
    public Object processAuthority(ProceedingJoinPoint point, FeedbackIntercept feedbackIntercept) throws Throwable {

        //        处理参数名称以及参数值  成map
        Map<String, Object> maps = getMap(point);

        if (feedbackIntercept.ChangesState().equals("dbEquipment")) {
            DbEquipment dbEquipment = (DbEquipment) maps.get("dbEquipment");
            //调用目标方法
            DbEquipment dbEquipment1 = (DbEquipment) point.proceed();
            Equator equator = new GetterBaseEquator();
            if (!equator.isEquals(dbEquipment, dbEquipment1)) {
                //调用发送
                //connectionResponse(dbEquipment1.getHeartbeatText());
                return dbEquipment1;
            }
        } else if (feedbackIntercept.ChangesState().equals("dbTcpType") ){
            DbTcpType dbTcpType = (DbTcpType) maps.get("dbTcpType");
            //调用目标方法

            DbTcpType dbTcpType1 = (DbTcpType) point.proceed();
            Equator equator = new GetterBaseEquator();
            List<FieldInfo> diffFields  = equator.getDiffFields(dbTcpType, dbTcpType1);
            if (diffFields.size()>1){
                //条用发送
                //connectionResponse(dbTcpType1.getHeartName());
                return dbTcpType1;
            }
        }
        return null;
    }


    /*
        预警
     * websocket长连接响应
     * */
    private void connectionResponse(String ConnectionIdentifier) {

        SocketMsg socketMsg = new SocketMsg();


        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(ConnectionIdentifier.split("_")[0]);
        List<DbEquipment> dbEquipments = equipmentService.selectDbEquipmentList(dbEquipment);
        Long equipmentId = dbEquipments.get(0).getEquipmentId();
        R r = remoteApiService.deviceBelongs(equipmentId);
        List<DbLand> data = JSON.parseArray((String) r.get("data"), DbLand.class);

        socketMsg.setMsgType(MsgType.INFO.name());
        socketMsg.setMsg(PushEffectType.REFRESHPAGE.name());
//       页面刷新 预警
        if (data.size() > 1) {
            Map<Long, List<DbLand>> mps = data.stream().filter(land -> land.getLandId() != null).collect(Collectors.groupingBy(DbLand::getDbUserId));
            mps.keySet().forEach(ite -> {
                socketMsg.setUserId(ite.toString());
                redisMqUtils.publishTopic(socketMsg, WSTypeEnum.SYSTEM);
            });
        } else if (data.size() == 1) {
            socketMsg.setUserId(data.get(0).getDbUserId().toString());
            redisMqUtils.publishTopic(socketMsg, WSTypeEnum.SYSTEM);
        }

    }


    protected static Map<String, Object> getMap(ProceedingJoinPoint point) {
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
