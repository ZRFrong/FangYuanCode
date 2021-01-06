package com.ruoyi.system.controller;

import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.system.domain.DbAppUpdate;
import com.ruoyi.system.domain.SysOss;
import com.ruoyi.system.service.ISysOssService;
import io.swagger.annotations.*;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbAppVersion;
import com.ruoyi.system.service.IDbAppVersionService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

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

    @Autowired
    private ISysOssService sysOssService;


	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbAppVersion get( @PathVariable("id") Long id)
	{
		return dbAppVersionService.selectDbAppVersionById(id);
		
	}

	@GetMapping("downapp")
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
    @Transactional
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
	public R checkUpdate(@ApiParam(name="versions",value="版本号",required=true) String versions)
	{
		DbAppVersion dbAppVersion = new DbAppVersion();
		DbAppVersion dbAppVersion1 = dbAppVersionService.selectDbAppVersionList(dbAppVersion).get(0);
		DbAppUpdate dbAppUpdate = new DbAppUpdate();
			dbAppUpdate.setNew_version(dbAppVersion1.getAppVersion());
			dbAppUpdate.setApk_file_url(dbAppVersion1.getDownloadUrl());
			dbAppUpdate.setUpdate_log(dbAppVersion1.getUpdateState());
			dbAppUpdate.setConstraint(dbAppVersion1.getIsConstraint().equals(0)?true :false );
		if (Integer.parseInt(versions)>=Integer.parseInt(dbAppVersion1.getAppVersion())){
//			最新版本无需更新
			dbAppUpdate.setUpdate("No");
		}else {
			dbAppUpdate.setUpdate("Yes");

		}
		return R.data(dbAppUpdate);
	}


	@PostMapping("apkUpload")
    @ApiOperation(value = "上传APK",notes = "上传APK，同一版本号禁止重复上传！",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",value = "APK文件：",required = true),
            @ApiImplicitParam(name = "updateUserName",value = "版本更新人名字：",required = true),
            @ApiImplicitParam(name = "isConstraint",value = "是否强制更新：0:否 1:是",required = true),
            @ApiImplicitParam(name = "updateState",value = "版本更新特性说明!",required = true),
    })
	public R apkUpload(@RequestPart("file")MultipartFile file, @RequestParam(name = "updateUserName") String updateUserName,@RequestParam(name = "isConstraint") Integer isConstraint, @RequestParam(name = "updateState")String updateState) {
		if (file.isEmpty()){
			return R.error();
		}
		String apkVersion ;
        try {
            File fileTmp = new File(file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(),fileTmp);
            DbAppVersion version = new DbAppVersion();
            ApkFile apkFile = new ApkFile(fileTmp);
            ApkMeta apkMeta = apkFile.getApkMeta();
            apkVersion = apkMeta.getVersionCode()+"";
            DbAppVersion dbAppVersion = dbAppVersionService.selectDbAppVersionByAppVersion(apkVersion);
            if (dbAppVersion != null){
                return R.error(ResultEnum.APK_EXIST.getCode(),ResultEnum.APK_EXIST.getMessage());
            }
            version.setAppVersion(apkVersion);
            version.setUpdateState(updateState);
            version.setIsConstraint(isConstraint);
            version.setUpdateBy(updateUserName);
            version.setUpdateTime(new Date());
            SysOss sysOss = sysOssService.insertFile(file, updateUserName);
            String url = sysOss.getUrl();
            if (url != null){
                version.setDownloadUrl(url+"");
                int i = dbAppVersionService.insertDbAppVersion(version);
                return i>0?R.ok():R.error();
            }else {
                return R.error("上传失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.error();
	}
}
