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
    @ApiOperation(value = "根据id查询", notes = "查询${tableComment}")
    public DbLand get(@ApiParam(name = "id", value = "long", required = true) @PathVariable("landId") Long landId) {

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
     * 新增保存土地
     */
    @PostMapping("save")
    @ApiOperation(value = "新增保存土地", notes = "新增保存土地")
    public R addSave(@ApiParam(name = "DbLand", value = "传入json格式", required = true)@RequestBody DbLand dbLand, HttpServletRequest request)

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
    @ApiOperation(value = "修改保存土地", notes = "修改保存土地")
    public R editSave(@ApiParam(name = "DbLand", value = "传入json格式", required = true) @RequestBody DbLand dbLand, HttpServletRequest request) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        return toAjax(dbLandService.updateDbLand(dbLand));
    }

    /**
     * 删除${tableComment}
     */
    @GetMapping("remove")
    @ApiOperation(value = "删除土地", notes = "删除土地")
    public R remove( @ApiParam(name = "删除的id子串", value = "已逗号分隔的id集", required = true) String landId) {
        return toAjax(dbLandService.deleteDbLandByIds(landId));
    }

    /*
    * 用户关联地块返回
    * */
    @GetMapping("listPlot")
    @ApiOperation(value = "查询地块列表", notes = "地块列表")
    public R listPlot(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
//        获取当前用户id
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setSiteId(0L);
        startPage();
        return result(dbLandService.selectDbLandList(dbLand));
    }




}
