package com.ruoyi.fangyuanapi.controller;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.utils.sms.ResultEnum;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbAttention;
import com.ruoyi.fangyuanapi.service.IDbAttentionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 关注和被关注 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("attention")
@RequestMapping("attention")
public class DbAttentionController extends BaseController
{

	@Autowired
	private IDbAttentionService dbAttentionService;

	/**
	 * 关注接口
	 * @param attentionUserId 要关注的用户id
	 * @return
	 */
	@PostMapping
    @ApiOperation(value = "用户关注接口",notes = "关注接口",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attentionUserId",value = "要关注的用户id",required = true)
    })
	public R insertAttention(Long  attentionUserId){
		if (attentionUserId == null || attentionUserId <= 0){
			return null;
		}
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
		DbAttention attention = dbAttentionService.insertDbAttention(Long.valueOf(userId),attentionUserId);
        return attention == null? R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage()): R.ok();
	}

    /**
     * 获取粉丝列表
     * @return
     */
	@GetMapping("getFans/currPage")

	public R getFans(HttpServletRequest request,@PathVariable(value = "currPage",required = false) Integer currPage){
        String userId = request.getHeader(Constants.CURRENT_ID);
		currPage = currPage == null || currPage <=0 ? 0:(currPage - 1) * PageConf.pageSize;
        List<Map<String,String>> list = dbAttentionService.getFans(userId,currPage);
        if (list == null ||list.size() <= 0){
            return R.error(ResultEnum.NULL_FANS.getCode(),ResultEnum.NULL_FANS.getMessage());
        }
        return R.data(list);
    }

    /**
     * 取消关注
     * @param request
     * @param userId
     * @return
     */
	@DeleteMapping("deleteAttention/{userId}")
	public R deleteAttention(HttpServletRequest request,@PathVariable Long userId){
        String loginUserId = request.getHeader(Constants.CURRENT_ID);
        boolean b = dbAttentionService.deleteAttention(loginUserId,userId);
        return null;
    }

    /**
     * 查询关注的人
     * @param request
     * @return
     */
	@GetMapping("getAttention/{currPage}")
	public R getAttention(HttpServletRequest request,@PathVariable(value = "currPage",required = false) Integer currPage){
        String userId = request.getHeader(Constants.CURRENT_ID);
		currPage = currPage == null || currPage <=0 ? 0:(currPage - 1) * PageConf.pageSize;
        List<Map<String,String>> result = dbAttentionService.selectDbAttentionByUserId(userId,currPage);
        if (result.size() <= 0 || request == null){
            return R.error(ResultEnum.NULL_ATTENTION.getCode(),ResultEnum.NULL_ATTENTION.getMessage());
        }
        return R.data(result);
	}

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbAttention get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbAttentionService.selectDbAttentionById(id);
	}

	/**
	 * 查询关注和被关注列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询关注和被关注列表" , notes = "关注和被关注列表")
	public R list(@ApiParam(name="DbAttention",value="传入json格式",required=true) DbAttention dbAttention)
	{
		startPage();
		return result(dbAttentionService.selectDbAttentionList(dbAttention));
	}

	/**
	 * 新增保存关注和被关注
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存关注和被关注" , notes = "新增保存关注和被关注")
	public R addSave(@ApiParam(name="DbAttention",value="传入json格式",required=true) @RequestBody DbAttention dbAttention)
	{
		return toAjax(dbAttentionService.insertDbAttention(dbAttention));
	}

	/**
	 * 修改保存关注和被关注
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存关注和被关注" , notes = "修改保存关注和被关注")
	public R editSave(@ApiParam(name="DbAttention",value="传入json格式",required=true) @RequestBody DbAttention dbAttention)
	{
		return toAjax(dbAttentionService.updateDbAttention(dbAttention));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除关注和被关注" , notes = "删除关注和被关注")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbAttentionService.deleteDbAttentionByIds(ids));
	}

}