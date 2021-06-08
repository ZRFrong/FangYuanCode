package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbCropInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author zheng
 * @date 2021-06-08
 */
public interface DbCropInfoMapper {
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
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbCropInfoById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbCropInfoByIds(String[] ids);

    /**
     * 根据大棚id 获取作物信息
     *
     * @param landId
     * @since: 2.0.0
     * @return: com.ruoyi.system.domain.DbCropInfo
     * @author: ZHAOXIAOSI
     * @date: 2021/6/8 14:21
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    DbCropInfo selectDbCropInfoByTagId(@Param("landId") Long landId);
}
