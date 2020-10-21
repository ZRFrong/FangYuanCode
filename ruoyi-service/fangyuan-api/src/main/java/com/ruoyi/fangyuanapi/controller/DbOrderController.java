package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.IdWorker;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.dto.PramDto;
import com.ruoyi.fangyuanapi.dto.SkuDto;
import com.ruoyi.fangyuanapi.service.IDbCategoryService;
import com.ruoyi.fangyuanapi.service.IDbSpecParamService;
import com.ruoyi.fangyuanapi.wechat.WeChatPay;
import com.ruoyi.system.domain.DbCategory;
import com.ruoyi.system.domain.DbOrder;
import com.ruoyi.system.domain.DbSpecParam;
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
import com.ruoyi.fangyuanapi.service.IDbOrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * db_order 提供者
 * 
 * @author zheng
 * @date 2020-09-30
 */
@RestController
@Api("dborder")
@RequestMapping("dborder")
public class DbOrderController extends BaseController
{
	
	@Autowired
	private IDbOrderService dbOrderService;

	@Autowired
    private IdWorker idWorker;

	@Autowired
    private RedisUtils redisUtils;

	@Autowired
    private IDbSpecParamService dbSpecParamService;

	@Autowired
    private IDbCategoryService categoryService;

	@Autowired
    private WeChatPay weChatPay;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbOrder get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbOrderService.selectDbOrderById(id);
		
	}
	
	/**
	 * 查询db_order列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询db_order列表" , notes = "db_order列表")
	public R list(@ApiParam(name="DbOrder",value="传入json格式",required=true) DbOrder dbOrder)
	{
		startPage();
        return result(dbOrderService.selectDbOrderList(dbOrder));
	}
	
	
	/**
	 * 新增保存db_order
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存db_order" , notes = "新增保存db_order")
	public R addSave(@ApiParam(name="DbOrder",value="传入json格式",required=true) @RequestBody DbOrder dbOrder)
	{		
		return toAjax(dbOrderService.insertDbOrder(dbOrder));
	}

	/**
	 * 修改保存db_order
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存db_order" , notes = "修改保存db_order")
	public R editSave(@ApiParam(name="DbOrder",value="传入json格式",required=true) @RequestBody DbOrder dbOrder)
	{		
		return toAjax(dbOrderService.updateDbOrder(dbOrder));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除db_order" , notes = "删除db_order")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbOrderService.deleteDbOrderByIds(ids));
	}

	@PostMapping("insertOrder")
	public R insertOrder(HttpServletRequest request, List<SkuDto> list,Long price) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        DbOrder order = new DbOrder();
        if (list !=null && price != null){
            Long sum = null;
            Set<Long> cids = list.stream().map(SkuDto::getCid).collect(Collectors.toSet());
            Set<Long> ids = list.stream().map(SkuDto::getId).collect(Collectors.toSet());
            List<DbSpecParam> specParams = dbSpecParamService.selectDbSpecParamByIds(ids);
            long count = specParams.stream().map(DbSpecParam::getPrice).count();
            if (price != count){
                return R.error();
            }
            order.setPrice(count);
            order.setUserId(Long.valueOf(userId));
            long l = idWorker.nextId();
            order.setOrder(l);
            String ip = IpUtils.getIpAddr(request);
            try {
                String s = weChatPay.send(ip, sum.longValue(), l);
                String str = JSON.toJSONString(list);
                order.setCategoryList(str);
                redisUtils.set(l+"",JSON.toJSONString(order),RedisTimeConf.ONE_DAY);
                return R.data(s);
            } catch (Exception e) {
                e.printStackTrace();
                return R.error(ResultEnum.SERVICE_BUSY.getCode(),ResultEnum.SERVICE_BUSY.getMessage());
            }
        }

        return R.error();
	}

	public static void main(String[] args){
        AtomicReference<Double> sum = null;
        Double s = sum.get();
        long l = s.longValue();
        System.out.println(l);
    }

}
