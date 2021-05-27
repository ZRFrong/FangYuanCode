package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbAbnormalInfo;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报警信息Service接口
 * 
 * @author zheng
 * @date 2020-12-02
 */
public interface IDbAbnormalInfoService 
{
    /**
     * 查询报警信息
     * 
     * @param id 报警信息ID
     * @return 报警信息
     */
    public DbAbnormalInfo selectDbAbnormalInfoById(Long id);

    /**
     * 查询报警信息列表
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 报警信息集合
     */
    public List<DbAbnormalInfo> selectDbAbnormalInfoList(DbAbnormalInfo dbAbnormalInfo);

    /**
     * 新增报警信息
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 结果
     */
    public int insertDbAbnormalInfo(DbAbnormalInfo dbAbnormalInfo);

    /**
     * 修改报警信息
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 结果
     */
    public int updateDbAbnormalInfo(DbAbnormalInfo dbAbnormalInfo);

    /**
     * 批量删除报警信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbAbnormalInfoByIds(String ids);

    /**
     * 删除报警信息信息
     * 
     * @param id 报警信息ID
     * @return 结果
     */
    public int deleteDbAbnormalInfoById(Long id);

    List<DbAbnormalInfo> selectAbnormalList(DbAbnormalInfo dbAbnormalInfo);

    /**
     * 查询预警信息
     * @since: 2.0.0
     * @param list 大棚id数组
     * @param date 时间查询
     * @param userId 时间查询
     * @param currPage 页码
     * @param pageSize 条数
     * @return: List<Map<String,String>>
     * @author: ZHAOXIAOSI
     * @date: 2021/5/9 13:33
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<Map<String,String>> selectAbnormals(List<Long> list, Long userId,Date date, Long currPage, Long pageSize);
}
