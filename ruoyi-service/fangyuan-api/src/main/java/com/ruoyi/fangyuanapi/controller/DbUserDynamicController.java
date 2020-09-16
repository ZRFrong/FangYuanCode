package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sensitivewdfilter.WordFilter;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.service.DbDynamicService;
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
import com.ruoyi.fangyuanapi.domain.DbUserDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 动态 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("dynamic1")
@RequestMapping("dynamic1")
public class DbUserDynamicController extends BaseController
{

	@Autowired
	private IDbUserDynamicService dbUserDynamicService;

	@Autowired
	private DbDynamicService dbDynamicService;

	/**
	 *
	 * @param request 用来获取heard头里的userid
	 * @param text 动态发布的内容
	 * @param file 资源数组： 图片可有六个 视频一个
	 * @param authority 权限：谁可见
	 * @param entryIds 词条数组
	 * @param site 发表动态时的位置
	 * @return
	 */
	@PostMapping
	public R insterDynamic(HttpServletRequest request, String text, List<MultipartFile> file, Integer authority, List<Long> entryIds, String site){
		String userId = request.getHeader("");
		if (StringUtils.isNotEmpty(text)&& file !=null && file.size()>0 && file.size() <= 6){
			if (WordFilter.isContains(text)){
				return R.error(ResultEnum.TEXT_ILLEGAL.getCode(),ResultEnum.TEXT_ILLEGAL.getMessage());
			}
			String url =  dbDynamicService.checkAndUploadFile(file);
			if (StringUtils.isEmpty(url)){
				return R.error(ResultEnum.IMAGES_AND_VIDOE_ILLEGAL.getCode(),ResultEnum.IMAGES_AND_VIDOE_ILLEGAL.getMessage());
			}

		}
		return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());

	}


	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbUserDynamic get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbUserDynamicService.selectDbUserDynamicById(id);

	}

	/**
	 * 查询动态列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询动态列表" , notes = "动态列表")
	public R list(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) DbUserDynamic dbUserDynamic)
	{
		startPage();
		return result(dbUserDynamicService.selectDbUserDynamicList(dbUserDynamic));
	}


	/**
	 * 新增保存动态
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存动态" , notes = "新增保存动态")
	public R addSave(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) @RequestBody DbUserDynamic dbUserDynamic)
	{
		return toAjax(dbUserDynamicService.insertDbUserDynamic(dbUserDynamic));
	}

	/**
	 * 修改保存动态
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存动态" , notes = "修改保存动态")
	public R editSave(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) @RequestBody DbUserDynamic dbUserDynamic)
	{
		return toAjax(dbUserDynamicService.updateDbUserDynamic(dbUserDynamic));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除动态" , notes = "删除动态")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbUserDynamicService.deleteDbUserDynamicByIds(ids));
	}

}