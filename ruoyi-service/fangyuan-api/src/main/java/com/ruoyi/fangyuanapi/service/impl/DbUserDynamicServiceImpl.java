package com.ruoyi.fangyuanapi.service.impl;

import java.io.IOException;
import java.util.*;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.conf.QiniuUtils;
import com.ruoyi.system.domain.DbDynamicAndEntry;
import com.ruoyi.system.domain.DbUser;
import com.ruoyi.system.domain.DbUserAndDynamic;
import com.ruoyi.fangyuanapi.mapper.DbDynamicAndEntryMapper;
import com.ruoyi.fangyuanapi.mapper.DbUserAndDynamicMapper;
import com.ruoyi.fangyuanapi.mapper.DbUserMapper;
import com.ruoyi.system.feign.RemoteOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbUserDynamicMapper;
import com.ruoyi.system.domain.DbUserDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 动态Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
@Slf4j
public class DbUserDynamicServiceImpl implements IDbUserDynamicService 
{
    @Autowired
    private DbUserDynamicMapper dbUserDynamicMapper;

    private String image = ".jpg.png.jpeg.bmp.webp.tif.gif";

    private String video = ".mp4.flv.mov.avi.wmv.ts.mpg";


    @Autowired
    private DbDynamicAndEntryMapper dbDynamicAndEntryMapper;

    @Autowired
    private DbUserAndDynamicMapper dbUserAndDynamicMapper;

    @Autowired
    private DbUserMapper dbUserMapper;

    @Autowired
    private RemoteOssService build;

    @Autowired
    private QiniuUtils qiniuUtils;

    /**
     * 查询动态
     * 
     * @param id 动态ID
     * @return 动态
     */
    @Override
    public DbUserDynamic selectDbUserDynamicById(Long id)
    {
        return dbUserDynamicMapper.selectDbUserDynamicById(id);
    }

    /**
     * 查询动态列表
     * 
     * @param dbUserDynamic 动态
     * @return 动态
     */
    @Override
    public List<DbUserDynamic> selectDbUserDynamicList(DbUserDynamic dbUserDynamic)
    {
        return dbUserDynamicMapper.selectDbUserDynamicList(dbUserDynamic);
    }

    /**
     * 新增动态
     * 
     * @param dbUserDynamic 动态
     * @return 结果
     */
    @Override
    public int insertDbUserDynamic(DbUserDynamic dbUserDynamic)
    {
        return dbUserDynamicMapper.insertDbUserDynamic(dbUserDynamic);
    }

    /**
     * 修改动态
     * 
     * @param dbUserDynamic 动态
     * @return 结果
     */
    @Override
    public int updateDbUserDynamic(DbUserDynamic dbUserDynamic)
    {
        return dbUserDynamicMapper.updateDbUserDynamic(dbUserDynamic);
    }

    /**
     * 删除动态对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbUserDynamicByIds(String ids)
    {
        return dbUserDynamicMapper.deleteDbUserDynamicByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除动态信息
     * 
     * @param id 动态ID
     * @return 结果
     */
    @Override
    public int deleteDbUserDynamicById(Long id)
    {
        return dbUserDynamicMapper.deleteDbUserDynamicById(id);
    }

    @Override
    public String checkAndUploadFile(List<MultipartFile> file) {
        String url =null;
        List <String> res =  new ArrayList<>();
        try {
            for (MultipartFile multipartFile : file) {
                byte[] bytes = multipartFile.getBytes();
                String name = multipartFile.getOriginalFilename();//获取文件名
                String s = name.substring(name.lastIndexOf("."));//文件后缀名字
                String s1 = UUID.randomUUID().toString().replaceAll("-", "");
                R r = build.editSave(multipartFile);
                url = (String) r.get("data");
                if (StringUtils.isEmpty(url)){
                    return null;
                }
                if (image.contains(s)){//调用图片审核
                    res.add(url);
                    String result = qiniuUtils.checkImage(url);
                    if (StringUtils.isNotEmpty(result)){
                        return result;
                    }
                }
                if (video.contains(s)){//调用视频审核
                    res.add(url);
                    String result = qiniuUtils.videoCheck(url);//返回任务id
                    if (StringUtils.isNotEmpty(result)){
                        //立即返回结果 开启线程去查询任务结果

                        return result;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传出错了");
        }
        JSON json = JSONUtil.parse(res);//json数组
        return json.toString();
    }



    @Override
    @Transactional
    public DbUserDynamic insterDynamic(String userId,DbUserDynamic dynamic, Long[] entryIds) {
        dynamic.setCreatedTime(new Date());
        int i = dbUserDynamicMapper.insertDbUserDynamic(dynamic);
        if (entryIds.length >0){
            for (Long entryId : entryIds) {
                DbDynamicAndEntry dynamicAndEntry = new DbDynamicAndEntry();
                dynamicAndEntry.setEntryId(entryId);
                dynamicAndEntry.setDynamicId(dynamic.getId());
                dbDynamicAndEntryMapper.insertDbDynamicAndEntry(dynamicAndEntry);
            }
        }
        //todo
        //插入用户和动态和中间表，修改用户动态数量
        DbUserAndDynamic dbUserAndDynamic = new DbUserAndDynamic();
        dbUserAndDynamic.setUserId(Long.valueOf(userId));
        dbUserAndDynamic.setDynamicId(dynamic.getId());
        dbUserAndDynamicMapper.insertDbUserAndDynamic(dbUserAndDynamic);
        DbUser user = dbUserMapper.selectDbUserById(Long.valueOf(userId));
        user.setDynamicNum(user.getDynamicNum()+1);
        dbUserMapper.updateDbUser(user);
        return dynamic;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String name = StringUtils.getUUIDFileName(multipartFile.getOriginalFilename());
        String upload = null;
        R r = build.editSave(multipartFile);
        return upload;
    }



    /**
     * 查询用户所发布动态的图片
     * @param dynamicIds
     * @return
     */
    @Override
    public List<Map<String,String>> selectImagesByDynamicId(List<Long> dynamicIds,Integer currPage,Integer pageSize) {
        ArrayList<Map<String,String>> result = new ArrayList<>();
        for (Long id : dynamicIds) {
           Map<String,String> map = dbUserDynamicMapper.selectDynamicCreatedAndResourcesByid(id,currPage,pageSize);

           result.add(map);
        }
        return result;
    }

    /**
     * 根据id 和权限 查询用户动态
     * @param dynamicId
     * @return
     */
    @Override
    public DbUserDynamic selectDbUserDynamicByIdAndPermission(Long dynamicId){
        return dbUserDynamicMapper.selectDbUserDynamicByIdAndPermission(dynamicId);
    }

    @Override
    public ArrayList<DbUserDynamic> selectDbUserDynamicOrderByCreateTime(Integer currPage, Integer pageSize) {
        ArrayList<DbUserDynamic> list =  dbUserDynamicMapper.selectDbUserDynamicOrderByCreateTime(currPage,pageSize);
        return list ;
    }

    @Override
    public ArrayList<DbUserDynamic> selectDynamicList(int start, int end) {
        return dbUserDynamicMapper.selectDynamicList(start,end) ;
    }

    @Override
    public List<DbUserDynamic> searchDynamic(String word) {
        return dbUserDynamicMapper.searchDynamic(word);
    }
}
