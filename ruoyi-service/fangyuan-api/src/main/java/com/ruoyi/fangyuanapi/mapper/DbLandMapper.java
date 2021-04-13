package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbLand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 土地Mapper接口
 *
 * @author zheng
 * @date 2020-09-24
 */
public interface DbLandMapper
{
    /**
     * 查询土地
     *
     * @param landId 土地ID
     * @return 土地
     */
    public DbLand selectDbLandById(Long landId);

    /**
     * 查询土地列表
     *
     * @param dbLand 土地
     * @return 土地集合
     */
    public List<DbLand> selectDbLandList(DbLand dbLand);

    /**
     * 新增土地
     *
     * @param dbLand 土地
     * @return 结果
     */
    public int insertDbLand(DbLand dbLand);

    /**
     * 修改土地
     *
     * @param dbLand 土地
     * @return 结果
     */
    public int updateDbLand(DbLand dbLand);

    /**
     * 删除土地
     *
     * @param landId 土地ID
     * @return 结果
     */
    public int deleteDbLandById(Long landId);

    /**
     * 批量删除土地
     *
     * @param landIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbLandByIds(String[] landIds);

    List<DbLand> selectDbLandByUserId(@Param("dbUserId") Long dbUserId,@Param("siteId") Long siteId);

    /**
     * 查詢用戶地的數量
     * @param dbUserId
     * @return
     */
    Integer selectDbLandCountByUserId(Long dbUserId);

    @Select("select db_user_id from db_land d where db_user_id!=0   GROUP BY db_user_id")
    List<Long> groupByUserId();

    List<DbLand> selectDbLandNoSiteList(DbLand dbLand);

    List<DbLand> selectDbLandWeChatList(DbLand dbLand);

    Integer selectDbLandBySiteId(Long landId);

    List<Map<String,Object>> selectDbLandByUserIdAndSideId(@Param("userId") Long userId,@Param("sideId") Long sideId);

    /**
     * 根据UserId查询其所属土地
     * @since: 1.0.0
     * @param userId
     * @return: java.util.List<com.ruoyi.system.domain.DbLand>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/13 9:45
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<DbLand> selectDbLandsByUserId(Long userId);
}
