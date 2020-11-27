package com.ruoyi.fangyuanapi.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.ZhaoQRCodeUtils;
import com.ruoyi.fangyuanapi.service.IDbCategoryService;
import com.ruoyi.fangyuanapi.service.IDbSpecParamService;
import com.ruoyi.system.domain.DbCategory;
import com.ruoyi.system.domain.DbSpecParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * db_category 提供者
 * 
 * @author zheng
 * @date 2020-09-30
 */
@RestController
@Api("提供者")
@RequestMapping("category")
public class DbCategoryController extends BaseController
{
	
	@Autowired
	private IDbCategoryService dbCategoryService;

	@Autowired
	private IDbSpecParamService dbSpecParamService;

	@GetMapping("getCategory")
	@CrossOrigin
	public R getCategory(){
		List<DbCategory> categories = dbCategoryService.selectDbCategoryAll();
		categories.forEach(e -> {
            DbSpecParam param = new DbSpecParam();
            param.setCid(e.getId());
            List<DbSpecParam> dbSpecParamList = dbSpecParamService.selectDbSpecParamList(param);
           // dbSpecParamList.stream().forEach(p -> p.setPrice(p.getPrice()*100));
            e.setSpecParams(dbSpecParamList);

		});
        R data = R.data(categories);

        return R.data(categories);
	}

	@GetMapping("getCode/{orderId}")
	@CrossOrigin
	public void getCode( @PathVariable Long orderId, HttpServletResponse response){

		// 设置响应流信息
		response.setContentType("image/jpg");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		OutputStream stream = null;

		try {
			stream = response.getOutputStream();
			//type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
			//获取一个二维码图片
			BitMatrix bitMatrix = ZhaoQRCodeUtils.createCode("99.36");
			//以流的形式输出到前端
			MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , stream);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbCategory get(  @PathVariable("id") Long id)
	{
		return dbCategoryService.selectDbCategoryById(id);
		
	}
	
	/**
	 * 查询db_category列表
	 */
	@GetMapping("list")
	public R list( DbCategory dbCategory)
	{
		startPage();
        return result(dbCategoryService.selectDbCategoryList(dbCategory));
	}
	
	
	/**
	 * 新增保存db_category
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbCategory dbCategory)
	{		
		return toAjax(dbCategoryService.insertDbCategory(dbCategory));
	}

	/**
	 * 修改保存db_category
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbCategory dbCategory)
	{		
		return toAjax(dbCategoryService.updateDbCategory(dbCategory));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{		
		return toAjax(dbCategoryService.deleteDbCategoryByIds(ids));
	}
	
}
