package com.ruoyi.system.controller;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
*web操作  后台调试
* */
@RestController
@RequestMapping("operationWeb")
public class OperationWeb {

    @Autowired
    private RemoteTcpService remoteTcpService;

    /*
    * 在线列表查询
    * */
    @GetMapping("getList")
    public R get()
    {
        System.out.println("进来了");
        return R.data(remoteTcpService.tcpClients());

    }






    /*
    * 操作
    * */
    @PostMapping("operationOnly")
    public R get( @ApiParam(name = "DbTcpClient", value = "传入json格式", required = true) DbOperationVo dbOperationVo)
    {
        return R.data(remoteTcpService.operation(dbOperationVo));

    }


}
