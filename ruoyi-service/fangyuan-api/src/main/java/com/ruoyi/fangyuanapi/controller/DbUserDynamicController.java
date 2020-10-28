package com.ruoyi.fangyuanapi.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qiniu.common.QiniuException;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.json.JSONUtils;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.md5.ZhaoMD5Utils;
import com.ruoyi.common.utils.sensitivewdfilter.WordFilter;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.conf.QiniuConf;
import com.ruoyi.fangyuanapi.conf.QiniuUtils;
import com.ruoyi.fangyuanapi.service.*;
import com.ruoyi.system.domain.DbManualReview;
import com.ruoyi.system.feign.RemoteOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbUserDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 动态 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */

@RestController
@Api("dynamic1")
@RequestMapping("dynamic1")
public class DbUserDynamicController extends BaseController {

	@Autowired
	private IDbUserDynamicService dbUserDynamicService;

	@Autowired
	private QiniuUtils qiniuUtils;

	@Autowired
	private QiniuConf qiniuConf;

	@Autowired
	private RemoteOssService remoteOssService;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private JSONUtils utils;

	@Autowired
	private IDbManualReviewService dbManualReviewService;

	@Autowired
	private IDbUserAndDynamicService dbUserAndDynamicService;

	@Autowired
	private IDbAttentionService dbAttentionService;

	/**
	 * 获取关注的朋友的动态
	 *
	 * @param request
	 * @param currPage
	 * @return
	 */
	public R getAttentionUserDynamic(HttpServletRequest request, Integer currPage) {
		String userId = request.getHeader(Constants.CURRENT_ID);
		currPage = currPage == null || currPage <= 0 ? 0 : (currPage - 1) * PageConf.pageSize;
		List<Long> ids = dbAttentionService.selectReplyAttentionUserIds(userId);//关注的userIdS
		if (ids == null) { //没有关注的人
			return R.error(ResultEnum.NULL_ATTENTION.getCode(), ResultEnum.NULL_ATTENTION.getMessage());
		}
		ArrayList<DbUserDynamic> dynamics = new ArrayList<>();
		ids.forEach(e -> {//获取关注的人的动态,
			List<Long> list = dbUserAndDynamicService.selectDbUserAndDynamicByUserId(Long.valueOf(userId));//动态id
			list.forEach(d -> {
				DbUserDynamic dynamic = dbUserDynamicService.selectDbUserDynamicById(d);
				dynamics.add(dynamic);
				redisUtils.zSetAdd(RedisKeyConf.REDIS_ZSET_.name() + userId, String.valueOf(dynamic.getId()), dynamic.getCreatedTime().getTime());
			});
		});

		ArrayList<DbUserDynamic> result = new ArrayList<>();
		Set<String> setIds = redisUtils.revRange(RedisKeyConf.REDIS_ZSET_.name() + userId, currPage, PageConf.pageSize);
		if (dynamics.size() <= 10) {
			return R.data(dynamics);
		}
		//取10条
		dynamics.forEach(a -> {
			setIds.forEach(b -> {
				if (a.getId().equals(b)) {
					result.add(a);
				}
			});
		});
		redisUtils.delete(RedisKeyConf.REDIS_ZSET_ + userId);

		return R.data(result);
	}


