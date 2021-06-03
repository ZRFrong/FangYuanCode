package com.ruoyi.fangyuanapi.controller;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.aes.TokenUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuanapi.conf.TokenConf;
import com.ruoyi.fangyuanapi.dto.LandAdminDto;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.fangyuanapi.service.IDbUserService;
import com.ruoyi.fangyuanapi.utils.DbEquipmentAdminUtils;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbUser;
import com.ruoyi.system.feign.SendSmsClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbEquipmentAdmin;
import com.ruoyi.fangyuanapi.service.IDbEquipmentAdminService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 【设备管理员】
 * 
 * @author ZHAO
 * @date 2021-03-30
 * @sign 天下风云出我辈，一入代码岁月催！
 */
@RestController
@Api("admin")
@RequestMapping("admin")
public class DbEquipmentAdminController extends BaseController
{
	
	@Autowired
	private IDbEquipmentAdminService dbEquipmentAdminService;

	@Autowired
    private SendSmsClient smsClient;

	@Autowired
    private IDbUserService dbUserService;

	@Autowired
    private IDbLandService dbLandService;

	@Autowired
	private TokenConf tokenConf;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbEquipmentAdmin get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbEquipmentAdminService.selectDbEquipmentAdminById(id);
		
	}
	
	/**
	 * 查询【请填写功能名称】列表
	 */
	@GetMapping("list")
	public R list(@ApiParam(name="DbEquipmentAdmin",value="传入json格式",required=true) DbEquipmentAdmin dbEquipmentAdmin)
	{
		startPage();
        return result(dbEquipmentAdminService.selectDbEquipmentAdminList(dbEquipmentAdmin));
	}
	
	
	/**
	 * 新增保存【请填写功能名称】
	 */
	@PostMapping("save")
	public R addSave(@ApiParam(name="DbEquipmentAdmin",value="传入json格式",required=true) @RequestBody DbEquipmentAdmin dbEquipmentAdmin)
	{		
		return toAjax(dbEquipmentAdminService.insertDbEquipmentAdmin(dbEquipmentAdmin));
	}

	/**
	 * 修改保存【请填写功能名称】
	 */
	@PostMapping("update")
	public R editSave(@ApiParam(name="DbEquipmentAdmin",value="传入json格式",required=true) @RequestBody DbEquipmentAdmin dbEquipmentAdmin)
	{		
		return toAjax(dbEquipmentAdminService.updateDbEquipmentAdmin(dbEquipmentAdmin));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbEquipmentAdminService.deleteDbEquipmentAdminByIds(ids));
	}

	/**
	 * 获取所有大棚管理员列表
	 * @since: 1.0.0
	 * @return: com.ruoyi.common.core.domain.R
	 * @author: ZHAOXIAOSI
	 * @date: 2021/4/7 10:09
	 * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
	 */
	@GetMapping("getAllEquipmentAdmins")
	@ApiOperation(value = "获取管理员列表",notes = "获取管理员列表",httpMethod = "GET")
	public R getAllEquipmentAdmins(){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        List<DbEquipmentAdmin> list =  dbEquipmentAdminService.selectDbEquipmentAdminListByUserId(Long.valueOf(userId));
		if (CollectionUtils.isEmpty(list)){
			return R.ok("您还尚未拥有大棚，请前去添加吧！");
		}

        return R.data(DbEquipmentAdminUtils.test(list, userId));
	}

    /**
     * @since: 1.0.0
     * @param landId 大棚id
     * @param functionIds 功能id集合
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/7 10:08
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("insertEquipmentAdmin")
    public R insertEquipmentAdmin(Long landId,String functionIds){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbEquipmentAdmin admin  = dbEquipmentAdminService.selectDbEquipmentAdminByUserIdAndLandId(landId,Long.valueOf(userId),null);
        if (admin != null){
            return R.ok("您已经成为此大棚的管理员了，切勿重复添加~ _ ~!");
        }
        return checkupAdmin(Long.valueOf(userId), landId, functionIds);
    }

    /***
     * 手机号授权管理员
     * @since: 2.0.0
     * @param phone 手机号
     * @param landId 大棚id
     * @param functionIds  功能id集
	 * @param code  验证码
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/16 16:48
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("phoneAuthorizeAdmin")
	@ApiOperation(value = "手机号授权管理员",notes = "手机号授权管理员",httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone",value = "手机号",required = true),
			@ApiImplicitParam(name = "code",value = "验证码",required = true),
			@ApiImplicitParam(name = "landId",value = "土地id",required = true),
			@ApiImplicitParam(name = "functionIds",value = "功能集",required = true),
	})
    public R phoneAuthorizeAdmin(String phone,String code,Long landId,String functionIds){
        //TODO 先空置，待确认流程后在编写 输出手机号直接添加
		R r = smsClient.checkCode(phone, code);
		if (!"200".equals(r.get("code")+"")){
			return r;
		}
		String userId = getRequest().getHeader(Constants.CURRENT_ID);
		r = checkSuperAdmin(landId, Long.valueOf(userId));
		if (!"200".equals(r.get("code")+"")){
			return r;
		}
		DbUser user = new DbUser();
		user.setPhone(phone);
		user = dbUserService.selectDbUserByPhone(user);
		if (user == null){
			return R.error(HttpStatus.BAD_REQUEST.value(),"此用户尚未在方圆村注册,请联系该用户注册！");
		}
		return checkupAdmin(user.getId(),landId,functionIds);
    }

    private R checkSuperAdmin(Long landId ,Long userId){
		DbEquipmentAdmin equipmentAdmin = dbEquipmentAdminService.selectDbEquipmentAdminByUserIdAndLandId(landId, userId, null);
		if (equipmentAdmin != null && equipmentAdmin.getIsSuperAdmin()>0){
			return R.error(422,"您不是超级管理员，没有该大棚的权限！");
		}
		return R.ok();
	}

    private R  checkupAdmin(Long userId,Long landId,String functionIds){
		DbEquipmentAdmin admin = dbEquipmentAdminService.selectDbEquipmentAdminByUserIdAndLandId(landId, userId, null);
		if (admin != null){
			if (admin.getFunctionIds().equals(functionIds)){
				return R.ok("您已经成为管理员了！");
			}else {//修改操作
				admin.setFunctionIds(functionIds);
				int i = dbEquipmentAdminService.updateDbEquipmentAdmin(admin);
				return i>0 ? R.ok("修改管理员操作成功！"): R.error();
			}
		}
		//未成为管理员
		DbUser user = dbUserService.selectDbUserById(Long.valueOf(userId));
		DbLand land = dbLandService.selectDbLandById(landId);
		if (land == null){
			return R.error(450,"。。。。");
		}
		DbEquipmentAdmin dbEquipmentAdmin = DbEquipmentAdmin.builder()
				.landName(land.getNickName())
				.createTime(new Date())
				.isDel(0)
				.userId(Long.valueOf(userId))
				.landId(landId)
				.functionIds(functionIds)
				.isSuperAdmin(land.getDbUserId())
				.avatar(user.getAvatar())
				.build();
		dbEquipmentAdminService.insertDbEquipmentAdmin(dbEquipmentAdmin);
		return R.ok("您已经成为该大棚的管理员了，请前去查看吧！");
	}

    /**
     * 扫码添加管理员
     * @since: 2.0.0
 	 * @param functionIds 授权的功能id
     * @param landId  大棚id
	 * @param token  大棚管理员的token
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/16 16:10
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
	@PostMapping("authorizeAdmin")
	@ApiOperation(value = "扫码添加管理员接口",notes = "扫码添加管理员接口，授权级别为大棚",httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "landId",value = "大棚",required = true),
			@ApiImplicitParam(name = "functionIds",value = "功能ids",required = true),
			@ApiImplicitParam(name = "token",value = "生成二维码的用户Token",required = true)
	})
	public R authorizeAdmin(String functionIds,Long landId,String token){
		Map<String, Object> map = TokenUtils.verifyToken(token, tokenConf.getAccessTokenKey());
		if (map == null){
			return R.error(403,"令牌验证错误!");
		}
		R r = checkSuperAdmin(landId, Long.valueOf(map.get("id") + ""));
		if (Integer.valueOf(r.get("code")+"") != 200){
			return r;
		}
		String userId = getRequest().getHeader(Constants.CURRENT_ID);
		return checkupAdmin(Long.valueOf(userId),landId,functionIds);
	}

}
