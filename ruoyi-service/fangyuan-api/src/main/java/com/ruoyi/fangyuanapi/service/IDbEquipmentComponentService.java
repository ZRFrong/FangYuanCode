package com.ruoyi.fangyuanapi.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbEquipmentComponent;

import java.util.List;

/**
 * 版本加功能Service接口
 * 
 * @author zheng
 * @date 2021-04-08
 */
public interface IDbEquipmentComponentService 
{
    /**
     * 查询版本加功能
     * 
     * @param id 版本加功能ID
     * @return 版本加功能
     */
    public DbEquipmentComponent selectDbEquipmentComponentById(Long id);

    /**
     * 查询版本加功能列表
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 版本加功能集合
     */
    public List<DbEquipmentComponent> selectDbEquipmentComponentList(DbEquipmentComponent dbEquipmentComponent);

    /**
     * 新增版本加功能
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 结果
     */
    public int insertDbEquipmentComponent(DbEquipmentComponent dbEquipmentComponent);

    /**
     * 修改版本加功能
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 结果
     */
    public int updateDbEquipmentComponent(DbEquipmentComponent dbEquipmentComponent);

    /**
     * 批量删除版本加功能
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbEquipmentComponentByIds(String ids);

    /**
     * 删除版本加功能信息
     * 
     * @param id 版本加功能ID
     * @return 结果
     */
    public int deleteDbEquipmentComponentById(Long id);

    /***
     * 批量查询功能对象
     * @since: 2.0.0
     * @param split 功能id数组
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipmentComponent>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/25 11:26
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<DbEquipmentComponent> selectDbEquipmentComponentByIds(String[] split);

    /**
     * 方法描述
     * @since: 2.0.0
     * @param heartbeatText
     * @param switchState
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/18 23:06
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    R updateLightStatus(String heartbeatText, String switchState,Integer fillLightTimingStatus);

    /**
     * 方法描述 修改操作状态
     * @since: 2.0.0
     * @param heartbeatText
     * @param switchState
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/18 23:06
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    R updatFunctionLogo(String heartbeatText,String fucntionLogo, String switchState,Integer fillLightTimingStatus);

    /**
     * 修改功能进度数值
     * @since: 2.0.0
     * @param list
     * @param heartbeatText
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/5/20 17:52
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    int updateDbEquipmentComponentProgress(List<String> list, String heartbeatText);
}
