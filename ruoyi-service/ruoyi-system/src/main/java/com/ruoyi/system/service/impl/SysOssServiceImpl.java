package com.ruoyi.system.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.system.feign.RemoteOssService;
import com.ruoyi.system.oss.CloudStorageService;
import com.ruoyi.system.oss.OSSFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.system.domain.SysOss;
import com.ruoyi.system.mapper.SysOssMapper;
import com.ruoyi.system.service.ISysOssService;

import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 文件上传 服务层实现
 * 
 * @author zmr
 * @date 2019-05-16
 */
@Service
public class SysOssServiceImpl implements ISysOssService 
{
	@Autowired
	private SysOssMapper sysOssMapper;

	/**
     * 查询文件上传信息
     * 
     * @param id 文件上传ID
     * @return 文件上传信息
     */
    @Override
	public SysOss selectSysOssById(Long id)
	{
	    return sysOssMapper.selectByPrimaryKey(id);
	}
	
	/**
     * 查询文件上传列表
     * 
     * @param sysOss 文件上传信息
     * @return 文件上传集合
     */
	@Override
	public List<SysOss> selectSysOssList(SysOss sysOss)
	{
	    Example example = new Example(SysOss.class);
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sysOss.getFileName()))
        {
            criteria.andLike("fileName", "%" + sysOss.getFileName() + "%");
        }
        if (StringUtils.isNotBlank(sysOss.getFileSuffix()))
        {
            criteria.andEqualTo("fileSuffix", sysOss.getFileSuffix());
        }
        if (StringUtils.isNotBlank(sysOss.getCreateBy()))
        {
            criteria.andLike("createBy", sysOss.getCreateBy());
        }
        return sysOssMapper.selectByExample(example);
	}
	
    /**
     * 新增文件上传
     * 
     * @param sysOss 文件上传信息
     * @return 结果
     */
	@Override
	public int insertSysOss(SysOss sysOss)
	{
	    return sysOssMapper.insertSelective(sysOss);
	}
	
	/**
     * 修改文件上传
     * 
     * @param sysOss 文件上传信息
     * @return 结果
     */
	@Override
	public int updateSysOss(SysOss sysOss)
	{
	    return sysOssMapper.updateByPrimaryKeySelective(sysOss);
	}

	/**
     * 删除文件上传对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSysOssByIds(String ids)
	{
		return sysOssMapper.deleteByIds(ids);
	}


	@Override
	public SysOss insertFile(MultipartFile file,String createBy) {
		// 上传文件
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		CloudStorageService storage = OSSFactory.build();
		String url = null;
		try {
			url = storage.uploadSuffix(file.getBytes(), suffix);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 保存文件信息
		SysOss ossEntity = new SysOss();
		ossEntity.setUrl(url);
		ossEntity.setFileSuffix(suffix);
		ossEntity.setCreateBy(ServletUtils.getRequest().getHeader(Constants.CURRENT_USERNAME));
		ossEntity.setFileName(fileName);
		ossEntity.setCreateTime(new Date());
		ossEntity.setService(storage.getService());
		if (com.ruoyi.common.utils.StringUtils.isEmpty(createBy)){
            ossEntity.setCreateBy("admin");
        }
        ossEntity.setCreateBy(createBy);
        System.out.println(ossEntity);
        //int i = sysOssMapper.insertSelective(ossEntity);
        int i = sysOssMapper.insert(ossEntity);
        return i > 0 ? ossEntity : null;
	}

}
