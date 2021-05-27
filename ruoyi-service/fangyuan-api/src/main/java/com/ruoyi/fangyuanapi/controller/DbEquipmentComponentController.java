package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbEquipmentComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.service.IDbEquipmentComponentService;

import java.util.List;

/**
 * 版本加功能 提供者
 * 
 * @author zheng
 * @date 2021-04-08
 */
@RestController
@Api("component")
@RequestMapping("component")
public class DbEquipmentComponentController extends BaseController
{
	
	@Autowired
	private IDbEquipmentComponentService dbEquipmentComponentService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbEquipmentComponent get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbEquipmentComponentService.selectDbEquipmentComponentById(id);
		
	}
	
	/**
	 * 查询版本加功能列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询版本加功能列表" , notes = "版本加功能列表")
	public R list(@ApiParam(name="DbEquipmentComponent",value="传入json格式",required=true) DbEquipmentComponent dbEquipmentComponent)
	{
		startPage();
        return result(dbEquipmentComponentService.selectDbEquipmentComponentList(dbEquipmentComponent));
	}
	
	
	/**
	 * 新增保存版本加功能
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存版本加功能" , notes = "新增保存版本加功能")
	public R addSave(@ApiParam(name="DbEquipmentComponent",value="传入json格式",required=true) @RequestBody DbEquipmentComponent dbEquipmentComponent)
	{		
		return toAjax(dbEquipmentComponentService.insertDbEquipmentComponent(dbEquipmentComponent));
	}

	/**
	 * 修改保存版本加功能
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存版本加功能" , notes = "修改保存版本加功能")
	public R editSave(@ApiParam(name="DbEquipmentComponent",value="传入json格式",required=true) @RequestBody DbEquipmentComponent dbEquipmentComponent)
	{		
		return toAjax(dbEquipmentComponentService.updateDbEquipmentComponent(dbEquipmentComponent));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除版本加功能" , notes = "删除版本加功能")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbEquipmentComponentService.deleteDbEquipmentComponentByIds(ids));
	}
	/**
	 * 查询版本加功能列表
	 */
	@GetMapping("parentFunctionList")
	@ApiOperation(value = "查询父级功能列表" , notes = "查询父级功能列表")
	public R parentFunctionList()
	{
        DbEquipmentComponent dbEquipmentComponent = new DbEquipmentComponent();
        dbEquipmentComponent.setClassificationId(0L);
        return result(dbEquipmentComponentService.selectDbEquipmentComponentList(dbEquipmentComponent));
	}
    /**
     * 查询版本加功能列表
     */
    @GetMapping("childFunctionList")
    @ApiOperation(value = "查询子级功能列表" , notes = "查询子级功能列表")
    public R childFunctionList(@ApiParam(name="DbEquipmentComponent",value="传入json格式",required=true) DbEquipmentComponent dbEquipmentComponent)
    {
        return result(dbEquipmentComponentService.selectDbEquipmentComponentList(dbEquipmentComponent));
    }

    /**
     * 修改补光的开关状态
     * @since: 2.0.0
 	 * @param heartbeatText
 	 * @param switchState
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/20 17:38
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
	@PutMapping("modifyLightStatus")
	public R modifyLightStatus(String heartbeatText,String switchState,Integer fillLightTimingStatus){
		return dbEquipmentComponentService.updateLightStatus(heartbeatText,switchState,fillLightTimingStatus);
	}

	/**
	 * 修改功能的开关状态
	 * @since: 2.0.0
	 * @param heartbeatText
	 * @param switchState
	 * @param  functionLogo 功能标识
	 * @return: com.ruoyi.common.core.domain.R
	 * @author: ZHAOXIAOSI
	 * @date: 2021/5/20 17:38
	 * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
	 */
	@PutMapping("modifyFunctionStatus")
	public R modifyFunctionLogoStatus(String heartbeatText,String functionLogo,String switchState,Integer fillLightTimingStatus){
		return dbEquipmentComponentService.updatFunctionLogo(heartbeatText,functionLogo,switchState,fillLightTimingStatus);
	}

	/**
	 * 卷帘卷膜进度解析
	 * @since: 2.0.0
 	 * @param list
 	 * @param heartbeatText
	 * @return: com.ruoyi.common.core.domain.R
	 * @author: ZHAOXIAOSI
	 * @date: 2021/5/20 17:44
	 * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
	 */
	@PutMapping("heartbeatText")
	public R progressAnalysis(@RequestParam("list") List<String> list,@RequestParam("heartbeatText")String heartbeatText){
		int i = dbEquipmentComponentService.updateDbEquipmentComponentProgress(list, heartbeatText);
		return i>0 ? R.ok() : R.error();
	}
}
