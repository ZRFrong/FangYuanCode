package com.ruoyi.fangyuanapi.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.SensorDeviceDto;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.controller.OperateControllerAppNew;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentAdminMapper;
import com.ruoyi.fangyuanapi.mapper.DbLandMapper;
import com.ruoyi.fangyuanapi.service.DaPengService;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.fangyuanapi.service.IDbMonitorService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.domain.monitor.DbMonitor;
import com.ruoyi.system.feign.DbTcpClientService;
import com.ruoyi.system.feign.RemoteTcpService;
import com.ruoyi.system.util.DbTcpUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author zheng
 * @Date 2021/6/25 16:26
 * @Version 1.0
 */
@Log4j2
@Service
public class DaPengServiceImpl implements DaPengService {

    @Autowired
    private DbLandMapper dbLandMapper;
    @Autowired
    private DbEquipmentAdminMapper dbEquipmentAdminMapper;
    @Autowired
    private OperateControllerAppNew operateControllerAppNew;

    @Autowired
    private IDbLandService dbLandService;
    @Autowired
    private IDbEquipmentService equipmentService;

    @Autowired
    private RemoteTcpService remoteTcpService;
    @Autowired
    private DbTcpClientService dbTcpClientService;

    @Autowired
    private IDbMonitorService dbMonitorService;

    /**
     * 获取所有大棚详细信息
     * @return 大棚列表
     */
    @Override
    public R listData(String landIds) {
        log.info("DaPengServiceImpl.listData landIds:{}",landIds);
        if(StringUtils.isEmpty(landIds)){
            return R.error("大棚列表为空");
        }
        List<DbLand> dbLands = dbLandMapper.selectDbLandByIds(landIds);
        List<Map<String, Object>> result = new ArrayList<>();
        for (DbLand dbLand : dbLands) {
            // 操作集
            List<Map<String, Object>> maps = dbLandService.selectLandOperationByLandId(dbLand.getLandId());
            if (CollectionUtil.isEmpty(maps)) {
                continue;
            }
            List<SensorDeviceDto> sensorResult = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                result.add(map);
                map.put("landName",dbLand.getNickName());
                map.put("landId",dbLand.getLandId());
                // 封装视频监控数据
                monitorVideo(map,dbLand.getLandId());
                // 设备大棚详细数据
                getItemInfo(dbLand.getLandId(),map);
                Long equipmentId = (Long)map.get("equipmentId");
                String heartbeatText = (String) map.get("heartbeatText");
                Integer equipmentNo = (Integer)map.get("equipmentNo");
                // 获取设备状态
                DbEquipment e = equipmentService.selectDbEquipmentById(equipmentId);
                map.put("isOnline",e.getIsOnline());
                map.put("isFault",e.getIsFault());

                // 获取传感器数据
                DbTcpType dbTcpType = new DbTcpType();
                dbTcpType.setHeartName(heartbeatText);
                List<DbTcpType> list = remoteTcpService.list(dbTcpType);
                if (CollectionUtil.isNotEmpty(list)) {
                    DbTcpType tcpType = list.get(0);
                    /** 此处取多个传感器的温度值，要求采用平均值并不合适  哪个传感器有温度就显示那个，如果有两个或者更多只显示后者 */
                    sensorResult = DbTcpUtils.getSensorResultForDaTong(tcpType);
                }else {
                    sensorResult = DbTcpUtils.getSensorResultForDaTong(new DbTcpType());
                }
                map.put("sensor",sensorResult);
            }
        }
        return R.data(result);
    }

    /**
     * 绑定视频监控数据
     * @param data 大棚数据
     * @param landId 大棚ID
     */
    private void monitorVideo(Map<String, Object> data,Long landId){
        List<DbMonitor> dbMonitors = new ArrayList<>(2);
        try{
            dbMonitors = dbMonitorService.selectVideoChannel(landId);
        }catch (Exception e){
            log.error("DaPengServiceImpl.monitorVideo异常 :{}",e);
        }
        data.put("monitorVideo",dbMonitors);
    }

    /**
     * 设置大棚详细信息
     * @param landId 大棚ID
     * @param landData 基础数据
     */
    private void getItemInfo(Long landId,Map<String, Object> landData){
        List<String> strings = ITEM_INFO_MAP.get(landId);
        if(CollectionUtil.isNotEmpty(strings) && strings.size() > 4){
            landData.put("category",strings.get(0));
            landData.put("plantTime",strings.get(1));
            landData.put("area",strings.get(2));
            landData.put("harvestTime",strings.get(3));
            landData.put("output",strings.get(4));
            String []viewPictures = null;
            if(landId == 4195){
                // 西瓜
                viewPictures = new String[]{"https://cdn.fangyuancun.cn/fangyuan/20210628/0ba9b73c46214bde86cfdaf411a85ccb.png"
                        ,"https://cdn.fangyuancun.cn/fangyuan/20210628/90022bd57e6742b1a3bb7cf60a06c545.png"
                        ,"https://cdn.fangyuancun.cn/fangyuan/20210628/6db6b5c9e9ca4f66a6654c29cf7ccacf.png"
                        ,"https://cdn.fangyuancun.cn/fangyuan/20210628/d3121a5329ff431580c8eafb131772a0.png"
                        ,"https://cdn.fangyuancun.cn/fangyuan/20210628/20f5b6d1d50f4ef8bb13f993745c4bb9.png"
                };
            }
            else if(landId == 4197){
                // 南瓜
                viewPictures = new String[]{"https://cdn.fangyuancun.cn/fangyuan/20210628/2cb84829b3264d9886c364e0017cea35.png"
                        ,"https://cdn.fangyuancun.cn/fangyuan/20210628/8550cdb20fc842a08c8739599c14dd1a.png"
                        ,"https://cdn.fangyuancun.cn/fangyuan/20210628/685caa779e8c47b182fdfe07b9a5bf0e.png"
                        ,"https://cdn.fangyuancun.cn/fangyuan/20210628/242861b8af29423f91de8894dd9c8516.png"
                        ,"https://cdn.fangyuancun.cn/fangyuan/20210628/5ecedb32467149ad922032d0877b9dde.png"
                };
            }
            landData.put("viewPictures",viewPictures);
        }
    }

    /**
     * 初始化大同大棚数据
     */
    private final static Map<Long,List<String>> ITEM_INFO_MAP = new HashMap<>();
    static {
        List<String> itemInfo_4195 = new ArrayList<>();
        itemInfo_4195.add("西瓜");
        itemInfo_4195.add("5月25日");
        itemInfo_4195.add("0.7亩");
        itemInfo_4195.add("7月25日");
        itemInfo_4195.add("5000斤");
        ITEM_INFO_MAP.put(4195L,itemInfo_4195);
        List<String> itemInfo_4197 = new ArrayList<>();
        itemInfo_4197.add("贝贝南瓜");
        itemInfo_4197.add("3月20日");
        itemInfo_4197.add("2.5亩");
        itemInfo_4197.add("7月10日");
        itemInfo_4197.add("8000斤");
        ITEM_INFO_MAP.put(4197L,itemInfo_4197);
    }
}
