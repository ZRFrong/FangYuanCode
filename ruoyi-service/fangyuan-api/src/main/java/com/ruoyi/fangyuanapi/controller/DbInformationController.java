package com.ruoyi.fangyuanapi.controller;

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
import com.ruoyi.system.domain.DbInformation;
import com.ruoyi.fangyuanapi.service.IDbInformationService;

import java.util.Date;

/**
 * 【农业资讯】 提供者
 * @author zheng
 * @date 2021-04-08
 */
@RestController
@Api("information")
@RequestMapping("information")
public class DbInformationController extends BaseController
{
	
	@Autowired
	private IDbInformationService dbInformationService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbInformation get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbInformationService.selectDbInformationById(id);
		
	}
	
	/**
	 * 查询【请填写功能名称】列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询【请填写功能名称】列表" , notes = "【请填写功能名称】列表")
	public R list(@ApiParam(name="DbInformation",value="传入json格式",required=true) DbInformation dbInformation)
	{
		startPage();
        return result(dbInformationService.selectDbInformationList(dbInformation));
	}

	/**
	 * 新增保存【请填写功能名称】
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存【请填写功能名称】" , notes = "新增保存【请填写功能名称】")
	public R addSave(@ApiParam(name="DbInformation",value="传入json格式",required=true) @RequestBody DbInformation dbInformation)
	{		
		return toAjax(dbInformationService.insertDbInformation(dbInformation));
	}

	/**
	 * 修改保存【请填写功能名称】
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存【请填写功能名称】" , notes = "修改保存【请填写功能名称】")
	public R editSave(@ApiParam(name="DbInformation",value="传入json格式",required=true) @RequestBody DbInformation dbInformation)
	{		
		return toAjax(dbInformationService.updateDbInformation(dbInformation));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除【请填写功能名称】" , notes = "删除【请填写功能名称】")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbInformationService.deleteDbInformationByIds(ids));
	}

	/**
	 * 方法描述
	 * @since: 1.0.0
 	 * @param title
 	 * @param content
 	 * @param cover
	 * @return: com.ruoyi.common.core.domain.R
	 * @author: ZHAOXIAOSI
	 * @date: 2021/4/12 10:49
	 * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
	 */

	@PostMapping("insertInformation")
	public R insertInformation(String title,String content,String cover	){
		int i = dbInformationService.insertDbInformation(new DbInformation().builder()
				.content(content)
				.cover(cover)
				.title(title)
				.createTime(new Date())
				.isDel(0)
				.readNum(0l)
				.build());
		return R.ok();
	}
}
