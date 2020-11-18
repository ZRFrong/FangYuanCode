package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.system.domain.DbOperationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;

import java.util.List;


/**
 * tcp在线设备 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("client")
@RequestMapping("client")
public class DbTcpClientController extends BaseController {

    @Autowired
    private IDbTcpClientService dbTcpClientService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{tcpClientId}")
    @ApiOperation(value = "根据id查询", notes = "查询${tableComment}")
    public DbTcpClient get(@ApiParam(name = "id", value = "long", required = true) @PathVariable("tcpClientId") Long tcpClientId) {
        return dbTcpClientService.selectDbTcpClientById(tcpClientId);

    }

    /**
     * 查询tcp在线设备列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询tcp在线设备列表", notes = "tcp在线设备列表")
    public R list(@ApiParam(name = "DbTcpClient", value = "传入json格式", required = true) DbTcpClient dbTcpClient) {
        startPage();
        return result(dbTcpClientService.selectDbTcpClientList(dbTcpClient));
    }


    /**
     * 新增保存tcp在线设备
     */
    @PostMapping("save")
    @ApiOperation(value = "新增保存tcp在线设备", notes = "新增保存tcp在线设备")
    public R addSave(@ApiParam(name = "DbTcpClient", value = "传入json格式", required = true) @RequestBody DbTcpClient dbTcpClient) {
        return toAjax(dbTcpClientService.insertDbTcpClient(dbTcpClient));
    }

    /**
     * 修改保存tcp在线设备
     */
    @PostMapping("update")
    @ApiOperation(value = "修改保存tcp在线设备", notes = "修改保存tcp在线设备")
    public R editSave(@ApiParam(name = "DbTcpClient", value = "传入json格式", required = true) @RequestBody DbTcpClient dbTcpClient) {
        return toAjax(dbTcpClientService.updateDbTcpClient(dbTcpClient));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    @ApiOperation(value = "删除tcp在线设备", notes = "删除tcp在线设备")
    public R remove(@ApiParam(name = "删除的id", value = "已逗号分隔的id集", required = true) String ids) {
        return toAjax(dbTcpClientService.deleteDbTcpClientByIds(ids));
    }


    /*
     * 操作设备
     * */
    @PostMapping("operation")
    @ApiOperation(value = "操作设备", notes = "tcp在线设备列表")
    public R operation(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true)@RequestBody  DbOperationVo dbOperationVo) {
        int operation = dbTcpClientService.operation(dbOperationVo);
        return toAjax(operation);
    }

    /*
    * 批量操作设备
    * */
    @PostMapping("operationList")
    @ApiOperation(value = "操作设备", notes = "tcp在线设备列表")
    public R operationList(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true)@RequestBody  List<DbOperationVo> dbOperationVo) {

        int operation = dbTcpClientService.operationList(dbOperationVo);

        return toAjax(operation);
    }

    /*
     * 手动 自动查询      手动自动状态更新
     *    01 03 00 00 00 01 84 0A
     * */
    @GetMapping("sinceOrHand")
    @ApiOperation(value = "时间毫秒", notes = "手动自动查询")
    public R sinceOrHand() {
        int operation = dbTcpClientService.sinceOrHand();

        return toAjax(operation);
    }




}