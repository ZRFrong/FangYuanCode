package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.service.IDbEquipmentAdminService;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.DbAbnormalInfo;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbEquipmentAdmin;
import com.ruoyi.system.domain.DbLand;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.service.IDbAbnormalInfoService;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 报警信息 提供者
 *
 * @author zheng
 * @date 2020-12-02
 */
@RestController
@Api("abnormalInfo")
@RequestMapping("abnormalInfo")
public class DbAbnormalInfoController extends BaseController {

    @Autowired
    private IDbAbnormalInfoService dbAbnormalInfoService;

    @Autowired
    private IDbLandService dbLandService;


    @Autowired
    private IDbEquipmentService equipmentService;


    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public DbAbnormalInfo get(@ApiParam(name = "id", value = "long", required = true) @PathVariable("id") Long id) {
        return dbAbnormalInfoService.selectDbAbnormalInfoById(id);

    }

    /**
     * 查询报警信息列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询报警信息列表", notes = "报警信息列表")
    public R list(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) DbAbnormalInfo dbAbnormalInfo) {
        startPage();
        return result(dbAbnormalInfoService.selectDbAbnormalInfoList(dbAbnormalInfo));
    }


    /**
     * 新增保存报警信息
     */
    @PostMapping("save")
    @ApiOperation(value = "新增保存报警信息", notes = "新增保存报警信息")
    public R addSave(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) @RequestBody DbAbnormalInfo dbAbnormalInfo) {
        return toAjax(dbAbnormalInfoService.insertDbAbnormalInfo(dbAbnormalInfo));
    }

    @PostMapping("saveEquiment")
    public R saveEquiment(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) @RequestBody DbAbnormalInfo dbAbnormalInfo) {
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHandlerText(dbAbnormalInfo.getObjectType());
        return toAjax(dbAbnormalInfoService.insertDbAbnormalInfo(dbAbnormalInfo));
    }


    @PostMapping("saveEquimentOperation")
    public R saveEquimentOperation(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) @RequestBody DbAbnormalInfo dbAbnormalInfo) {
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(dbAbnormalInfo.getObjectType());
//        List<DbEquipment> dbEquipments = equipmentService.selectDbEquipmentList(dbEquipment);
//        DbEquipment dbEquipment1 = dbEquipments.get(0);
//        /*
//        * 如果下边只有一个设备的话   显示到土地
//        * */
//
//
//        dbAbnormalInfo.setDbEquipmentId(dbEquipment1.getEquipmentId());
//
//        dbAbnormalInfo.setObjectType(dbEquipment1.getEquipmentName());
        /*
        * 处理名称   蘑菇棚-大棚1   卷帘一卷起-无响应
        * */

        int i = dbAbnormalInfoService.insertDbAbnormalInfo(dbAbnormalInfo);
        return toAjax(i);
    }



    /**
     * 修改保存报警信息
     */
    @PostMapping("update")
    public R editSave(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) @RequestBody DbAbnormalInfo dbAbnormalInfo) {
        return toAjax(dbAbnormalInfoService.updateDbAbnormalInfo(dbAbnormalInfo));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(@ApiParam(name = "删除的id子串", value = "已逗号分隔的id集", required = true) String ids) {
        return toAjax(dbAbnormalInfoService.deleteDbAbnormalInfoByIds(ids));
    }

    /**
     * 查询报警信息列表
     */
    @GetMapping("listApp")
    @ApiOperation(value = "查询报警信息列表", notes = "报警信息列表")
    public R list1(@ApiParam(name = "开始时间", value = "date", required = true) String startTime) {
        String header = getRequest().getHeader(Constants.CURRENT_ID);
        DbLand dbLand = new DbLand();
        dbLand.setDbUserId(Long.valueOf(header));
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        List<DbAbnormalInfo> dbAbnormalInfos = new ArrayList<>();
        for (DbLand land : dbLands) {
            String equipmentIds = land.getEquipmentIds();
            DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
            if (StringUtils.isEmpty(equipmentIds)){
                continue;
            }
            for (String s : equipmentIds.split(",")) {
                if (StringUtils.isEmpty(startTime)) {
                    dbAbnormalInfo.setAlarmExplain(startTime);
                }
                dbAbnormalInfo.setDbEquipmentId(Long.valueOf(s));
                dbAbnormalInfos.addAll(dbAbnormalInfoService.selectAbnormalList(dbAbnormalInfo));
            }
        }

        return result1(dbAbnormalInfos);

    }

    @Autowired
    private IDbEquipmentAdminService dbEquipmentAdminService;

    /**
     * 获取预警信息
     * @since: 2.0.0
     * @param landId
     * @param date
     * @param currPage
     *  @param pageSize
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/9 15:11
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("getAbnormalInfo/{landId}/{date}/{currPage}/{pageSize}")
    @ApiOperation(value = "获取预警信息",notes = "获取预警信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "landId",value = "大棚id",required = false),
            @ApiImplicitParam(name = "date",value = "时间 2010-1-1 到天即可，没有可以为 null 或者 双引号",required = false),
            @ApiImplicitParam(name = "currPage",value = "从1 开始",required = true),
            @ApiImplicitParam(name = "pageSize",value = "条数 最大不得50",required = true)
    })
    public R getAbnormalInfo(@PathVariable("landId") Long landId,@PathVariable("date") Date date,@PathVariable("currPage") Long currPage,@PathVariable("pageSize") Long pageSize){
        if (pageSize > PageConf.MAX_PAGE_SIZE || currPage == null || pageSize == null){
            return null;
        }
        Long userId = Long.valueOf(getRequest().getHeader(Constants.CURRENT_ID));
        List<Long> list = new ArrayList<Long>();
        List<Map<String,Object>> mapList = null;
        if (landId != null && landId > 0 ){
            //查询单个大棚
            list = Arrays.asList(landId);
        }
        HashMap<Long, String> map = new HashMap<>();
        if (list.size() == 0){
            //查询多个大棚或者按照日期查询多个大棚
            mapList = dbEquipmentAdminService.selectLandIdAndNameByUserId(null,userId);
            if (mapList.size() == 0){
                return R.ok("暂无大棚！");
            }
        }else {
            mapList = dbEquipmentAdminService.selectLandIdAndNameByUserId(landId,userId);
        }
        for (Map<String, Object> e : mapList) {
            list.add(Long.valueOf(e.get("land_id")+""));
            map.put(Long.valueOf(e.get("land_id")+""),e.get("land_name")+"");
        }
        currPage = (currPage - 1) * pageSize;
        List<Map<String, String>> maps = dbAbnormalInfoService.selectAbnormals(list, userId, date, currPage, pageSize);
        for (Map<String, String> m : maps) {
            m.put("land_name",map.get(m.get("land_id")));
        }
        return R.data(maps);
    }



}
