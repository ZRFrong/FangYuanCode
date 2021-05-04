package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.PhoneUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbPutQuestions;
import com.ruoyi.fangyuanapi.service.IDbPutQuestionsService;

/**
 * 提问 提供者
 * 
 * @author zheng
 * @date 2021-01-18
 */
@RestController
@Api("questions")
@RequestMapping("questions")
public class DbPutQuestionsController extends BaseController
{
	
	@Autowired
	private IDbPutQuestionsService dbPutQuestionsService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbPutQuestions get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbPutQuestionsService.selectDbPutQuestionsById(id);
		
	}
	
	/**
	 * 查询列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询提问列表" , notes = "提问列表")
	public R list(@ApiParam(name="DbPutQuestions",value="传入json格式",required=true) DbPutQuestions dbPutQuestions)
	{
		startPage();
        return result(dbPutQuestionsService.selectDbPutQuestionsList(dbPutQuestions));
	}
	
	
	/**
	 * 新增保存提问
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存提问" , notes = "新增保存提问")
	public R addSave(@ApiParam(name="DbPutQuestions",value="传入json格式",required=true) @RequestBody DbPutQuestions dbPutQuestions)
	{		
		return toAjax(dbPutQuestionsService.insertDbPutQuestions(dbPutQuestions));
	}

	/**
	 * 修改保存提问
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存提问" , notes = "修改保存提问")
	public R editSave(@ApiParam(name="DbPutQuestions",value="传入json格式",required=true) @RequestBody DbPutQuestions dbPutQuestions)
	{		
		return toAjax(dbPutQuestionsService.updateDbPutQuestions(dbPutQuestions));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除提问" , notes = "删除提问")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbPutQuestionsService.deleteDbPutQuestionsByIds(ids));
	}

	@PostMapping("insertQuestions")
	@ApiOperation(value = "提交反馈" , notes = "提交反馈" , httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="problemTypeId",value = "反馈类型id" ,required = true),
            @ApiImplicitParam(name="questionsText",value = "反馈描述" ,required = true),
            @ApiImplicitParam(name="images",value = "反馈图片" ,required = false),
            @ApiImplicitParam(name="phone",value = "手机号" ,required = true),
            @ApiImplicitParam(name="landAddress",value = "土地位置" ,required = true),
    })
	public R insertQuestions(Long problemTypeId,String questionsText,String images,String phone,String landAddress ){

	    if(StringUtils.isEmpty(phone) ||  !PhoneUtils.checkPhone(phone)){
	        return R.error(ResultEnum.PHONE_ERROR_OR_NULL.getCode(),ResultEnum.PHONE_ERROR_OR_NULL.getMessage());
        }
        DbPutQuestions questions = new DbPutQuestions();
        questions.setProblemTypeId(problemTypeId);
        questions.setQuestionsText(questionsText);
        if (StringUtils.isNotEmpty(images)){
            questions.setImages(images);
        }

        questions.setPhone(phone);
        questions.setLandText(landAddress);
        int i = dbPutQuestionsService.insertDbPutQuestions(questions);
        return i>0 ? R.ok():R.error(ResultEnum.SUBMIT_ERROR.getCode(),ResultEnum.SUBMIT_ERROR.getMessage());
	}

}
