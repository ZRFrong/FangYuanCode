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
import com.ruoyi.fangyuanapi.domain.DbEntry;
import com.ruoyi.fangyuanapi.service.IDbEntryService;

/**
 * 词条 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("entry")
public class DbEntryController extends BaseController
{
	
	@Autowired
	private IDbEntryService dbEntryService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbEntry get(@PathVariable("id") Long id)
	{
		return dbEntryService.selectDbEntryById(id);
		
	}
	
	/**
	 * 查询词条列表
	 */
	@GetMapping("list")
	public R list(DbEntry dbEntry)
	{
		startPage();
        return result(dbEntryService.selectDbEntryList(dbEntry));
	}
	
	
	/**
	 * 新增保存词条
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbEntry dbEntry)
	{		
		return toAjax(dbEntryService.insertDbEntry(dbEntry));
	}

	/**
	 * 修改保存词条
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbEntry dbEntry)
	{		
		return toAjax(dbEntryService.updateDbEntry(dbEntry));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbEntryService.deleteDbEntryByIds(ids));
	}
	
}
