package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuanapi.service.DaPengService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 方圆村大棚智控平台接口控制层
 * @Author zheng
 * @Date 2021/6/25 13:44
 * @Version 1.0
 */
@RestController
@RequestMapping("dapeng")
@Log4j2
public class DaPengController {

    @Autowired
    private DaPengService daPengService;

    @GetMapping("listData")
    public R listData(){
        return daPengService.listData();
    }


}
