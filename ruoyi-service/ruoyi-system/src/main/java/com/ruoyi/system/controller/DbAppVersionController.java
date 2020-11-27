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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	public DbAppVersion get( @PathVariable("id") Long id)
	{
		return dbAppVersionService.selectDbAppVersionById(id);
		
	}

	@RequestMapping("downapp")
	@ApiOperation(value = "查询app版本更新列表" , notes = "app版本更新列表")
	public void alipayforward( HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String downloadUrl = dbAppVersionService.selectDbAppVersionList(new DbAppVersion()).get(0).getDownloadUrl();
        resp.sendRedirect(downloadUrl);
	}

	
	/**
	 * 查询app版本更新列表
	 */
	@GetMapping("list")
	public R list( DbAppVersion dbAppVersion)
	{
		startPage();
        return result(dbAppVersionService.selectDbAppVersionList(dbAppVersion));
	}
	
	
	/**
	 * 新增保存app版本更新
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbAppVersion dbAppVersion)
	{

		return toAjax(dbAppVersionService.insertDbAppVersion(dbAppVersion));
	}

	/**
	 * 修改保存app版本更新
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbAppVersion dbAppVersion)
	{		
		return toAjax(dbAppVersionService.updateDbAppVersion(dbAppVersion));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
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
