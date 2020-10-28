package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.utils.PassDemo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sensitivewdfilter.WordFilter;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbComment;
import com.ruoyi.fangyuanapi.service.IDbCommentService;

import java.util.List;
import java.util.Map;

/**
 * 评论 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("comment")
@RequestMapping("comment")
public class DbCommentController extends BaseController
{

	@Autowired
	private IDbCommentService dbCommentService;

	@Autowired
	private IDbUserDynamicService dynamicService;



	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbComment get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbCommentService.selectDbCommentById(id);
	}

	/**
	 * 查询评论列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询评论列表" , notes = "评论列表")
	public R list(@ApiParam(name="DbComment",value="传入json格式",required=true) DbComment dbComment)
	{
		startPage();
		return result(dbCommentService.selectDbCommentList(dbComment));
	}


	/**
	 * 新增保存评论
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存评论" , notes = "新增保存评论")
	public R addSave(@ApiParam(name="DbComment",value="传入json格式",required=true) @RequestBody DbComment dbComment)
	{
		return toAjax(dbCommentService.insertDbComment(dbComment));
	}

	/**
	 * 修改保存评论
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存评论" , notes = "修改保存评论")
	public R editSave(@ApiParam(name="DbComment",value="传入json格式",required=true) @RequestBody DbComment dbComment)
	{
		return toAjax(dbCommentService.updateDbComment(dbComment));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除评论" , notes = "删除评论")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbCommentService.deleteDbCommentByIds(ids));
	}

	/**
	 * 评论查询接口
	 * @param dynamicId
	 * @param currPage
	 * @return
	 */
	@GetMapping("getCommentList/{dynamicId}/{currPage}")
	public R getCommentList(@PathVariable Long dynamicId,@PathVariable Integer currPage){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        if (StringUtils.isNotEmpty(userId)){
            userId = PassDemo.decode(userId)+"";
        }
        System.out.println(userId);
        List<Map<String, Object>> list = dbCommentService.getCommentList(dynamicId, userId,currPage);

        return R.data(list);
	}

    /**
     * 二级评论接口
     * @param commentId
     * @param currPage
     * @return
     */
	@GetMapping("getSecondaryComment/{commentId}/{currPage}")
	public R getSecondaryComment(@PathVariable Long commentId,@PathVariable Integer currPage){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        List<Map<String, Object>> list = dbCommentService.getTwoCommentList(commentId, userId, currPage, PageConf.pageSize);
        return R.data(list);
	}

    /**
     * 新增评论接口
     * @param dynamicId 动态id 必传
     * @param commentId 评论id 可选
     * @param replyUserId 回复的用户id 可选
     * @param text 评论的内容
     * @return
     */
	@PostMapping("addComment")
	public R addComment(@RequestParam(required = true) Long dynamicId,@RequestParam( required = false) Long commentId,
                        @RequestParam(required = false) Long replyUserId ,@RequestParam(required = false) Long replyCommentId,
                        @RequestParam String text){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        if (WordFilter.isContains(text)){
            return R.error(ResultEnum.TEXT_ILLEGAL.getCode(),ResultEnum.TEXT_ILLEGAL.getMessage());
        }
        DbComment comment = dbCommentService.addComment(dynamicId,commentId,replyUserId,replyCommentId,Long.valueOf(userId),text);
        return comment != null ? R.ok():R.error(ResultEnum.SERVICE_BUSY.getCode(),ResultEnum.SERVICE_BUSY.getMessage()) ;
    }
}