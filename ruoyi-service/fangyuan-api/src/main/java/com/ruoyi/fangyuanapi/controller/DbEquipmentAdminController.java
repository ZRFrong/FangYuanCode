package com.ruoyi.fangyuanapi.controller;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbEquipmentAdmin;
import com.ruoyi.fangyuanapi.service.IDbEquipmentAdminService;
import java.util.List;
import java.util.Map;
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
    private IDbEquipmentService equipmentService;

	
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
	 * 获取所有设备管理员列表
	 * @since: 1.0.0
	 * @return: com.ruoyi.common.core.domain.R
	 * @author: ZHAOXIAOSI
	 * @date: 2021/4/7 10:09
	 * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
	 */

	@GetMapping("getAllEquipmentAdmins")
	public R getAllEquipmentAdmins(){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        List<Long> landIds = dbEquipmentAdminService.selectEquipmentIdByUserId(Long.valueOf(userId));
        if (CollectionUtils.isEmpty(landIds)){
            return R.ok("当前无设备，快去添加吧！");
        }
        List<Map<String,Object>> result = dbEquipmentAdminService.selectDbEquipmentAdminByLandId(landIds);
        return R.data(result);
	}

    /**
     * 扫码添加管理员
     * @since: 1.0.0
     * @param landId
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/7 10:08
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("insertEquipmentAdmin")
    public R insertEquipmentAdmin(Long landId){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbEquipmentAdmin admin  = dbEquipmentAdminService.selectDbEquipmentAdminByUserIdAndLandId(landId,Long.valueOf(userId),null);
        if (admin != null){
            return R.ok("您已经成为此大棚的管理员了，切勿重复添加~ _ ~!");
        }
        DbEquipmentAdmin dbEquipmentAdmin =  dbEquipmentAdminService.insertEquipmentAdmin(landId,userId,admin.getEquipmentId());
        return dbEquipmentAdmin != null ? R.ok("添加成功，请前去看看吧！") : R.error("添加失败，请稍后再试或者联系管理员！");
    }
	
}
