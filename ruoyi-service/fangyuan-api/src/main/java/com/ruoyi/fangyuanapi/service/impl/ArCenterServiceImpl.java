package com.ruoyi.fangyuanapi.service.impl;

import cn.hutool.log.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.SensorDeviceDto;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.conf.SensorIconConf;
import com.ruoyi.fangyuanapi.conf.SensorImgConf;
import com.ruoyi.fangyuanapi.dto.ArDataDto;
import com.ruoyi.fangyuanapi.dto.ArOperateDto;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentComponentMapper;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentMapper;
import com.ruoyi.fangyuanapi.mapper.DbLandMapper;
import com.ruoyi.fangyuanapi.service.ArCenterService;
import com.ruoyi.fangyuanapi.service.IDbMonitorService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbEquipmentComponent;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.domain.monitor.DbMonitor;
import com.ruoyi.system.feign.RemoteTcpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.service.impl.ArCenterServiceImpl.java
 * @Description
 * @createTime 2021年06月04日 16:14:00
 */
@Service
@Slf4j
public class ArCenterServiceImpl implements ArCenterService {

    @Autowired
    private DbLandMapper dbLandMapper;

    @Autowired
    private DbEquipmentMapper dbEquipmentMapper;

    @Autowired
    private DbEquipmentComponentMapper dbEquipmentComponentMapper;

    @Autowired
    private RemoteTcpService tcpService;

    @Autowired(required = false)
    private SensorImgConf sensorImgConf;

    @Autowired
    private SensorIconConf sensorIconConf;

    @Autowired
    private IDbMonitorService dbMonitorService;

    @Override
    public R getLandData(Long id) {
        DbLand land = dbLandMapper.selectDbLandById(id);
        String equipmentIds = land.getEquipmentIds();
        if (StringUtils.isEmpty(equipmentIds)){
            return R.ok();
        }
        String[] ids = equipmentIds.split(",");
        ArDataDto arDataDto = null;
        for (String s : ids) {
            DbEquipment equipment = dbEquipmentMapper.selectDbEquipmentById(Long.valueOf(s));
            List<DbTcpType> list = tcpService.list(DbTcpType.builder()
                    .heartName(equipment.getHeartbeatText() + "_" + equipment.getEquipmentNoString())
                    .build());
            List<DbEquipmentComponent> componentList =dbEquipmentComponentMapper.selectDbEquipmentComponentListByHeartbeat(equipment.getHeartbeatText());
            ArrayList<ArOperateDto> operateDtos = new ArrayList<>();
            //功能名称 图
            for (DbEquipmentComponent component : componentList) {
                JSONObject spList = JSON.parseObject(component.getSpList());
                operateDtos.add(ArOperateDto.builder()
                        .icon(getIcon(spList.get("checkCode")+""))
                        .name(component.getEquipmentName())
                        .build());
            }
            List<DbMonitor> monitors = null;
            try {
                monitors = dbMonitorService.selectVideoChannel(id);
            }catch (Exception e){
                log.error("获取指定设备通道的直播/回放视频地址异常");
            }
            arDataDto = ArDataDto.builder()
                    .operateDto(operateDtos)
                    .landName(land.getNickName())
                    .build();
            if (monitors != null && monitors.size() > 0){
                arDataDto.setVideoUrl(monitors.get(0).getDeviceVideoUrls().get("flvUrl").toString());
            }
            if (list != null && list.size() > 0){
                arDataDto.setSensor(getSensor(list.get(0)));
            }
            break;
        }

        return R.data(arDataDto);
    }

    /**
     * SensorDeviceDto.builder()
     *                 .unit("°C")
     *                 .value(dbTcpType.getTemperatureAir())
     *                 .deviceName("空气温度")
     *                 .icon("https://cdn.fangyuancun.cn/fangyuan/20210604/3cf554c2b27b4c1c92c77ea0b75d709b.png")
     *                 .build();
     */
    private List<Map<String,Object>> getSensor(DbTcpType  dbTcpType){
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        list.add(getMap("°C",dbTcpType.getTemperatureAir(),"空气温度",sensorIconConf.getTemperatureAirImg(),sensorImgConf.getTemperatureAirImg(),1));
        list.add(getMap("%",dbTcpType.getHumidityAir(),"空气湿度",sensorIconConf.getHumidityAirImg(),sensorImgConf.getHumidityAirImg(),2));
        list.add(getMap("°C",dbTcpType.getTemperatureSoil(),"土壤温度",sensorIconConf.getTemperatureSoilImg(),sensorImgConf.getTemperatureSoilImg(),3));
        list.add(getMap("%",dbTcpType.getHumiditySoil(),"土壤湿度",sensorIconConf.getHumiditySoilImg(),sensorImgConf.getHumiditySoilImg(),4));
        list.add(getMap("mg/L",dbTcpType.getNitrogen(),"氮",sensorIconConf.getNitrogenImg(),sensorImgConf.getNitrogenImg(),5));
        list.add(getMap("mg/L",dbTcpType.getPhosphorus(),"磷",sensorIconConf.getPhosphorusImg(),sensorImgConf.getPhosphorusImg(),6));
        list.add(getMap("mg/L",dbTcpType.getPotassium(),"钾",sensorIconConf.getPotassiumImg(),sensorImgConf.getPotassiumImg(),7));
        list.add(getMap("us/cm",dbTcpType.getConductivity(),"电导率",sensorIconConf.getConductivityImg(),sensorImgConf.getConductivityImg(),8));
        list.add(getMap("Lux",dbTcpType.getLight(),"光照强度",sensorIconConf.getLightImg(),sensorImgConf.getLightImg(),9));
        list.add(getMap("",dbTcpType.getPh(),"土壤PH",sensorIconConf.getPhImg(),sensorImgConf.getPhImg(),10));
        list.add(getMap("ppm",dbTcpType.getCo2(),"二氧化碳",sensorIconConf.getCo2Img(),sensorImgConf.getCo2Img(),11));
        return list;
    }

    private Map<String,Object> getMap(String unit,String value,String deviceName,String icon,String img,Integer type){
        HashMap<String, Object> map = new HashMap<>();
        map.put("sensor",SensorDeviceDto.builder()
                .unit(unit)
                .value(value)
                .deviceName(deviceName)
                .icon(icon)
                .build());
        map.put("img",img);
        map.put("type",type);
        return map;
    }

    private String getIcon(String code){
        String icon = null;
        switch (code){
            case "1":
                icon = "https://cdn.fangyuancun.cn/fangyuan/20210604/cff58d55d26742f3a7110a2425636b0e.png";
                break;
            case "2":
                icon = "https://cdn.fangyuancun.cn/fangyuan/20210604/b5fabf686a4944b3af11e613344f21c7.png";
                break;
            case "3":
                icon = "https://cdn.fangyuancun.cn/fangyuan/20210604/a4f93590621148b3973193dcd75df81f.png";
                break;
            case "4":
                icon = "https://cdn.fangyuancun.cn/fangyuan/20210604/6feb933ac27b41b68221d1f9b3a5b00c.png";
                break;
            default:
        }
        return icon;
    }
}
