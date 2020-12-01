package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.fangyuanapi.service.IDbLandService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 土地 提供者
 *
 * @author zheng
 * @date 2020-09-24
 */
@RestController
@Api("land")
@RequestMapping("land")
public class DbLandController extends BaseController {

    @Autowired
    private IDbLandService dbLandService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{landId}")
    public DbLand get( @PathVariable("landId") Long landId) {

        return dbLandService.selectDbLandById(landId);

    }

    /**
     * 查询土地列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询土地列表", notes = "土地列表")
    public R list(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        startPage();
        return result(dbLandService.selectDbLandList(dbLand));
    }

    /**
     * 查询土地列表
     */
    @GetMapping("listApp")
    @ApiOperation(value = "查询土地列表app", notes = "土地列表")
    public R listApp(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        return R.data(dbLands);
    }


    /**
     * 新增保存土地
     */
    @PostMapping("save")
    @ApiOperation(value = "新增土地/地块", notes = "土地/地块id")
    public R addSave(@RequestBody DbLand dbLand, HttpServletRequest request)
    {
        String userId = request.getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setCreateTime(new Date());
        int i = dbLandService.insertDbLand(dbLand);
        return R.data(dbLand.getLandId());
    }

    /**
     * 修改保存土地
     */
    @PostMapping("update")
    public R editSave( @RequestBody DbLand dbLand, HttpServletRequest request) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        return toAjax(dbLandService.updateDbLand(dbLand));
    }

    /**
     * 删除${tableComment}
     */
    @GetMapping("remove")
    public R remove(  String landId) {
        return toAjax(dbLandService.deleteDbLandByIds(landId));
    }

    /*
    * 用户关联地块返回
    * */
    @GetMapping("listPlot")
    public R listPlot( DbLand dbLand) {
//        获取当前用户id
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setSiteId(0L);
        startPage();
        return result(dbLandService.selectDbLandList(dbLand));
    }




}
