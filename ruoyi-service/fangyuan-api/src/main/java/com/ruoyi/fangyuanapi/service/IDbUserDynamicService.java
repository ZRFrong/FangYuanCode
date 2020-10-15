package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbUserDynamic;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 动态Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbUserDynamicService 
{
    /**
     * 查询动态
     * 
     * @param id 动态ID
     * @return 动态
     */
    public DbUserDynamic selectDbUserDynamicById(Long id);

    /**
     * 查询动态列表
     * 
     * @param dbUserDynamic 动态
     * @return 动态集合
     */
    public List<DbUserDynamic> selectDbUserDynamicList(DbUserDynamic dbUserDynamic);

    /**
     * 新增动态
     * 
     * @param dbUserDynamic 动态
     * @return 结果
     */
    public int insertDbUserDynamic(DbUserDynamic dbUserDynamic);

    /**
     * 修改动态
     * 
     * @param dbUserDynamic 动态
     * @return 结果
     */
    public int updateDbUserDynamic(DbUserDynamic dbUserDynamic);

    /**
     * 批量删除动态
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbUserDynamicByIds(String ids);

    /**
     * 删除动态信息
     * 
     * @param id 动态ID
     * @return 结果
     */
    public int deleteDbUserDynamicById(Long id);

    String checkAndUploadFile(List<MultipartFile> file);

    /**
     * 添加动态
     * @param userId
     * @param dynamic
     * @param entryIds
     * @return
     */
    DbUserDynamic insterDynamic(String userId,DbUserDynamic dynamic, Long[] entryIds);

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    String uploadFile(MultipartFile multipartFile);


    /**
     * 查询用户所发布动态的所有图片
     * @param dynamicIds
     * @return
     */
    List<Map<String,String>> selectImagesByDynamicId(List<Long> dynamicIds,Integer currPage,Integer pageSize);

    /**
     * 根据id，和权限查询动态
     * @param dynamicId
     * @return
     */
    DbUserDynamic selectDbUserDynamicByIdAndPermission(Long dynamicId);

    /**
     * 查询推介动态
     * @param currPage
     * @param pageSize
     * @return
     */
    ArrayList<DbUserDynamic> selectDbUserDynamicOrderByCreateTime(Integer currPage, Integer pageSize);
}
