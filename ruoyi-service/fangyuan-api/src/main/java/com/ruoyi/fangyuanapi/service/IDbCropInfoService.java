package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbCropInfo;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zheng
 * @date 2021-06-08
 */
public interface IDbCropInfoService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DbCropInfo selectDbCropInfoById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbCropInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DbCropInfo> selectDbCropInfoList(DbCropInfo dbCropInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbCropInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertDbCropInfo(DbCropInfo dbCropInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbCropInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateDbCropInfo(DbCropInfo dbCropInfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbCropInfoByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbCropInfoById(Long id);

    /***
     * 根据大棚id获取作物信息
     * @since: 2.0.0
     * @param landId
     * @return: com.ruoyi.system.domain.DbCropInfo
     * @author: ZHAOXIAOSI
     * @date: 2021/6/8 14:18
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    DbCropInfo selectDbCropInfoByTagId(Long landId);

    List<Map<String,Object>> selectList();
}
