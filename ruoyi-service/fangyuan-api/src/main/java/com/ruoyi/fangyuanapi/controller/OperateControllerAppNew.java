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
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
                function.setOperatePojos(com.alibaba.fastjson.JSON.parseArray(dbEquipment.getHandlerText(),OperatePojo.class ));
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
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("getLandList")
    @ApiOperation(value = "APP2.0智控页",notes = "APP2.0智控页",httpMethod = "GET")
    public R getLandList(){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        List<DbEquipmentAdmin> dbEquipmentAdmins = dbEquipmentAdminService.selectDbEquipmentAdminListByUserId(Long.valueOf(userId));
        if (CollectionUtils.isEmpty(dbEquipmentAdmins)){
            return R.ok("当前暂无大棚,请前去添加！");
        }
        ArrayList<LandDto> landDtos = new ArrayList<>();
        for (DbEquipmentAdmin admin : dbEquipmentAdmins) {
            String ids = admin.getFunctionIds();
            String[] split = ids.split(",");
            if (split == null || split.length == 0){
                continue;
            }
            LandDto landDto = new LandDto();
            HashSet<String> set = new HashSet<>();
            List<DbEquipmentComponent> dbEquipmentComponents = dbEquipmentComponentService.selectDbEquipmentComponentByIds(split);
            ArrayList<FunctionDto> components = new ArrayList<>();
            HashMap<String, String> hashMap = new HashMap<>();
            for (DbEquipmentComponent component : dbEquipmentComponents) {
                set.add(component.getHeartbeatText());
                FunctionDto build = FunctionDto.builder()
                        .res(JSON.parseObject(component.getSpList()))
                        .build();
                if (hashMap.get(component.getHeartbeatText()) == null){
                    hashMap.put(component.getHeartbeatText(),dbTcpClientService.queryOne(component.getHeartbeatText()).get("data")+"");
                }
                build.setIsOnline(hashMap.get(component.getHeartbeatText()).equals("0")? "1" : "0");
                components.add(build);
            }
            for (String s : set) {
                DbTcpType dbTcpType = new DbTcpType();
                dbTcpType.setHeartName(s);
                List<DbTcpType> list = remoteTcpService.list(dbTcpType);
                if (list == null || list.size() == 0){
                    continue;
                }
                DbTcpType tcpType = list.get(0);
                /** 此处取多个传感器的温度值，要求采用平均值并不合适  哪个传感器有温度就显示那个，如果有两个或者更多只显示后者 */
                if (strIsNotEmpty(tcpType.getTemperatureSoil())){
                    landDto.setTemperatureSoil(tcpType.getTemperatureSoil());
                }
                if (strIsNotEmpty(tcpType.getHumiditySoil())){
                    landDto.setHumiditySoil(tcpType.getHumiditySoil());
                }
                if (strIsNotEmpty(tcpType.getLight())){
                    landDto.setLight(tcpType.getLight());
                }
                if (strIsNotEmpty(tcpType.getCo2())){
                    landDto.setCo2(tcpType.getCo2());
                }
                if (strIsNotEmpty(tcpType.getTemperatureAir())){
                    landDto.setTemperatureAir(tcpType.getTemperatureAir());
                }
                if (strIsNotEmpty(tcpType.getHumidityAir())){
                    landDto.setHumidityAir(tcpType.getHumidityAir());
                }
            }
            /** 是否显示，只要有一个温度就显示传感器 */
            if (StringUtils.isNotEmpty(landDto.getCo2()) || StringUtils.isNotEmpty(landDto.getHumidityAir()) ||
                    StringUtils.isNotEmpty(landDto.getHumiditySoil()) || StringUtils.isNotEmpty(landDto.getLight())
                    || StringUtils.isNotEmpty(landDto.getTemperatureAir()) || StringUtils.isNotEmpty(landDto.getTemperatureSoil())){
                landDto.setIsShow("0");
            }else {
                landDto.setIsShow("1");
            }
            landDto.setFunctionList(components);
            landDto.setLandId(admin.getLandId());
            landDto.setLandName(admin.getLandName());
            landDto.setIsAdmin(admin.getIsSuperAdmin());
            landDtos.add(landDto);
        }
        return R.data(landDtos);
    }

    private static boolean strIsNotEmpty(String str){
        if (StringUtils.isNotEmpty(str) && !"0.0".equals(str) && !"0".equals(str)){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        JSONObject object = JSONUtil.parseObj("{\"spList\":[{\"handleName\":\"start\",\"handleCode\":\"15,162,255,00\"},{\"handleName\":\"start_stop\",\"handleCode\":\"15,162,00,00\"},{\"handleName\":\"down\",\"handleCode\":\"15,163,255,00\"},{\"handleName\":\"down_stop\",\"handleCode\":\"15,163,00,00\"}],\"functionId\":10547,\"percentage\":1,\"isSlide\":1,\"isScheduled\":1,\"checkName\":\"2卷帘\",\"startDate\":null,\"checkCode\":\"1\",\"switchState\":null}");
        Map<String,String> o = (Map<String, String>) JSON.parse("{\"isSlide\":1,\"isScheduled\":1,\"checkName\":\"2卷帘\",\"checkCode\":\"1\",\"spList\":[{\"handleName\":\"start\",\"handleCode\":\"15,162,255,00\"},{\"handleName\":\"start_stop\",\"handleCode\":\"15,162,00,00\"},{\"handleName\":\"down\",\"handleCode\":\"15,163,255,00\"},{\"handleName\":\"down_stop\",\"handleCode\":\"15,163,00,00\"}],\"functionId\":10547,\"percentage\":1,\"startDate\":null,\"switchState\":null}\n");
        System.out.println(o);
    }
}
