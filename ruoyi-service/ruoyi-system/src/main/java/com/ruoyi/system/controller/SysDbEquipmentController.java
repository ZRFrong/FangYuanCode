package com.ruoyi.system.controller;

import cn.hutool.db.Db;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.DbEquipmentCilent;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.controller.DbEquipmentController.java
 * @createTime 2021年003月22日 11:36:00
 */
@RestController
@RequestMapping("sys")
public class SysDbEquipmentController {

    @Autowired
    private DbEquipmentCilent dbEquipmentCilent;


    @PostMapping("batchEquipment")
    @ApiOperation(value = "批量生成设备及二维码接口",notes = "区间切记不要太大，差值不要超过200",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prefix",value = "心跳前缀",required = true),
            @ApiImplicitParam(name = "suffix",value = "心跳后缀",required = true),
            @ApiImplicitParam(name = "openInterval",value = "开区间",required = true),
            @ApiImplicitParam(name = "closedInterval",value = "闭区间",required = true),
    })
    public R batchEquipment(String prefix,String suffix,String openInterval,String closedInterval){
        return dbEquipmentCilent.batchEquipment(prefix,suffix,openInterval,closedInterval);
    }

}
