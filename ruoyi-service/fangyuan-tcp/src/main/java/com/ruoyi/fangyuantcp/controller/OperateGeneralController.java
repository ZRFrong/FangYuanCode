package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.service.OperateGeneralService;
import com.ruoyi.system.domain.DbOperationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/*
*通用操作
* */
@RestController
@Api("operateGeneral")
@RequestMapping("operateGeneral")
public class OperateGeneralController  extends BaseController {


    @Autowired
    private OperateGeneralService operateGeneralService;

    /*
     * 操作设备
     * */
    @PostMapping("operation")
    @ApiOperation(value = "操作设备", notes = "操作设备")
    public R operation(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true) @RequestBody DbOperationVo dbOperationVo) {
        int operation = operateGeneralService.operation(dbOperationVo);
        return toAjax(operation);
    }

    /*
     * 批量操作设备
     * */
    @PostMapping("operationList")
    @ApiOperation(value = "批量操作设备", notes = "批量操作设备")
    public R operationList(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true) @RequestBody List<DbOperationVo> dbOperationVo) throws ExecutionException, InterruptedException {
        return operateGeneralService.operationList(dbOperationVo);
    }
}
