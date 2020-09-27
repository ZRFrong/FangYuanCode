//package com.ruoyi.fangyuanapi.controller;
//
//import com.qiniu.common.QiniuException;
//import com.ruoyi.common.constant.Constants;
//import com.ruoyi.common.json.JSONUtils;
//import com.ruoyi.common.redis.config.RedisTimeConf;
//import com.ruoyi.common.redis.util.RedisUtils;
//import com.ruoyi.common.utils.StringUtils;
//import com.ruoyi.common.utils.sensitivewdfilter.WordFilter;
//import com.ruoyi.common.utils.sms.ResultEnum;
//import com.ruoyi.common.utils.upload.CheckResultUtils;
//import com.ruoyi.fangyuanapi.conf.QiniuConf;
//import com.ruoyi.fangyuanapi.conf.QiniuUtils;
//import com.ruoyi.fangyuanapi.service.DbDynamicService;
//import com.ruoyi.system.feign.RemoteOssService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//
//import com.ruoyi.common.core.domain.R;
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.system.domain.DbUserDynamic;
//import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.xml.soap.Text;
//import java.util.*;
///*
//*
// * 动态 提供者
// *
// * @author fangyuan
// * @date 2020-09-07*/
//
//
//@RestController
//@Api("dynamic1")
//@RequestMapping("dynamic1")
//public class DbUserDynamicController extends BaseController
//{
//
//	@Autowired
//	private IDbUserDynamicService dbUserDynamicService;
//
//	@Autowired
//	private QiniuUtils qiniuUtils;
//
//	@Autowired
//	private QiniuConf qiniuConf;
//
//	@Autowired
//	private RemoteOssService remoteOssService;
//
//	@Autowired
//    private RedisUtils redisUtils;
///*
//
//*
//	 *
//	 * @param request 用来获取heard头里的userid
//	 * @param text 动态发布的内容
//	 * @param file 资源数组： 图片可有六个 视频一个
//	 * @param authority 权限：谁可见
//	 * @param entryIds 词条数组
//	 * @param site 发表动态时的位置
//	 * @return
//*/
//
//
//	@PostMapping(value = "insterDynamic",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public R insterDynamic(HttpServletRequest request, @RequestParam("text") String text, @RequestPart("file") MultipartFile[] file, @RequestParam(value = "authority",required = false)Integer authority,@RequestParam(value = "entryIds",required = false) Long[] entryIds,@RequestParam(value = "site",required = false) String site){
//		String userId = request.getHeader(Constants.CURRENT_ID);
//        DbUserDynamic dynamic = null;
//		if (StringUtils.isNotEmpty(text)&& file !=null && file.length>0 && file.length <= 6){
//            if (WordFilter.isContains(text)){//敏感词过滤
//				return R.error(ResultEnum.TEXT_ILLEGAL.getCode(),ResultEnum.TEXT_ILLEGAL.getMessage());
//			}
//			boolean isReview = false;
//			dynamic = new DbUserDynamic();//内容合法初始化动态对象
//            dynamic.setContent(text);//内容
//            dynamic.setPermission(authority);//权限
//            dynamic.setOrientation(site);//位置
//            ArrayList<String> urls = new ArrayList<>();//装多个url
//            JSONUtils<Object, Object> utils = new JSONUtils<>();
//            for (MultipartFile multipartFile : file) {
//				if (StringUtils.checkFileIsImages(multipartFile.getOriginalFilename(),qiniuConf.getImageFilter())){//是图片调用上传接口
//					//String url = dbUserDynamicService.uploadFile(multipartFile);
//                    dynamic.setIsHaveVoide(1);//没有视频
//                    R r = remoteOssService.editSave(multipartFile);
//                    String url = (String) r.get("msg");//上传
//                    if (StringUtils.isEmpty(url)){
//                        return R.error(ResultEnum.SERVICE_BUSY.getCode(),ResultEnum.SERVICE_BUSY.getMessage());
//                    }
//                    String s = qiniuUtils.checkImage(url);
//                    switch (s){
//                        case "block" ://不合法直接返回
//                            return R.error(ResultEnum.RESULT_BLOCK.getCode(),ResultEnum.RESULT_BLOCK.getMessage());
//                        case "review" ://人工审核
//                            dynamic.setIsBanned(1);
//                            isReview = true;
//                    }
//                    urls.add(url);
//				}
//				if (StringUtils.checkFileIsVideo(multipartFile.getOriginalFilename(),qiniuConf.getVideoUrl())){//是视频立刻返回接果
//                    String videoUrl = dbUserDynamicService.uploadFile(multipartFile);
//                    if (videoUrl == null){
//                        return R.error(ResultEnum.SERVICE_BUSY.getCode(),ResultEnum.SERVICE_BUSY.getMessage());
//                    }
//                    urls.add(videoUrl);
//                    dynamic.setIsHaveVoide(0);//有视频
//                    dynamic.setResource(utils.listToJsonArray(urls));
//                    dynamic.setIsBanned(1);
//                    String jobId = qiniuUtils.videoCheck(videoUrl);
//                    DbUserDynamic userDynamic = dbUserDynamicService.insterDynamic(userId, dynamic, entryIds);
//                    redisUtils.set(jobId,userDynamic.getId(),RedisTimeConf.THERE_MONTH);
//                    return R.data(jobId);
//                }
//			}
//            dynamic.setResource(utils.listToJsonArray(urls));
//            DbUserDynamic dynamic1 = dbUserDynamicService.insterDynamic(userId, dynamic, entryIds);
//            if (isReview){//插入人工审核表
//                dynamic1.getId();
//                return R.error(ResultEnum.RESULT_REVIEW.getCode(),ResultEnum.RESULT_REVIEW.getMessage());
//            }
//		}
//		return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
//	}
//
//	@GetMapping("videoCallback/{jobId}")
//	public R videoCallback(HttpServletRequest request,@PathVariable String jodId){
//        String userId = request.getHeader(Constants.CURRENT_ID);
//        String dynamicId = redisUtils.get(jodId);
//        if (StringUtils.isEmpty(dynamicId)){
//            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
//        }
//        try {
//            String result = qiniuUtils.getCheckVideoResult(jodId);
//            switch (result){
//                case "block"://违规
//                    dbUserDynamicService.deleteDbUserDynamicById(Long.valueOf(dynamicId));
//                case "review"://需人工审核
//
//                case "pass":
//                    return R.ok("审核已通过！");
//                default:
//                    return R.error();
//            }
//        } catch (QiniuException e) {
//            e.printStackTrace();
//        }
//        return R.error();
//    }
//
//
//
//
//
//
//
//
//*
//	 * 查询${tableComment}
//
//
//	@GetMapping("get/{id}")
//	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
//	public DbUserDynamic get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
//	{
//		return dbUserDynamicService.selectDbUserDynamicById(id);
//
//	}
//
//*
//	 * 查询动态列表
//
//
//	@GetMapping("list")
//	@ApiOperation(value = "查询动态列表" , notes = "动态列表")
//	public R list(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) DbUserDynamic dbUserDynamic)
//	{
//		startPage();
//		return result(dbUserDynamicService.selectDbUserDynamicList(dbUserDynamic));
//	}
//
//
//*
//	 * 新增保存动态
//
//
//	@PostMapping("save")
//	@ApiOperation(value = "新增保存动态" , notes = "新增保存动态")
//	public R addSave(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) @RequestBody DbUserDynamic dbUserDynamic)
//	{
//		return toAjax(dbUserDynamicService.insertDbUserDynamic(dbUserDynamic));
//	}
//
//*
//	 * 修改保存动态
//
//
//	@PostMapping("update")
//	@ApiOperation(value = "修改保存动态" , notes = "修改保存动态")
//	public R editSave(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) @RequestBody DbUserDynamic dbUserDynamic)
//	{
//		return toAjax(dbUserDynamicService.updateDbUserDynamic(dbUserDynamic));
//	}
//
//*
//	 * 删除${tableComment}
//
//
//	@PostMapping("remove")
//	@ApiOperation(value = "删除动态" , notes = "删除动态")
//	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
//	{
//		return toAjax(dbUserDynamicService.deleteDbUserDynamicByIds(ids));
//	}
//
//}
