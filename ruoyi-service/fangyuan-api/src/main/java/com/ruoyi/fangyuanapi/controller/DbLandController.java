package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.PhoneUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.conf.OperationConf;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentComponentMapper;
import com.ruoyi.fangyuanapi.service.*;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.RemoteTcpService;
import com.ruoyi.system.feign.SendSmsClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private IDbQrCodeService dbQrCodeService;

    @Autowired
    private IDbEquipmentAdminService dbEquipmentAdminService;

    @Autowired
    private SendSmsClient smsClient;

    @Autowired
    private IDbUserService dbUserService;

    @RequestMapping("getOperationConf")
    public R getOperationConf(){
        return R.data(operationConf.getTyps());
    }


    @PutMapping("updateDbLand")
    @ApiOperation(value = "修改大棚接口",notes = "修改大棚接口,非超级管理员禁止修改",httpMethod ="PUT" )
    public R updateDbLand(DbLand land){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbEquipmentAdmin admin = dbEquipmentAdminService.selectDbEquipmentAdminByUserIdAndLandId(land.getLandId(), Long.valueOf(userId), null);
        if (admin == null){
            return R.error(HttpStatus.BAD_REQUEST.value(),"错误参数！");
        }
        if ( admin.getIsSuperAdmin() > 0L){
            return R.ok("您不是超级管理员，不具备修改的权限！");
        }
        dbLandService.updateDbLand(land);
        if (!admin.getLandName().equals(land.getNickName())){
            admin.setLandName(land.getNickName());
            dbEquipmentAdminService.updateDbEquipmentAdmin(admin);
        }
        return R.ok();
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
        // 0001 0000 0000
        R r = remoteTcpService.operationList(dbOperationVos);
        return r;
    }

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{landId}")
    @ApiOperation(value = "根据土地id获取详细信息",notes = "根据土地id查询",httpMethod = "GET")
    public R get(@PathVariable("landId") Long landId) {

        return R.data(dbLandService.selectDbLandById(landId));
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

    /*设备返回用户id*/
    @GetMapping("deviceBelongs/{equipmentId}")
    private R deviceBelongs( @PathVariable("equipmentId") Long equipmentId){
        DbLand dbLand = new DbLand();
        dbLand.setEquipmentIds(equipmentId.toString());
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        return R.data(JSON.toJSONString(dbLands));
    }

    /**
     * APP查询土地列表
     */
    @GetMapping("getLandListApp")
    @ApiOperation(value = "App查询土地列表", notes = "土地列表")
    public R getLandListApp() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbLand dbLand = new DbLand();
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
//    @GetMapping("listBinding")
//    @ApiOperation(value = "查询土地列表app", notes = "土地列表")
//    public R listBinding(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
//        String userId = getRequest().getHeader(Constants.CURRENT_ID);
//        dbLand.setDbUserId(Long.valueOf(userId));
//        dbLand.setSiteId(0l);
//        List<LandVo> landVos = new ArrayList<>();
//        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
//
//        for (DbLand land : dbLands) {
//
//            LandVo landVo = new LandVo();
//            landVo.setPlotName(land.getNickName());
//            DbLand dbLand1 = new DbLand();
//            dbLand1.setSiteId(land.getLandId());
//            List<DbLand> dbLands2 = dbLandService.selectDbLandList(dbLand1);
//            landVo.setLands(dbLands2);
//            landVos.add(landVo);
//        }
//
//        return R.data(landVos);
//    }

    /**
     * 获取大棚列表 只展示他自己的
     * @since: 2.0.0
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/28 15:51
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("getLandDataList")
    @ApiOperation(value = "APP2.0获取土地列表",notes = "APP2.0获取土地列表",httpMethod = "GET")
    public R getLandDataList(){
        return R.data(dbLandService.selectDbLandsByUserIdAndSiteId(Long.valueOf(getRequest().getHeader(Constants.CURRENT_ID))));
    }


    @GetMapping("listBinding")
    @ApiOperation(value = "查询土地列表app", notes = "土地列表",httpMethod = "GET")
    public R listBinding() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        return R.data(dbLandService.selectDbLandsByUserId(Long.valueOf(userId)));
    }

    /**
     * 决定是否未第一次绑定接口 是，跳入添加大棚或者选择大棚，否，直接驳回，告知去app联系管理员添加
     * @since: 2.0.0
     * @param qrCodeId 二维码id
     * @return: com.ruoyi.common.core.domain.R  1：被绑定，直接驳回 0：未绑定，
     * @author: ZHAOXIAOSI
     * @date: 2021/4/16 13:51
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("listBinding")
    @ApiOperation(value = "决定是否未第一次绑定接口",notes = "决定是否未第一次绑定接口 是，跳入添加大棚或者选择大棚，否，直接驳回，告知去app联系管理员添加",httpMethod = "POST")
    public R listBinding(Long qrCodeId){
        //查，是否已经绑定过
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbQrCode dbQrCode = dbQrCodeService.selectDbQrCodeById(qrCodeId);
        //管理员 id
        Long id = dbQrCode.getAdminUserId();
        HashMap<String, Object> map = new HashMap<>();
        if (id != null){
            // 已经被绑定 进行验证 方可通过
            if (!userId.equals(id+"")){
                //绑定者是当前用户  告知当前绑定在那个棚下 以及功能
                DbUser user = dbUserService.selectDbUserById(Long.valueOf(userId));
                map.put("phone",PhoneUtils.replacePhone(user.getPhone(),3,7,"8"));
                map.put("nickName",user.getNickname());
                map.put("avatar",user.getAvatar());
                map.put("isBinding",1);
                return R.data(map);
            }
        }
        map.put("isBinding",0);
        return R.data(map);
    }

    /**
     * 将设备绑定在新增大棚上
     * @since: 2.0.0
     * @param dbLand
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/19 11:11
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("bindingEquipmentToLand")
    @ApiOperation(value = "将设备绑定在新增大棚上",notes = "将设备绑定在新增大棚上 ： 添加操作",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address",value = "详细地址 例如门牌号",required = true),
            @ApiImplicitParam(name = "area",value = "土地面积 单位亩",required = true),
            @ApiImplicitParam(name = "latitude",value = "纬度",required = false),
            @ApiImplicitParam(name = "longitude",value = "经度",required = false),
            @ApiImplicitParam(name = "nickName",value = "大棚昵称",required = true),
            @ApiImplicitParam(name = "productCategory",value = "产品类别",required = true),
            @ApiImplicitParam(name = "productName",value = "产品名称",required = true),
            @ApiImplicitParam(name = "region",value = "地区格式（山西省,太原市,小店区）",required = true),
            @ApiImplicitParam(name = "noteText",value = "备注",required = false),
            @ApiImplicitParam(name = "equipmentId",value = "设备id",required = true),
    })
    public R insertEquipmentToLand(DbLand dbLand,Long equipmentId) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        return dbLandService.insertEquipmentToLand(dbLand,userId,equipmentId) ? R.ok():R.error("绑定出错了，请稍后再试或者联系管理员！") ;
    }

    /**
     * 将设备绑定在已有大棚上
     * @since: 2.0.0
     * @param landId
     * @param equipmentId
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/19 11:10
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PutMapping("bindingEquipmentToLand")
    @ApiOperation(value = "将设备绑定在已有大棚上",notes = "将设备绑定在已有大棚上 : 修改操作",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "landId",value = "土地Id",required = true),
            @ApiImplicitParam(name = "equipmentId",value = "设备Id",required = true)
    })
    public R updateEquipmentToLand(Long landId,Long equipmentId){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        return dbLandService.updateEquipmentToLand(landId,equipmentId ,userId) ? R.ok() : R.error("绑定出错了，请稍后再试或者联系管理员！");
    }

    /***
     * 绑定设备大棚确认接口 未使用
     * @since: 2.0.0
     * @param landId
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/16 13:49
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("bindingAck")
    public R bindingAck(Long landId){
        DbLand land = dbLandService.selectDbLandById(landId);
        int length = land.getEquipmentIds().split(",").length;
        HashMap<String, Object> map = new HashMap<>();
        map.put("landName",land.getNickName());
        map.put("num",length);
        return R.data(map);
    }

    /**
     * 获取单个大棚操作信息
     * @Author BugKing
     * @Date 14:53 2021/5/2
     * @since: 2.0.0
     * @param  landId
     * @return com.ruoyi.common.core.domain.R
     * @sign 他日若遂凌云志,敢笑黄巢不丈夫!
     **/
    @GetMapping("getOneLand")
    public R getOneLand(Long landId){

        return null;
    }

    /**
     * 设备和大棚绑定接口 未使用
     * @since: 2.0.0
     * @param phone
     * @param code
     * @param landId
     * @param landName
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/16 13:52
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */

    @PostMapping("bindingEquipment")
    public R bindingEquipment(String phone,String code,Long landId,String landName){
        Long userId = Long.valueOf(getRequest().getHeader(Constants.CURRENT_ID));
        R r = smsClient.checkCode(phone, code);
        if ("200".equals(r.get("code")+"")){
            DbUser user = dbUserService.selectDbUserById(userId);
            DbEquipmentAdmin equipmentAdmin = dbEquipmentAdminService.selectDbEquipmentAdminByUserIdAndLandId(landId, userId, null);
            if (equipmentAdmin != null){
                return R.error("您已经绑定该设备了!");
            }
            int i = dbEquipmentAdminService.insertDbEquipmentAdmin(DbEquipmentAdmin.builder()
                    .createTime(new Date())
                    .isSuperAdmin(1L)
                    .userId(Long.valueOf(userId))
                    .avatar(user.getAvatar())
                    .landId(landId)
                    .landName(landName)
                    .build());
            return  i > 0 ? R.ok():R.error();
        }
        return r;
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
            dbLand.setSiteId(1L);
            List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
//                添加地块
            if (dbLands.size() >= 6) {
                for (int i = 0; i <= ((dbLands.size() / 6)); i++) {
                    DbLand dbLand1 = new DbLand();
                    dbLand1.setSiteId(0L);
                    dbLand1.setNickName("地块" + (i + 1));
                    dbLand1.setDbUserId(aLong);
                    int i1 = dbLandService.insertDbLand(dbLand1);
                    separatedList(dbLands, i, dbLand1.getLandId());
                }
            } else {
                DbLand dbLand1 = new DbLand();
                dbLand1.setSiteId(0L);
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

    /**
     * 查询用户下的大棚
     * @since: 2.0.0
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/13 9:46
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping()
    public R getAPPLandList(){
        Long userId = Long.valueOf(getRequest().getHeader(Constants.CURRENT_ID));
        List<DbLand> lands = dbLandService.selectDbLandsByUserId(userId);
        //todo
        return null;
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

    @Autowired
    private DbEquipmentComponentMapper dbEquipmentComponentMapper;

    /**
     * 将1.0运行时产生的数据转换到2.0
     * @since: 2.0.0
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/19 15:17
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("dataChange")
    @Transactional(rollbackFor = Exception.class)
    public R dataChange(){
        //所有用户数据
        List<DbUser> dbUserList = dbUserService.selectDbUserList(null);
        for (DbUser dbUser : dbUserList) {
            //一个用户下所有的土地
            List<DbLand> list = dbLandService.selectDbLandsByUserId(dbUser.getId());
            for (DbLand land : list) {
                //先查该土地是否已经有了超级管理员  如果有那么将该管理员添加为子管理员
                String equipmentIds = land.getEquipmentIds();

                if (StringUtils.isEmpty(equipmentIds) ){
                    continue;
                }
                DbEquipmentAdmin admin = dbEquipmentAdminService.selectIsSuperAdmin(land.getLandId());
                DbEquipmentAdmin dbEquipmentAdmin = null;
                String functionIds = "";
                if (admin == null){
                    //添加为超级管理员
                    dbEquipmentAdminService.insertDbEquipmentAdmin(insertAdmin(dbUser,0L,land));
                }else {
                    //添加为子管理员
                    dbEquipmentAdminService.insertDbEquipmentAdmin(insertAdmin(dbUser,admin.getUserId(),land));
                }
            }
        }
        return R.ok("大棚管理员转换完成！");
    }
    //操作集为空
    private DbEquipmentAdmin  insertAdmin(DbUser dbUser,Long isSuperAdmin,DbLand land ){
        String[] split = land.getEquipmentIds().split(",");
        String functionIds = "";
        for (int i = 0; i < split.length; i++) {
            if (i == 0){
                functionIds = getDbEquipmentComponent(Long.valueOf(split[i]));
            }else {
                functionIds = functionIds + "," + getDbEquipmentComponent(Long.valueOf(split[i]));
            }
        }
        return DbEquipmentAdmin.builder()
                .isSuperAdmin(isSuperAdmin)
                .isDel(0)
                .functionIds(functionIds)
                .createTime(new Date())
                .avatar(dbUser.getAvatar())
                .landId(land.getLandId())
                .landName(land.getNickName())
                .userId(dbUser.getId())
                .build();
    }

    public String getDbEquipmentComponent(Long equipmentId){
        List<DbEquipmentComponent> dbEquipmentComponentList = dbEquipmentComponentMapper.selectDbEquipmentComponentByEquipmentId(equipmentId);
        List<Long> ids = dbEquipmentComponentList.stream().map(DbEquipmentComponent::getId).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i == 0){
                builder.append(ids.get(i));
            }else {
                builder.append(",").append(ids.get(i));
            }
        }
        return builder.toString();
    }

}
