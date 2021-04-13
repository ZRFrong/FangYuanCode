package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbInformation;
import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zheng
 * @date 2021-04-08
 */
public interface DbInformationMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DbInformation selectDbInformationById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbInformation 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DbInformation> selectDbInformationList(DbInformation dbInformation);

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbInformation 【请填写功能名称】
     * @return 结果
     */
    public int insertDbInformation(DbInformation dbInformation);

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbInformation 【请填写功能名称】
     * @return 结果
     */
    public int updateDbInformation(DbInformation dbInformation);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbInformationById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbInformationByIds(String[] ids);
}
