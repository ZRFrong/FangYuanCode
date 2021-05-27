package com.ruoyi.system.service;

import com.ruoyi.system.domain.DbAppVersion;
import java.util.List;
import java.util.Map;

/**
 * app版本更新Service接口
 * 
 * @author zheng
 * @date 2020-10-28
 */
public interface IDbAppVersionService 
{
    /**
     * 查询app版本更新
     * 
     * @param id app版本更新ID
     * @return app版本更新
     */
    public DbAppVersion selectDbAppVersionById(Long id);

    /**
     * 查询app版本更新列表
     * 
     * @param dbAppVersion app版本更新
     * @return app版本更新集合
     */
    public List<DbAppVersion> selectDbAppVersionList(DbAppVersion dbAppVersion);

    /**
     * 新增app版本更新
     * 
     * @param dbAppVersion app版本更新
     * @return 结果
     */
    public int insertDbAppVersion(DbAppVersion dbAppVersion);

    /**
     * 修改app版本更新
     * 
     * @param dbAppVersion app版本更新
     * @return 结果
     */
    public int updateDbAppVersion(DbAppVersion dbAppVersion);

    /**
     * 批量删除app版本更新
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbAppVersionByIds(String ids);

    /**
     * 删除app版本更新信息
     * 
     * @param id app版本更新ID
     * @return 结果
     */
    public int deleteDbAppVersionById(Long id);

    DbAppVersion selectDbAppVersionByAppVersion(String apkVersion);

    /**
     * 检测APP是否需要更新接口
     * @since: 2.0.0
     * @param version
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @author: ZHAOXIAOSI
     * @date: 2021/5/5 15:45
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    Map<String,Object> appCheckUpdate(String version);
}
