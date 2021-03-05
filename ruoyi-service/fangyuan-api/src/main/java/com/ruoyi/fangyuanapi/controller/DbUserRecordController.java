package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
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
import com.ruoyi.system.domain.DbUserRecord;
import com.ruoyi.fangyuanapi.service.IDbUserRecordService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;


/**
 * 【请填写功能名称】 提供者
 * @author zheng
 * @date 2021-03-04
 */
@RestController
@Api("record")
@RequestMapping("record")
public class DbUserRecordController extends BaseController
{
	
	@Autowired
	private IDbUserRecordService dbUserRecordService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbUserRecord get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbUserRecordService.selectDbUserRecordById(id);
		
	}
	
	/**
	 * 查询【请填写功能名称】列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询【请填写功能名称】列表" , notes = "【请填写功能名称】列表")
	public R list(@ApiParam(name="DbUserRecord",value="传入json格式",required=true) DbUserRecord dbUserRecord)
	{
		startPage();
        return result(dbUserRecordService.selectDbUserRecordList(dbUserRecord));
	}
	
	
	/**
	 * 新增保存【请填写功能名称】
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存【请填写功能名称】" , notes = "新增保存【请填写功能名称】")
	public R addSave(@ApiParam(name="DbUserRecord",value="传入json格式",required=true) @RequestBody DbUserRecord dbUserRecord)
	{
		String s = getRequest().getHeader(Constants.CURRENT_ID);
		dbUserRecord.setDbUserId(Long.valueOf(s));
		dbUserRecord.setCreateTime(new Date());
		return toAjax(dbUserRecordService.insertDbUserRecord(dbUserRecord));
	}

	/**
	 * 修改保存【请填写功能名称】
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存【请填写功能名称】" , notes = "修改保存【请填写功能名称】")
	public R editSave(@ApiParam(name="DbUserRecord",value="传入json格式",required=true) @RequestBody DbUserRecord dbUserRecord)
	{		
		return toAjax(dbUserRecordService.updateDbUserRecord(dbUserRecord));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除【请填写功能名称】" , notes = "删除【请填写功能名称】")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbUserRecordService.deleteDbUserRecordByIds(ids));
	}

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://apistore.baidu.com/microservice/iplookup?ip=106.6.104.72");
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		String line =null;
		while(null != (line = br.readLine()))
		{
			buffer.append(line);
		}
		br.close();
		isr.close();
		is.close();
		System.out.println(buffer.toString());

//		JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
//		System.out.println(jsonObject);
//		JSONObject jsonObject1 = JSONObject.fromObject(jsonObject.get("retData"));
//		System.out.println(jsonObject1.get("province").toString()+jsonObject1.get("city").toString()+ "(" +jsonObject1.get("carrier").toString() + ")");
	}

	
}
