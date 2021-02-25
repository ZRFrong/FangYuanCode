package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbProblem;
import com.ruoyi.fangyuanapi.service.IDbProblemService;

import java.util.List;
import java.util.Map;

/**
 * 问题 提供者
 * 
 * @author zheng
 * @date 2021-01-18
 */
@RestController
@Api("problem")
@RequestMapping("problem")
public class DbProblemController extends BaseController
{
	
	@Autowired
	private IDbProblemService dbProblemService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询问题" , notes = "查询问题", httpMethod = "POST")
	public R get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return R.data(dbProblemService.selectDbProblemById(id));
	}
	
	/**
	 * 查询问题列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询问题列表" , notes = "问题列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum",value = "页码" ,required = true),
			@ApiImplicitParam(name = "pageSize",value = "条数" ,required = true)
	})
	public R list(@RequestParam(name = "pageNum") Integer currPage, @RequestParam(name = "pageSize") Integer pageSize)
	{
		//时间
		if (pageSize == null || pageSize > 10 || pageSize <= 0){
			pageSize = PageConf.pageSize;
		}
		currPage = currPage == null || currPage <= 0  ? 0 :(currPage -1) * pageSize;
		List<DbProblem> list = dbProblemService.selectDbProblem(currPage,pageSize);
		return R.rows(list);
	}
	
	
	/**
	 * 新增保存问题
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存问题" , notes = "新增保存问题" ,httpMethod = "POST")
	public R addSave(@ApiParam(name="DbProblem",value="传入json格式",required=true) @RequestBody DbProblem dbProblem)
	{		
		return toAjax(dbProblemService.insertDbProblem(dbProblem));
	}

	/**
	 * 修改保存问题
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存问题" , notes = "修改保存问题" ,httpMethod = "POST")
	public R editSave(@ApiParam(name="DbProblem",value="传入json格式",required=true) @RequestBody DbProblem dbProblem)
	{		
		return toAjax(dbProblemService.updateDbProblem(dbProblem));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除" , notes = "删除问题" , httpMethod = "POST")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbProblemService.deleteDbProblemByIds(ids));
	}

	@GetMapping("getProblem")
	@ApiOperation(value = "获取问题列表" , notes = "获取问题列表" , httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "problemType",value = "问题类型id：如果不传按照问题访问量由高到低展示",required = false),
			@ApiImplicitParam(name = "currPage",value = "当前页码：如果不传默认页码为1",required = false),
			@ApiImplicitParam(name = "pageSize",value = "条数：如果不传默认页码为10",required = false)
	})
    public R getProblemListByType(Integer problemType,Integer currPage,Integer pageSize){
		if (pageSize == null || pageSize > 10 || pageSize <= 0){
			pageSize = PageConf.pageSize;
		}
		currPage = currPage == null || currPage <= 0  ? 0 :(currPage -1) * pageSize;
		List<Map<String,Object>> result = dbProblemService.getProblemListByType(problemType,currPage,pageSize);
		return R.data(result);
    }

}
