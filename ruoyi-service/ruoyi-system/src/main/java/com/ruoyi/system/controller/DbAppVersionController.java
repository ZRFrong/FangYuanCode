package com.ruoyi.system.controller;

import com.ruoyi.system.domain.DbAppUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbAppVersion;
import com.ruoyi.system.service.IDbAppVersionService;

import java.util.List;

/**
 * app版本更新 提供者
 * 
 * @author zheng
 * @date 2020-10-28
 */
@RestController
@Api("appVersion")
@RequestMapping("appVersion")
public class DbAppVersionController extends BaseController
{
	
	@Autowired
	private IDbAppVersionService dbAppVersionService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbAppVersion get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbAppVersionService.selectDbAppVersionById(id);
		
	}
	
	/**
	 * 查询app版本更新列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询app版本更新列表" , notes = "app版本更新列表")
	public R list(@ApiParam(name="DbAppVersion",value="传入json格式",required=true) DbAppVersion dbAppVersion)
	{
		startPage();
        return result(dbAppVersionService.selectDbAppVersionList(dbAppVersion));
	}
	
	
	/**
	 * 新增保存app版本更新
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存app版本更新" , notes = "新增保存app版本更新")
	public R addSave(@ApiParam(name="DbAppVersion",value="传入json格式",required=true) @RequestBody DbAppVersion dbAppVersion)
	{

		return toAjax(dbAppVersionService.insertDbAppVersion(dbAppVersion));
	}

	/**
	 * 修改保存app版本更新
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存app版本更新" , notes = "修改保存app版本更新")
	public R editSave(@ApiParam(name="DbAppVersion",value="传入json格式",required=true) @RequestBody DbAppVersion dbAppVersion)
	{		
		return toAjax(dbAppVersionService.updateDbAppVersion(dbAppVersion));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除app版本更新" , notes = "删除app版本更新")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbAppVersionService.deleteDbAppVersionByIds(ids));
	}

	/*
	*检测更新调用接口
	* */
	@GetMapping("checkUpdate")
	@ApiOperation(value = "检测版本更新" , notes = "检测当前版本是否为最新")
	public R checkUpdate(@ApiParam(name="版本号",value="版本号",required=true) String versions)
	{
		DbAppVersion dbAppVersion = new DbAppVersion();
		DbAppVersion dbAppVersion1 = dbAppVersionService.selectDbAppVersionList(dbAppVersion).get(0);
		DbAppUpdate dbAppUpdate = new DbAppUpdate();
			dbAppUpdate.setNew_version(dbAppVersion1.getAppVersion());
			dbAppUpdate.setApk_file_url(dbAppVersion1.getDownloadUrl());
			dbAppUpdate.setUpdate_log(dbAppVersion1.getUpdateState());
			dbAppUpdate.setConstraint(dbAppVersion1.getIsConstraint().equals(0)?true :false );
		if (versions.equals(dbAppVersion1.getAppVersion())){
//			最新版本无需更新
			dbAppUpdate.setUpdate("No");
		}else {
			dbAppUpdate.setUpdate("Yes");

		}
		return R.data(dbAppUpdate);
	}
	

	
}
