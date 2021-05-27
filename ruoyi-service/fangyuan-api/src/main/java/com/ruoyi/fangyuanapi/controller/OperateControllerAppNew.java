package com.ruoyi.fangyuanapi.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.dto.FunctionDto;
import com.ruoyi.fangyuanapi.dto.LandDto;
import com.ruoyi.fangyuanapi.service.IDbEquipmentAdminService;
import com.ruoyi.fangyuanapi.service.IDbEquipmentComponentService;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.DbTcpClientService;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 新版app接口2.0
 * @Author zheng
 * @Date 2021/4/6 10:40
 * @Version 1.0
 */
@RestController
@Api("operateNewApp")
@RequestMapping("operateNewApp")
public class OperateControllerAppNew extends BaseController {

    @Autowired
    private IDbLandService landService;

    @Autowired
    private IDbEquipmentService equipmentService;

    @Autowired
    private IDbEquipmentAdminService dbEquipmentAdminService;

    @Autowired
    private IDbEquipmentComponentService dbEquipmentComponentService;

    @Autowired
    private RemoteTcpService remoteTcpService;

    @Autowired
    private DbTcpClientService dbTcpClientService;

    /*
     *列表回写    当前用户下边所有土地  DbLandAppVo
     *
     * */
    @GetMapping("showList")
    @ApiOperation(value = "查询当前用户下的操作列表", notes = "查询当前用户下的操作列表")
    public R showList() {
//            {{传感，功能集{功能名称，标识，状态，进度，指令{指令集}}}}
        long currentUserId = getCurrentUserId();
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        List<DbLandAppVo> dbLandAppVos = new ArrayList<>();
        DbLand dbLand = new DbLand();
        dbLand.setDbUserId(247L);
        List<DbLand> dbLands = landService.selectDbLandListByUserIdAndSideId(Long.valueOf(247L));
        dbLands.stream().forEach(ite -> {
            Arrays.asList(ite.getEquipmentIds().split(",")).forEach(ite2 -> {
                DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(ite2));
                DbLandAppVo dbLandAppVo = new DbLandAppVo();
                DbTcpType dbTcpType = new DbTcpType();
                dbTcpType.setHeartName(dbEquipment.getHeartbeatText() + "_" + dbEquipment.getEquipmentNoString());
                List<DbTcpType> list = remoteTcpService.list(dbTcpType);
                if (list.size() != 0 && list != null) {
                    DbTcpType dbTcpType1 = list.get(0);
                    dbLandAppVo.setDbTcpType(dbTcpType1);
                }
                List<DbLandAppVo.Function> functions = new ArrayList<DbLandAppVo.Function>();
                DbLandAppVo.Function function = new DbLandAppVo.Function();
                function.setEquipmentName(dbEquipment.getEquipmentName());
                function.setOperatePojos(com.alibaba.fastjson.JSON.parseArray(dbEquipment.getHandlerText(), OperatePojo.class));
                functions.add(function);
                dbLandAppVo.setFunctions(functions);
                dbLandAppVos.add(dbLandAppVo);
            });
        });
        return R.data(dbLandAppVos);
    }


    /***
     * APP2.0智控页
     * @since: 2.0.0
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/25 10:24
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    @GetMapping("getLandList")
    @ApiOperation(value = "APP2.0智控页", notes = "APP2.0智控页", httpMethod = "GET")
    public R getLandList() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        List<DbEquipmentAdmin> dbEquipmentAdmins = dbEquipmentAdminService.selectDbEquipmentAdminListByUserId(Long.valueOf(userId));
        if (CollectionUtils.isEmpty(dbEquipmentAdmins)) {
            return R.ok("当前暂无大棚,请前去添加！");
        }
        return R.data(getLandDto(dbEquipmentAdmins));
    }

    /**
     * @param landId 大棚Id
     * @return com.ruoyi.common.core.domain.R
     * @Author ZHAOXIAOSI
     * @Version 2.0.0
     * @Description 刷新单个大棚信息
     * @Date 16:23 2021/5/4
     * @sign 他日若遂凌云志, 敢笑黄巢不丈夫!
     **/
    @ApiOperation(value = "刷新单个大棚信息", notes = "刷新单个大棚信息", httpMethod = "GET")
    @GetMapping("refreshLandInfo/{landId}")
    @ApiImplicitParam(name = "landId", value = "大棚id", required = true)
    public R refreshLandInfo(@PathVariable("landId") Long landId) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbEquipmentAdmin admin = dbEquipmentAdminService.selectDbEquipmentAdminByUserIdAndLandId(landId, Long.valueOf(userId), null);
        if (admin == null || StringUtils.isEmpty(admin.getId() + "")) {
            return null;
        }
        return R.data(getLandDto(Arrays.asList(admin)).get(0));
    }

    private ArrayList<LandDto> getLandDto(List<DbEquipmentAdmin> dbEquipmentAdmins) {
        ArrayList<LandDto> landDtos = new ArrayList<>();
        for (DbEquipmentAdmin admin : dbEquipmentAdmins) {
            String ids = admin.getFunctionIds();
            String[] split = ids.split(",");
            if (split == null || split.length == 0) {
                continue;
            }
            LandDto landDto = new LandDto();
            List<DbEquipmentComponent> dbEquipmentComponents = dbEquipmentComponentService.selectDbEquipmentComponentByIds(split);
            ArrayList<FunctionDto> components = new ArrayList<>();
            Set<String> set =new HashSet<>();
            for (DbEquipmentComponent component : dbEquipmentComponents) {
                set.add(component.getHeartbeatText()+"_"+component.getEquipmentNoString());
            }
            HashMap<String, Integer> hashMap = new HashMap<>();
            //存放idauto
            HashMap<String, DbTcpType> map = new HashMap<>();
            for (String s : set) {
                DbTcpType dbTcpType = new DbTcpType();
                dbTcpType.setHeartName(s);
                List<DbTcpType> list = remoteTcpService.list(dbTcpType);
                if (list == null || list.size() == 0) {
                    continue;
                }
                DbTcpType tcpType = list.get(0);
                /** 此处取多个传感器的温度值，要求采用平均值并不合适  哪个传感器有温度就显示那个，如果有两个或者更多只显示后者 */
                if (strIsNotEmpty(tcpType.getTemperatureSoil())) {
                    landDto.setTemperatureSoil(tcpType.getTemperatureSoil());
                }
                if (strIsNotEmpty(tcpType.getHumiditySoil())) {
                    landDto.setHumiditySoil(tcpType.getHumiditySoil());
                }
                if (strIsNotEmpty(tcpType.getLight())) {
                    landDto.setLight(tcpType.getLight());
                }
                if (strIsNotEmpty(tcpType.getCo2())) {
                    landDto.setCo2(tcpType.getCo2());
                }
                if (strIsNotEmpty(tcpType.getTemperatureAir())) {
                    landDto.setTemperatureAir(tcpType.getTemperatureAir());
                }
                if (strIsNotEmpty(tcpType.getHumidityAir())) {
                    landDto.setHumidityAir(tcpType.getHumidityAir());
                }
                String s1 = s.split("_")[0];
                R r = dbTcpClientService.queryOne(s1);
                r.get("data");
                hashMap.put(s1, Integer.valueOf(r.get("data") + "") > 0 ? 0 : 1);
                map.put(s1, tcpType);
            }
            for (DbEquipmentComponent component : dbEquipmentComponents) {
                FunctionDto build = FunctionDto.builder().build();
                if (hashMap.get(component.getHeartbeatText()) == null) {
                    hashMap.put(component.getHeartbeatText(), hashMap.get(component.getHeartbeatText()));
                }
                build.setIsOnline(hashMap.get(component.getHeartbeatText()) == null ? "1" : hashMap.get(component.getHeartbeatText()) + "");
                Map<String, Object> operateText = (Map<String, Object>) JSON.parse(component.getSpList());

                DbTcpType type = map.get(component.getHeartbeatText());
                if (type != null) {
                    operateText.put("idAuto", type.getIdAuto() == null ? 1 :type.getIdAuto() );
                    operateText.put("autoControlType", type.getAutocontrolType() == null?"0":type.getAutocontrolType());
                    operateText.put("autoControlTypeEnd", type.getAutocontrolTypeEnd()== null?"0":type.getAutocontrolTypeEnd());
                }else {
                    operateText.put("idAuto", "1");
                    operateText.put("autoControlType", "0");
                    operateText.put("autoControlTypeEnd", "0");
                }
                if ("0".equals(operateText.get("isScheduled"))) {
                    //查询定时情况
                }

                build.setRes(operateText);
                components.add(build);
            }
            /** 是否显示，只要有一个温度就显示传感器 */
            if (StringUtils.isNotEmpty(landDto.getCo2()) || StringUtils.isNotEmpty(landDto.getHumidityAir()) ||
                    StringUtils.isNotEmpty(landDto.getHumiditySoil()) || StringUtils.isNotEmpty(landDto.getLight())
                    || StringUtils.isNotEmpty(landDto.getTemperatureAir()) || StringUtils.isNotEmpty(landDto.getTemperatureSoil())) {
                landDto.setIsShow("0");
            } else {
                landDto.setIsShow("1");
            }
            landDto.setFunctionList(components);
            landDto.setLandId(admin.getLandId());
            landDto.setLandName(admin.getLandName());
            landDto.setIsAdmin(admin.getIsSuperAdmin());
            landDtos.add(landDto);
        }

        return landDtos;
    }

    private static boolean strIsNotEmpty(String str) {
        if (StringUtils.isNotEmpty(str) && !"0.0".equals(str) && !"0".equals(str)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        JSONObject object = JSONUtil.parseObj("{\"spList\":[{\"handleName\":\"start\",\"handleCode\":\"15,162,255,00\"},{\"handleName\":\"start_stop\",\"handleCode\":\"15,162,00,00\"},{\"handleName\":\"down\",\"handleCode\":\"15,163,255,00\"},{\"handleName\":\"down_stop\",\"handleCode\":\"15,163,00,00\"}],\"functionId\":10547,\"percentage\":1,\"isSlide\":1,\"isScheduled\":1,\"checkName\":\"2卷帘\",\"startDate\":null,\"checkCode\":\"1\",\"switchState\":null}");
        Map<String, String> o = (Map<String, String>) JSON.parse("{\"isSlide\":1,\"isScheduled\":1,\"checkName\":\"2卷帘\",\"checkCode\":\"1\",\"spList\":[{\"handleName\":\"start\",\"handleCode\":\"15,162,255,00\"},{\"handleName\":\"start_stop\",\"handleCode\":\"15,162,00,00\"},{\"handleName\":\"down\",\"handleCode\":\"15,163,255,00\"},{\"handleName\":\"down_stop\",\"handleCode\":\"15,163,00,00\"}],\"functionId\":10547,\"percentage\":1,\"startDate\":null,\"switchState\":null}\n");
        String i = Integer.parseInt(Integer.valueOf("04") + "", 2) + "";
        System.out.println(i);
    }

}
