package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.conf.OperationConf;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.service.IDbLandService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


    @Autowired
    private IDbEquipmentService equipmentService;


    @Autowired
    private RemoteTcpService remoteTcpService;

    @Autowired
    private OperationConf operationConf;

    @RequestMapping("getOperationConf")
    public R getOperationConf(){
        return R.data(operationConf.getTyps());
    }


    @PostMapping("sendStopOperation")
    @ApiOperation(value = "批量暂停",notes = "批量对土地下的设备暂停： 粒度为单个操作：1卷帘 2 通风/卷膜 3补光 4浇水 5打药 6 施肥  7 锄草 8滴灌",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "landId",value = "土地id",required = true),
            @ApiImplicitParam(name = "type",value = "操作类型",required = true)
    })
    public R sendStopOperation(@RequestParam Long landId, @RequestParam String type){
        DbLand land = dbLandService.selectDbLandById(landId);
        String ids = land.getEquipmentIds();
        if (StringUtils.isEmpty(ids)){
            return R.error("该土地尚未绑定设备！");
        }
        String[] split = ids.split(",");
        ArrayList<DbOperationVo> list = new ArrayList<>();
        for (String id : split) {
            DbEquipment equipment = equipmentService.selectDbEquipmentById(Long.valueOf(id));
            List<OperatePojo> pojos = JSON.parseArray(equipment.getHandlerText(), OperatePojo.class);
            pojos.forEach(e ->{
                if (type.equals(e.getCheckCode())){
                    e.getSpList().forEach(d ->{
                        if ("start_stop".equals(d.getHandleName()) || "down_stop".equals(d.getHandleName())) {
                            DbOperationVo vo = new DbOperationVo();
                            vo.setHeartName(equipment.getHeartbeatText());
                            vo.setFacility(equipment.getEquipmentNo()+"");
                            vo.setOperationId(equipment.getEquipmentId()+"");
                            vo.setOperationName(equipment.getEquipmentName() + " " + e.getCheckName() + " " + d.getHandleName());
                            vo.setOperationText(d.getHandleCode());
                            list.add(vo);
                        }
                    });
                }
            });
        }
        R r = remoteTcpService.operationList(list);
        return r;
    }

    @PostMapping("sendOperation")
    @ApiOperation(value = "对土地下的设备批量操作",notes = "批量操作",httpMethod = "POST")
    @ApiParam(name = "dbOperationVos", value = "传入json格式", required = true)
    public R sendOperation(@RequestBody List<DbOperationVo> dbOperationVos) {

        if (dbOperationVos == null || dbOperationVos.size() <= 0){
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        R r = remoteTcpService.operationList(dbOperationVos);
        return r;
    }

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{landId}")
    @ApiOperation(value = "根据土地id获取详细信息",notes = "根据土地id查询",httpMethod = "GET")
    public DbLand get(@PathVariable("landId") Long landId) {
        return dbLandService.selectDbLandById(landId);
    }

    /**
     * 查询 土地下对应所有设备的操作集
     */
    @GetMapping("getLandOperation/{landId}")
    @ApiOperation(value = "土地下对应所有设备的操作集", notes = "查询操作" ,httpMethod = "POST")
    @ApiImplicitParam(name = "landId",value = "土地Id",required = true)
    public R getLandOperation(@PathVariable("landId") Long landId) {
        if (landId < 0) {
            return null;
        }
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        List<Map<String, Object>> result = dbLandService.selectLandOperationByLandId(landId);
        return result == null || result.size() <= 0 ? R.error("该土地下暂未绑定设备！") : R.data(result);
    }

    /**
     * 查询土地列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询土地列表", notes = "土地列表")
    public R list(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setSiteId(0L);
        startPage();
        return result(dbLandService.selectDbLandNoSiteList(dbLand));
    }

//    /**
//     * APP查询土地列表
//     *
//     */
//    @GetMapping("getLandListApp")
//    @ApiOperation(value = "App查询土地列表", notes = "土地列表")
//    public R getLandListApp(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
//        String userId = getRequest().getHeader(Constants.CURRENT_ID);
//        dbLand.setDbUserId(Long.valueOf(userId));
//        List<DbLand> lands = dbLandService.selectDbLandWeChatList(dbLand);
//        return R.data(lands);
//    }

    /**
     * APP查询土地列表
     */
    @GetMapping("getLandListApp")
    @ApiOperation(value = "App查询土地列表", notes = "土地列表")
    public R getLandListApp(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        List<Map<String, Object>> result = dbLandService.selectDbLandByUserIdAndSideId(Long.valueOf(userId));
        return R.data(result);

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
     * 查询土地列表
     */
    @GetMapping("listBinding")
    @ApiOperation(value = "查询土地列表app", notes = "土地列表")
    public R listBinding(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setSiteId(0l);
        List<LandVo> landVos = new ArrayList<>();
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);

        for (DbLand land : dbLands) {

            LandVo landVo = new LandVo();
            landVo.setPlotName(land.getNickName());
            DbLand dbLand1 = new DbLand();
            dbLand1.setSiteId(land.getLandId());
            List<DbLand> dbLands2 = dbLandService.selectDbLandList(dbLand1);
            landVo.setLands(dbLands2);
            landVos.add(landVo);
        }

        return R.data(landVos);
    }


    /**
     * 新增保存土地
     */
    @PostMapping("save")
    @ApiOperation(value = "新增土地/地块", notes = "土地/地块id")
    public R addSave(@RequestBody DbLand dbLand, HttpServletRequest request) {
        if (dbLand == null) {
            return R.error();
        }
        String userId = request.getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setCreateTime(new Date());
        int i = dbLandService.insertDbLand(dbLand);
        return R.data(dbLand.getLandId());
    }

    /**
     * 新增保存土地
     */
    @PostMapping("weChatSave")
    @ApiOperation(value = "新增土地/地块", notes = "小程序新增土地地块")
    public R weChatAddSave(@RequestBody DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        if (dbLand == null) {
            return R.error("添加失败！");
        }
        dbLand.setDbUserId(Long.valueOf(userId));
        R r = dbLandService.weChatAddSave(dbLand);
        return r;
    }


    /**
     * 修改保存土地
     */
    @PostMapping("update")
    @ApiOperation(value = "修改土地/地块", notes = "修改土地", httpMethod = "POST")
    public R editSave(@RequestBody DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        return toAjax(dbLandService.updateDbLand(dbLand));
    }


    /**
     * 删除${tableComment}
     */
    @GetMapping("remove")
    public R remove(String landId) {
        int i = dbLandService.deleteDbLandById(Long.valueOf(landId));
        return i>0 ? R.ok() : R.error("删除失败或者检查选中的地块下是否存在土地！");
    }

    /*
     * 用户关联地块返回
     * */
    @GetMapping("listPlot")
    public R listPlot(DbLand dbLand) {
//        获取当前用户id
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setSiteId(0L);
        startPage();
        return result(dbLandService.selectDbLandList(dbLand));
    }


    /*
     * 根据土地id返回当前的装态
     * */
    @GetMapping("typeNow/{landId}")
    @ApiOperation(value = " 根据土地id返回当前的装态", notes = " 根据土地id返回当前的装态", httpMethod = "GET")
    public R typeNow(@ApiParam(name = "Long", value = "Long格式", required = true) @PathVariable("landId") Long landId) {
        DbLand dbLand = dbLandService.selectDbLandById(landId);
        String equipmentIds = dbLand.getEquipmentIds();
        String[] split = StringUtils.isEmpty(equipmentIds)? new String[]{} :equipmentIds.split(",");
        String s = null;
        if (StringUtils.isEmpty(equipmentIds)) {
            return R.data(null);
        }
        if (split.length == 0) {
            s = equipmentIds;
        } else {
            s = split[0];
        }
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(s));
        String heartbeatText = dbEquipment.getHeartbeatText();
        String equipmentNo = dbEquipment.getEquipmentNoString();
        String hear = heartbeatText + "_" + equipmentNo;
        DbTcpType dbTcpType = new DbTcpType();
        dbTcpType.setHeartName(hear);
        List<DbTcpType> list = remoteTcpService.list(dbTcpType);
        DbTcpType dbTcpType1 = list.get(0);
        return R.data(dbTcpType1);
    }

    /*
     * 已有数据添加地块
     * */
    @GetMapping("demo")
    public void demo() {
        DbLand dbLand = new DbLand();
        /*
         * 用户分组
         * */
        List<Long> d = dbLandService.groupByUserId();

        for (Long aLong : d) {
            dbLand.setDbUserId(aLong);
            dbLand.setSiteId(1l);
            List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
//                添加地块
            if (dbLands.size() >= 6) {
                for (int i = 0; i <= ((dbLands.size() / 6)); i++) {
                    DbLand dbLand1 = new DbLand();
                    dbLand1.setSiteId(0l);
                    dbLand1.setNickName("地块" + (i + 1));
                    dbLand1.setDbUserId(aLong);
                    int i1 = dbLandService.insertDbLand(dbLand1);
                    separatedList(dbLands, i, dbLand1.getLandId());
                }
            } else {
                DbLand dbLand1 = new DbLand();
                dbLand1.setSiteId(0l);
                dbLand1.setNickName("地块1");
                dbLand1.setDbUserId(aLong);
                int i1 = dbLandService.insertDbLand(dbLand1);
                for (DbLand land : dbLands) {
                    land.setSiteId(dbLand1.getLandId());
                    int i2 = dbLandService.updateDbLand(land);
                }
            }
        }
    }

    private void separatedList(List<DbLand> dbLands, int i, Long landId) {
        if (dbLands.size() <= i * 6) {
            for (int i1 = i * 6; i1 < i * 6; i1++) {
                DbLand dbLand = dbLands.get(i1);
                dbLand.setSiteId(landId);
                int i2 = dbLandService.updateDbLand(dbLand);
            }
        } else {
            for (int i1 = i * 6; i1 < dbLands.size(); i1++) {
                DbLand dbLand = dbLands.get(i1);
                dbLand.setSiteId(landId);
                int i2 = dbLandService.updateDbLand(dbLand);
            }
        }
    }
}
