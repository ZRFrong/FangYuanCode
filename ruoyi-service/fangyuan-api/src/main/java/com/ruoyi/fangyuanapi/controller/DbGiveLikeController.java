package com.ruoyi.fangyuanapi.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.md5.ZhaoMD5Utils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.dto.DynamicDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbGiveLike;
import com.ruoyi.fangyuanapi.service.IDbGiveLikeService;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

/**
 * 点赞 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("giveLike")
@RequestMapping("giveLike")
public class DbGiveLikeController extends BaseController
{

	@Autowired
	private IDbGiveLikeService dbGiveLikeService;

	@Autowired
    private RedisUtils redisUtils;


	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbGiveLike get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbGiveLikeService.selectDbGiveLikeById(id);

	}

	/**
	 * 查询点赞列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询点赞列表" , notes = "点赞列表")
	public R list(@ApiParam(name="DbGiveLike",value="传入json格式",required=true) DbGiveLike dbGiveLike)
	{
		startPage();
		return result(dbGiveLikeService.selectDbGiveLikeList(dbGiveLike));
	}


	/**
	 * 新增保存点赞
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存点赞" , notes = "新增保存点赞")
	public R addSave(@ApiParam(name="DbGiveLike",value="传入json格式",required=true) @RequestBody DbGiveLike dbGiveLike)
	{
		return toAjax(dbGiveLikeService.insertDbGiveLike(dbGiveLike));
	}

	/**
	 * 修改保存点赞
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存点赞" , notes = "修改保存点赞")
	public R editSave(@ApiParam(name="DbGiveLike",value="传入json格式",required=true) @RequestBody DbGiveLike dbGiveLike)
	{
		return toAjax(dbGiveLikeService.updateDbGiveLike(dbGiveLike));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除点赞" , notes = "删除点赞")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbGiveLikeService.deleteDbGiveLikeByIds(ids));
	}

    /**
     * 同是点赞和取消赞接口
     * @param dynamicId 动态id
     * @param commentId 评论id
     * @return
     */
	@PostMapping("insertGiveLike")
	public R insertGiveLike(Long dynamicId,Long commentId){
	    /*  */
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        String s = redisUtils.get(RedisKeyConf.GIVE_LIKE_FLAG_ + userId);
        Integer num = StringUtils.isEmpty(s)?1:Integer.valueOf(s)+1;
        if (num >= 3){
           return R.error(ResultEnum.GIVE_LIKE_ERROR.getCode(),ResultEnum.GIVE_LIKE_ERROR.getMessage());
        }
        DbGiveLike like = new DbGiveLike();
        like.setUserId(Long.valueOf(userId));
        like.setGiveLikeTime(new Date());
        int i = 0;
        DbGiveLike giveLike = null;
        if (dynamicId != null && dynamicId > 0){
            giveLike = dbGiveLikeService.selectDbGiveLikeByUserIdAndDynamicId(userId, dynamicId);
            like.setDynamicId(dynamicId);
        }
        if (commentId != null && commentId > 0){
            giveLike = dbGiveLikeService.selectDbGiveLikeByUserIdAndCommentId(userId, commentId);
            like.setCommentId(commentId);
        }
        if (giveLike != null && giveLike.getIsCancel() >=0 && giveLike.getIsCancel()<=1){
            giveLike.setIsCancel(giveLike.getIsCancel()==0?1:0);
            i = dbGiveLikeService.updateDbGiveLike(giveLike);
        }else {
            i = dbGiveLikeService.insertDbGiveLike(like);
        }
        if (i>0){
            redisUtils.set(RedisKeyConf.GIVE_LIKE_FLAG_+userId,StringUtils.isEmpty(s)?1:Integer.valueOf(s)+1,RedisTimeConf.ONE_HOUR);
        }
        return i>0?new R():R.error(ResultEnum.SERVICE_BUSY.getCode(),ResultEnum.SERVICE_BUSY.getMessage());
	}

    /**
     * 此接口弃用，后续会删除
     *
     * 取消赞
     * @param dynamicId 动态id
     * @param commentId 评论id
     * @return
     */
	@PutMapping("cancelGiveLike")
	public R cancelGiveLike(Long dynamicId,Long commentId){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbGiveLike like = new DbGiveLike();
        String s = redisUtils.get(RedisKeyConf.GIVE_LIKE_FLAG_ + userId);
        Integer num = StringUtils.isEmpty(s)?1:Integer.valueOf(s)+1;
        if (num >= 3){
            return R.error(ResultEnum.GIVE_LIKE_ERROR.getCode(),ResultEnum.GIVE_LIKE_ERROR.getMessage());
        }
        if (dynamicId != null && dynamicId > 0){
            like = dbGiveLikeService.selectDbGiveLikeByUserIdAndDynamicId(userId, dynamicId);
            like.setIsCancel(1);
        }
        if (commentId != null && commentId > 0){
            like =  dbGiveLikeService.selectDbGiveLikeByUserIdAndCommentId(userId,commentId );
            like.setIsCancel(1);
        }
        like.setGiveLikeTime(new Date());
        int i = dbGiveLikeService.updateDbGiveLike(like);
        if (i>0){
            redisUtils.set(RedisKeyConf.GIVE_LIKE_FLAG_+userId,StringUtils.isEmpty(s)?1:Integer.valueOf(s)+1,RedisTimeConf.ONE_HOUR);
        }
        return i>0?new R():R.error(ResultEnum.SERVICE_BUSY.getCode(),ResultEnum.SERVICE_BUSY.getMessage());
    }




}