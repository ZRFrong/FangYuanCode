package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbAbnormalInfo;
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
import com.ruoyi.fangyuanapi.service.IDbAbnormalInfoService;

/**
 * 报警信息 提供者
 * 
 * @author zheng
 * @date 2020-12-02
 */
@RestController
@Api("abnormalInfo")
@RequestMapping("abnormalInfo")
public class DbAbnormalInfoController extends BaseController
{
	
	@Autowired
	private IDbAbnormalInfoService dbAbnormalInfoService;


	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbAbnormalInfo get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbAbnormalInfoService.selectDbAbnormalInfoById(id);
		
	}
	
	/**
	 * 查询报警信息列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询报警信息列表" , notes = "报警信息列表")
	public R list(@ApiParam(name="DbAbnormalInfo",value="传入json格式",required=true) DbAbnormalInfo dbAbnormalInfo)
	{
		startPage();
        return result(dbAbnormalInfoService.selectDbAbnormalInfoList(dbAbnormalInfo));
	}
	
	
	/**
	 * 新增保存报警信息
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存报警信息" , notes = "新增保存报警信息")
	public R addSave(@ApiParam(name="DbAbnormalInfo",value="传入json格式",required=true) @RequestBody DbAbnormalInfo dbAbnormalInfo)
	{		
		return toAjax(dbAbnormalInfoService.insertDbAbnormalInfo(dbAbnormalInfo));
	}

	/**
	 * 修改保存报警信息
	 */
	@PostMapping("update")
	public R editSave(@ApiParam(name="DbAbnormalInfo",value="传入json格式",required=true) @RequestBody DbAbnormalInfo dbAbnormalInfo)
	{		
		return toAjax(dbAbnormalInfoService.updateDbAbnormalInfo(dbAbnormalInfo));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbAbnormalInfoService.deleteDbAbnormalInfoByIds(ids));
	}


	
}