	/**
	 * @param request   用来获取heard头里的userid
	 * @param text      动态发布的内容
	 * @param file      资源数组： 图片可有六个 视频一个
	 * @param authority 权限：谁可见
	 * @param entryIds  词条数组
	 * @param site      发表动态时的位置
	 * @return
	 *//*

	 * @return jobId或者人工审核Id
	 */
	@PostMapping(value = "insterDynamic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public R insterDynamic(HttpServletRequest request, @RequestParam("text") String text, @RequestPart("file") MultipartFile[] file, @RequestParam(value = "authority", required = false) Integer authority, @RequestParam(value = "entryIds", required = false) Long[] entryIds, @RequestParam(value = "site", required = false) String site) {
		String userId = request.getHeader(Constants.CURRENT_ID);
		DbUserDynamic dynamic = null;
		if (StringUtils.isNotEmpty(text) && file != null && file.length > 0 && file.length <= 6) {
			if (WordFilter.isContains(text)) {//敏感词过滤
				return R.error(ResultEnum.TEXT_ILLEGAL.getCode(), ResultEnum.TEXT_ILLEGAL.getMessage());
			}
			boolean isReview = false;
			dynamic = new DbUserDynamic();//内容合法初始化动态对象
			dynamic.setContent(text);//内容
			dynamic.setPermission(authority);//权限
			dynamic.setOrientation(site);//位置
			ArrayList<String> urls = new ArrayList<>();//装多个url
			for (MultipartFile multipartFile : file) {
				if (StringUtils.checkFileIsImages(multipartFile.getOriginalFilename(), qiniuConf.getImageFilter())) {//是图片调用上传接口
					//String url = dbUserDynamicService.uploadFile(multipartFile);
					dynamic.setIsHaveVoide(1);//没有视频
					R r = remoteOssService.editSave(multipartFile);
					String url = (String) r.get("msg");//上传
					if (StringUtils.isEmpty(url)) {
						return R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage());
					}
					String s = qiniuUtils.checkImage(url);
					switch (s) {
						case "block"://不合法直接返回
							return R.error(ResultEnum.RESULT_BLOCK.getCode(), ResultEnum.RESULT_BLOCK.getMessage());
						case "review"://人工审核
							dynamic.setIsBanned(1);
							isReview = true;
					}
					urls.add(url);
				}
				if (StringUtils.checkFileIsVideo(multipartFile.getOriginalFilename(), qiniuConf.getVideoUrl())) {//是视频立刻返回接果
					String videoUrl = dbUserDynamicService.uploadFile(multipartFile);
					if (videoUrl == null) {
						return R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage());
					}
					urls.add(videoUrl);
					dynamic.setIsHaveVoide(0);//有视频
					dynamic.setResource(utils.listToJsonArray(urls));
					dynamic.setIsBanned(1);
					String jobId = qiniuUtils.videoCheck(videoUrl);
					redisUtils.set(jobId, dynamicDataToString(userId, dynamic, entryIds), RedisTimeConf.THREE_DAY);//存放审核完成后要插入的数据
					return R.data(jobId);
				}
			}
			String urlString = utils.listToJsonArray(urls);
			dynamic.setResource(urlString);
			if (isReview) {//插入人工审核表
				DbManualReview manualReview = new DbManualReview();
				manualReview.setUserId(Long.valueOf(userId));
				manualReview.setDynamicContent(text);
				manualReview.setDynamicResource(urlString);
				manualReview.setCreated(new Date());
				int i = dbManualReviewService.insertDbManualReview(manualReview);
				redisUtils.set(manualReview.getId() + "", dynamicDataToString(userId, dynamic, entryIds), RedisTimeConf.THREE_DAY);//存放审核完成后要插入的数据
				return R.data(ZhaoMD5Utils.string2MD5(manualReview.getId() + ""));//人工审核id
			}
			DbUserDynamic dynamic1 = dbUserDynamicService.insterDynamic(userId, dynamic, entryIds);//PASS直接插入数据
			return R.ok();
		}
		return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
	}

	/**
	 * 视频审核回调接口
	 *
	 * @param request
	 * @param jodId
	 * @return
	 */
	@GetMapping("videoCallback/{jobId}")
	public R videoCallback(HttpServletRequest request, @PathVariable String jodId) {
		String userId = request.getHeader(Constants.CURRENT_ID);
		String dynamic = redisUtils.get(jodId);
		if (StringUtils.isEmpty(dynamic)) {
			return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
		}
		try {
			String result = qiniuUtils.getCheckVideoResult(jodId);
			Map<String, String> map = utils.stringToMap(dynamic);
			DbUserDynamic dynamic1 = JSON.parseObject(map.get("dynamic"), DbUserDynamic.class);
			Long[] entryIds = JSON.parseObject(map.get("entryIds"), Long[].class);
			switch (result) {
				case "block"://违规
					dbUserDynamicService.deleteDbUserDynamicById(Long.valueOf(dynamic));
				case "review"://需人工审核
					DbManualReview manualReview = new DbManualReview();
					manualReview.setUserId(Long.valueOf(userId));
					manualReview.setDynamicContent(dynamic1.getContent());
					manualReview.setDynamicResource(dynamic1.getResource());
					manualReview.setCreated(new Date());
					int i = dbManualReviewService.insertDbManualReview(manualReview);
					redisUtils.set(manualReview.getId() + "", dynamic, RedisTimeConf.THREE_DAY);//存放审核完成后要插入的数据
					redisUtils.delete(jodId);//删除视频上传之后放的数据
					return R.data(ZhaoMD5Utils.string2MD5(manualReview.getId() + ""));//人工审核id
				case "pass"://插入对象
					DbUserDynamic userDynamic = dbUserDynamicService.insterDynamic(userId, dynamic1, entryIds);
					return userDynamic == null ? R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage()) : R.ok("审核已通过！");
				default:
					return R.error();
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return R.error();
	}

	/**
	 * 人工审核结果查询
	 *
	 * @param reviewId 查询码 MD5加密
	 * @return 结果
	 */
	@GetMapping("getReviewResult/{reviewId}")
	public R getReviewResult(HttpServletRequest request, @PathVariable String reviewId) {
		String userId = request.getHeader(Constants.CURRENT_ID);
		reviewId = ZhaoMD5Utils.convertMD5(reviewId);
		String data = redisUtils.get(reviewId);
		if (StringUtils.isEmpty(data)) {
			return null;//没有命中缓存，不处理
		}
		DbManualReview dbManualReview = dbManualReviewService.selectDbManualReviewById(Long.valueOf(reviewId));
		if (1 == dbManualReview.getIsSussess() || 0 == dbManualReview.getIsViolation()) {
			Map<String, String> map = utils.stringToMap(data);
			DbUserDynamic dynamic1 = JSON.parseObject(map.get("dynamic"), DbUserDynamic.class);
			Long[] entryIds = JSON.parseObject(map.get("entryIds"), Long[].class);
			DbUserDynamic dynamic = dbUserDynamicService.insterDynamic(userId, dynamic1, entryIds);
			Optional<DbUserDynamic> optional = Optional.ofNullable(dynamic);
			optional.get();
			return dynamic == null ? R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage()) : R.ok("审核已通过！");
		}
		if (1 == dbManualReview.getIsSussess() || 1 == dbManualReview.getIsViolation()) {
			redisUtils.delete(reviewId);//审核失败
			return R.error(ResultEnum.VIOLATION_FAILURE.getCode(), ResultEnum.VIOLATION_FAILURE.getMessage());
		}
		return R.error(ResultEnum.UNDER_REVIEW.getCode(), ResultEnum.UNDER_REVIEW.getMessage());
	}

	/**
	 * 转换json字符
	 *
	 * @param userId
	 * @param dynamic
	 * @param entryIds
	 * @return
	 */
	private String dynamicDataToString(String userId, DbUserDynamic dynamic, Long[] entryIds) {
		HashMap<String, String> map = new HashMap<>();
		String s1 = utils.objectToString(dynamic);
		map.put("userId", userId);
		map.put("dynamic", s1);
		map.put("entryIds", utils.objectToString(entryIds));

		return utils.mapToString(map);
	}







	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbUserDynamic get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbUserDynamicService.selectDbUserDynamicById(id);

	}


	@GetMapping("list")
	@ApiOperation(value = "查询动态列表" , notes = "动态列表")
	public R list(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) DbUserDynamic dbUserDynamic)
	{
		startPage();
		return result(dbUserDynamicService.selectDbUserDynamicList(dbUserDynamic));
	}



	@PostMapping("save")
	@ApiOperation(value = "新增保存动态" , notes = "新增保存动态")
	public R addSave(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) @RequestBody DbUserDynamic dbUserDynamic)
	{
		return toAjax(dbUserDynamicService.insertDbUserDynamic(dbUserDynamic));
	}


	@PostMapping("update")
	@ApiOperation(value = "修改保存动态" , notes = "修改保存动态")
	public R editSave(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) @RequestBody DbUserDynamic dbUserDynamic)
	{
		return toAjax(dbUserDynamicService.updateDbUserDynamic(dbUserDynamic));
	}


	@PostMapping("remove")
	@ApiOperation(value = "删除动态" , notes = "删除动态")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbUserDynamicService.deleteDbUserDynamicByIds(ids));
	}




}
