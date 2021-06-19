package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.service.ArCenterService;
import com.ruoyi.fangyuanapi.service.IDbCropInfoService;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.DbCropInfo;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.feign.RemoteTcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.controller.ArCenter.java
 * @Description Ar
 * @createTime 2021年06月04日 15:17:00
 */
@RestController
@RequestMapping("arCenter")
public class ArCenterController {

    @Autowired
    private ArCenterService arCenterService;

    @Autowired
    private IDbCropInfoService dbCropInfoService;

    @Autowired
    private IDbLandService dbLandService;

    @Autowired
    private IDbEquipmentService equipmentService;

    @Autowired
    private RemoteTcpService remoteTcpService;

    /**
     * 根据大棚id获取传感器数据以及功能项及监控
     * @since: 1.0.0
     * @param id
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/6/4 16:15
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("getLandData/{id}")
    public R getLandData(@PathVariable("id") Long id){
       return arCenterService.getLandData(id);
    }

    /**
     * 获取作物信息
     * @since: 2.0.0
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/6/8 13:24
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("getCropInfo")
    public R getCropInfo(){
        List<Map<String,Object>> dbCropInfo =dbCropInfoService.selectList();
        return R.data(dbCropInfo);
    }

    /**
     * @Author BugKing
     * @Description
     * @Date 12:06 2021/6/19
     * @param landId 大棚id
     * @param strips 数据条数
     * @param curveType 曲线类型
     * @return com.ruoyi.common.core.domain.R
     **/
    @GetMapping("getStateCurveData/{landId}/{strips}/{curveType}")
    public R getStateCurveData(@PathVariable("landId") Long landId,@PathVariable("strips") Integer strips,@PathVariable("curveType") Integer curveType){
        DbLand land = dbLandService.selectDbLandById(landId);
        if (StringUtils.isEmpty(land.getEquipmentIds())){
            return R.ok();
        }
        String[] split = land.getEquipmentIds().split(",");
        if (split.length >2 ){
            return R.ok();
        }
        DbEquipment equipment = equipmentService.selectDbEquipmentById(Long.valueOf(split[0]));
        return  remoteTcpService.getStateCurveData(equipment.getHeartbeatText()+"_"+equipment.getEquipmentNoString(),strips,curveType);
    }

}
