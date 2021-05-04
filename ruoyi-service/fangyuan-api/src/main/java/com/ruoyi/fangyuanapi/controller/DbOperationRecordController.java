package com.ruoyi.fangyuanapi.controller;

import cn.hutool.http.HttpStatus;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.dto.LandAdminDto;
import com.ruoyi.fangyuanapi.dto.OperationDto;
import com.ruoyi.fangyuanapi.service.IDbEquipmentAdminService;
import com.ruoyi.fangyuanapi.utils.DbEquipmentAdminUtils;
import com.ruoyi.system.domain.DbEquipmentAdmin;
import com.ruoyi.system.domain.DbOperationRecord;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.service.IDbOperationRecordService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户操作记录 提供者
 *
 * @author zheng
 * @date 2020-10-16
 */
@RestController
@Api("用户操作记录")
@RequestMapping("operationRecord")
public class DbOperationRecordController extends BaseController {

    @Autowired
    private IDbOperationRecordService dbOperationRecordService;

    @Autowired
    private IDbEquipmentAdminService dbEquipmentAdminService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public DbOperationRecord get(@PathVariable("id") Long id) {
        return dbOperationRecordService.selectDbOperationRecordById(id);

    }

    /**
     * 查询用户操作记录列表
     */
    @GetMapping("list")
    public R list(DbOperationRecord dbOperationRecord) {
        startPage();
        return result(dbOperationRecordService.selectDbOperationRecordList(dbOperationRecord));
    }

    /**
     * 查询用户操作记录列表
     */
    @GetMapping("listApp")
    public R listApp(DbOperationRecord dbOperationRecord) {
        startPage();
        return result(dbOperationRecordService.selectDbOperationRecordList(dbOperationRecord));
    }



    /*
     * 当天的用户操作记录  默认显示当天的
     * */

    @GetMapping("listGroupDay")
    @ApiOperation(value = "查询操作记录列表", notes = "pagesize,pageName后边跟参即可，拦截会进行处理")
    public R listGroupDay(@ApiParam(name = "operationTime", value = "date", required = false)  String operationTime,
                          @ApiParam(name = "operationText", value = "string", required = false) String operationText,
                          @ApiParam(name = "pageNum", value = "integer", required = true)Integer pageNum,
                          @ApiParam(name = "pageSize", value = "integer", required = true) Integer pageSize) {
        String header = getRequest().getHeader(Constants.CURRENT_ID);
        pageNum = pageNum == null || pageNum <= 0  ? 0 :(pageNum -1) * pageSize;
        DbOperationRecord dbOperationRecord = new DbOperationRecord();
        if (!StringUtils.isEmpty(operationText)) {
            dbOperationRecord.setOperationText(operationText);
        } else if (!StringUtils.isEmpty(operationTime)) {
            dbOperationRecord.setOperationTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD,operationTime));
        }

//	    日期分组的操作记录
        List<DbOperationRecord> objects = dbOperationRecordService.listGroupDay(operationText,operationTime,pageNum,pageSize,Long.valueOf(header));
        return R.data(objects);
    }


    /**
     * 新增保存用户操作记录
     */
    @PostMapping("save")
    public R addSave(@RequestBody DbOperationRecord dbOperationRecord) {
        return toAjax(dbOperationRecordService.insertDbOperationRecord(dbOperationRecord));
    }

    /**
     * 修改保存用户操作记录
     */
    @PostMapping("update")
    public R editSave(@RequestBody DbOperationRecord dbOperationRecord) {
        return toAjax(dbOperationRecordService.updateDbOperationRecord(dbOperationRecord));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(dbOperationRecordService.deleteDbOperationRecordByIds(ids));
    }

    /**
     * @Author ZHAOXIAOSI
     * @Version 2.0.0
     * @Description 首次获取操作记录接口
     * @Date 1:15 2021/5/3
     * @param currPage
     * @param pageSize
     * @return com.ruoyi.common.core.domain.R
     * @sign 他日若遂凌云志,敢笑黄巢不丈夫!
     **/
    @GetMapping("getOperationList/{currPage}/{pageSize}")
    @ApiOperation(value = "首次进入获取操作记录接口",notes = "首次进入获取操作记录接口" ,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "currPage",value = "页码从一开始",required = true),
            @ApiImplicitParam( name = "pageSize",value = "条数",required = true),
    })
    public R getOperationList(@PathVariable("currPage") Integer currPage,@PathVariable("pageSize") Integer pageSize){
        if ( StringUtils.isEmpty(currPage+"") || StringUtils.isEmpty(pageSize +"") || pageSize > 50 || pageSize <= 0 || currPage <= 0 ){
            return R.error(HttpStatus.HTTP_BAD_REQUEST,"参数异常");
        }
        String user = getRequest().getHeader(Constants.CURRENT_ID);
        List<DbEquipmentAdmin> admins = dbEquipmentAdminService.selectDbEquipmentAdminListByUserId(Long.valueOf(user));
        if (CollectionUtils.isEmpty(admins)){
            return R.ok("您还尚未拥有大棚，请前去添加吧！");
        }
        ArrayList<LandAdminDto> test = DbEquipmentAdminUtils.test(admins, user);
        String userId = null;
        for (LandAdminDto dto : test) {
            if (dto.getIsSuperAdmin() > 0){
                userId = user;
            }
            ArrayList<OperationDto> operationDtos = dbOperationRecordService.selectDbOperationRecordByLandIdAndUserId(dto.getLandId(), userId, currPage, pageSize);
            dto.setOperation(operationDtos);
        }
        return R.data(test);
    }

    /**
     * @Author ZHAOXIAOSI
     * @version 2.0.0
     * @Description
     * @Date 1:16 2021/5/3
     * @param landId
     * @param userId
     * @param currPage
     * @param pageSize
     * @return com.ruoyi.common.core.domain.R
     * @sign 他日若遂凌云志,敢笑黄巢不丈夫!
     **/
    @ApiOperation(value = "非首次获取操作列表接口",notes = "非首次获取操作列表接口" ,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "landId",value = "大棚id",required = true),
            @ApiImplicitParam( name = "userId",value = "管理员id",required = false),
            @ApiImplicitParam( name = "currPage",value = "页码从一开始",required = true),
            @ApiImplicitParam( name = "pageSize",value = "条数",required = true),
    })
    @GetMapping("getOperationByLand/{landId}/{userId}/{currPage}/{pageSize}")
    public R getOperationByLand(@PathVariable("landId") Long landId,@PathVariable("userId")String userId,@PathVariable("currPage")Integer currPage,@PathVariable("pageSize")Integer pageSize){
        String dbUserId = getRequest().getHeader(Constants.CURRENT_ID);
        if (StringUtils.isEmpty(userId) || "null".equals(userId)){
            userId = null;
        }
        if (!dbUserId.equals(userId)){
            DbEquipmentAdmin admin = dbEquipmentAdminService.selectDbEquipmentAdminByUserIdAndLandId(landId, Long.valueOf(dbUserId), null);

            if (admin != null && admin.getIsSuperAdmin() > 0){
                userId = dbUserId;
            }
        }
        return R.data(dbOperationRecordService.selectDbOperationRecordByLandIdAndUserId(landId,userId,currPage,pageSize));
    }
}
