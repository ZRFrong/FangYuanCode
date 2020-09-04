package com.ruoyi.fangyuanapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.domain.DbLand;
import com.ruoyi.fangyuanapi.service.IDbLandService;

/**
 * 土地 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("land")
public class DbLandController extends BaseController
{
	
	@Autowired
	private IDbLandService dbLandService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{landId}")
	public DbLand get(@PathVariable("landId") Long landId)
	{
		return dbLandService.selectDbLandById(landId);
		
	}
	
	/**
	 * 查询土地列表
	 */
	@GetMapping("list")
	public R list(DbLand dbLand)
	{
		startPage();
        return result(dbLandService.selectDbLandList(dbLand));
	}
	
	
	/**
	 * 新增保存土地
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbLand dbLand)
	{		
		return toAjax(dbLandService.insertDbLand(dbLand));
	}

	/**
	 * 修改保存土地
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbLand dbLand)
	{		
		return toAjax(dbLandService.updateDbLand(dbLand));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbLandService.deleteDbLandByIds(ids));
	}
	
}
