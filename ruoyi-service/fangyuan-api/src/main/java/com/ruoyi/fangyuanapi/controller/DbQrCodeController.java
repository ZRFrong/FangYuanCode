package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbQrCode;
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
import com.ruoyi.fangyuanapi.service.IDbQrCodeService;

/**
 * 二维码 提供者
 * 
 * @author zheng
 * @date 2020-09-30
 */
@RestController
@Api("qrcode")
@RequestMapping("qrcode")
public class DbQrCodeController extends BaseController
{
	
	@Autowired
	private IDbQrCodeService dbQrCodeService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{qrCodeId}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbQrCode get(@ApiParam(name="id",value="long",required=true)  @PathVariable("qrCodeId") Long qrCodeId)
	{
		return dbQrCodeService.selectDbQrCodeById(qrCodeId);
		
	}
	
	/**
	 * 查询二维码列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询二维码列表" , notes = "二维码列表")
	public R list(@ApiParam(name="DbQrCode",value="传入json格式",required=true) DbQrCode dbQrCode)
	{
		startPage();
        return result(dbQrCodeService.selectDbQrCodeList(dbQrCode));
	}

	
	
	/**
	 * 新增保存二维码
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存二维码" , notes = "新增保存二维码")
	public R addSave(@ApiParam(name="DbQrCode",value="传入json格式",required=true) @RequestBody DbQrCode dbQrCode)
	{		
		return toAjax(dbQrCodeService.insertDbQrCode(dbQrCode));
	}

	/**
	 * 修改保存二维码
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存二维码" , notes = "修改保存二维码")
	public R editSave(@ApiParam(name="DbQrCode",value="传入json格式",required=true) @RequestBody DbQrCode dbQrCode)
	{		
		return toAjax(dbQrCodeService.updateDbQrCode(dbQrCode));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除二维码" , notes = "删除二维码")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbQrCodeService.deleteDbQrCodeByIds(ids));
	}
	
}
