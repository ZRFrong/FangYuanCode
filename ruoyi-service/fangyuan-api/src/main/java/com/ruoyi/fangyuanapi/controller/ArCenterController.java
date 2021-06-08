package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuanapi.service.ArCenterService;
import com.ruoyi.fangyuanapi.service.IDbCropInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return R.data(dbCropInfoService.selectDbCropInfoList(null));
    }

}
