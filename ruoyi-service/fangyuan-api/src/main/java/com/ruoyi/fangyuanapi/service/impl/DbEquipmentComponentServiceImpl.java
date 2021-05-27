package com.ruoyi.fangyuanapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentMapper;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbEquipmentComponent;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentComponentMapper;
import com.ruoyi.fangyuanapi.service.IDbEquipmentComponentService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 版本加功能Service业务层处理
 * 
 * @author zheng
 * @date 2021-04-08
 */
@Service
@Log4j2
public class DbEquipmentComponentServiceImpl implements IDbEquipmentComponentService 
{
    @Autowired
    private DbEquipmentComponentMapper dbEquipmentComponentMapper;

    @Autowired
    private DbEquipmentMapper dbEquipmentMapper;

    /**
     * 查询版本加功能
     * 
     * @param id 版本加功能ID
     * @return 版本加功能
     */
    @Override
    public DbEquipmentComponent selectDbEquipmentComponentById(Long id)
    {
        return dbEquipmentComponentMapper.selectDbEquipmentComponentById(id);
    }

    /**
     * 查询版本加功能列表
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 版本加功能
     */
    @Override
    public List<DbEquipmentComponent> selectDbEquipmentComponentList(DbEquipmentComponent dbEquipmentComponent)
    {
        return dbEquipmentComponentMapper.selectDbEquipmentComponentList(dbEquipmentComponent);
    }

    /**
     * 新增版本加功能
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 结果
     */
    @Override
    public int insertDbEquipmentComponent(DbEquipmentComponent dbEquipmentComponent)
    {
        dbEquipmentComponent.setCreateTime(DateUtils.getNowDate());
        return dbEquipmentComponentMapper.insertDbEquipmentComponent(dbEquipmentComponent);
    }

    /**
     * 修改版本加功能
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDbEquipmentComponent(DbEquipmentComponent dbEquipmentComponent)
    {
        return dbEquipmentComponentMapper.updateDbEquipmentComponent(dbEquipmentComponent);
    }

    /**
     * 删除版本加功能对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentComponentByIds(String ids)
    {
        return dbEquipmentComponentMapper.deleteDbEquipmentComponentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除版本加功能信息
     * 
     * @param id 版本加功能ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentComponentById(Long id)
    {
        return dbEquipmentComponentMapper.deleteDbEquipmentComponentById(id);
    }

    @Override
    public List<DbEquipmentComponent> selectDbEquipmentComponentByIds(String[] split) {

        return dbEquipmentComponentMapper.selectDbEquipmentComponentByIds(split);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateLightStatus(String heartbeatText, String switchState,Integer fillLightTimingStatus) {
        DbEquipmentComponent component =  dbEquipmentComponentMapper.selectDbEquipmentComponentByheartbeatText(heartbeatText);
        if (component == null){
            return R.ok();
        }
        Map<String,Object> parse = (Map<String, Object>) JSON.parse(component.getSpList());
        parse.put("switchState",Integer.parseInt(switchState) == 0? 1 :0);
        if (fillLightTimingStatus == null){
            parse.put("startDate",null);
        }
        component.setSpList(JSON.toJSONString(parse,SerializerFeature.WriteMapNullValue));
        int i = dbEquipmentComponentMapper.updateDbEquipmentComponent(component);
        System.out.println(i);
        return i>0 ? R.ok() : R.error();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updatFunctionLogo(String heartbeatText, String functionLogo, String switchState, Integer fillLightTimingStatus) {
        log.info("进入updatFunctionLogo heartbeatText：{}， functionLogo：{}， switchState：{}，fillLightTimingStatus：{}",heartbeatText,functionLogo,switchState,fillLightTimingStatus);
        DbEquipmentComponent component = dbEquipmentComponentMapper.selectDbEquipmentComponentByheartbeatTextAndLogo(heartbeatText,functionLogo);
        if (component == null){
            return R.ok();
        }
        Map<String,Object> parse = (Map<String, Object>) JSON.parse(component.getSpList());
        parse.put("switchState",Integer.parseInt(switchState) == 0? 1 :0);
        if (fillLightTimingStatus == null){
            parse.put("startDate",null);
        }
        component.setSpList(JSON.toJSONString(parse,SerializerFeature.WriteMapNullValue));
        int i = dbEquipmentComponentMapper.updateDbEquipmentComponent(component);
        System.out.println(i);
        return i>0 ? R.ok() : R.error();
    }


    @Override
    public int updateDbEquipmentComponentProgress(List<String> list, String heartbeatText) {
        DbEquipment equipment = dbEquipmentMapper.selectByHeartbeatText(heartbeatText);
        String text = equipment.getHandlerText();
        if (StringUtils.isEmpty(text)){
            return 0;
        }

        /**
         * 根据录入设备时的自然顺序 对应设备返回的进度值
         * */
        List<Map<String,Object>> handlerText = (List<Map<String, Object>>) JSON.parse(text);
        ArrayList<Long> rollerBlindIds = new ArrayList<>();
        ArrayList<Long> convolutionMembraneIds = new ArrayList<>();
        for (Map<String, Object> map : handlerText) {
            if (Integer.parseInt(map.get("checkCode")+"") == 1){
                rollerBlindIds.add(Long.valueOf(map.get("functionId")+""));
            }
            if (Integer.parseInt(map.get("checkCode")+"") == 2){
                convolutionMembraneIds.add(Long.valueOf(map.get("functionId")+""));
            }
        }
        int result = 0;
        for (int i = 0; i < rollerBlindIds.size(); i++) {
            result += updateEquipmentComponent(rollerBlindIds.get(i),Integer.parseInt(list.get(i),16));
        }

        for (int i = 0; i < convolutionMembraneIds.size(); i++) {
            result += updateEquipmentComponent(convolutionMembraneIds.get(i),Integer.parseInt(list.get(i+2),16));
        }

        return result;
    }

    /**
     * 方法描述
     * @since: 2.0.0
     * @param id  功能id
     * @param value   进度值
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/5/20 18:49
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    private int updateEquipmentComponent(Long id,Integer value){
        DbEquipmentComponent component = dbEquipmentComponentMapper.selectDbEquipmentComponentById(id);
        Map<String,Object> map = (Map<String, Object>) JSON.parse(component.getSpList());
        map.put("percentage",value);
        return dbEquipmentComponentMapper.updateDbEquipmentComponent(DbEquipmentComponent.builder()
                .id(id)
                .spList(JSON.toJSONString(map,SerializerFeature.WriteMapNullValue))
                .build());
    }

}
