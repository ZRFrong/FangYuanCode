package com.ruoyi.fangyuanapi.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.PassDemo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.md5.ZhaoMD5Utils;
import com.ruoyi.common.utils.sms.CategoryType;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.domain.DbUser;
import com.ruoyi.fangyuanapi.dto.DynamicDto;
import com.ruoyi.fangyuanapi.service.IDbGiveLikeService;
import com.ruoyi.fangyuanapi.service.IDbUserAndDynamicService;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;
import com.ruoyi.fangyuanapi.service.IDbUserService;
import com.ruoyi.system.feign.RemoteDeptService;
import com.ruoyi.system.feign.SendSmsClient;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("wxUser")
public class DbUserController {


    @Autowired
    private RemoteDeptService remoteDeptService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IDbUserService dbUserService;

    @Autowired
    private IDbGiveLikeService dbGiveLikeService;

    @Autowired
    private IDbUserAndDynamicService dbUserAndDynamicService;

    @Autowired
    private IDbUserDynamicService dbUserDynamicService;



    /**
     * 小程序注册
     * @param dbUser
     * @return R { 0 : SUCCESS}
     */
    @PostMapping("smallRegister")
    public R wxRegister(DbUser dbUser,String uuid){
        String codeSuccess = redisUtils.get(CategoryType.USER_CODE_SUCCESS_ + dbUser.getPhone());
        if (StringUtils.isEmpty(codeSuccess) && !ZhaoMD5Utils.convertMD5(uuid).equals(codeSuccess)){//判断通行证是否有效
            redisUtils.delete(CategoryType.USER_CODE_SUCCESS_ + dbUser.getPhone());
            return R.error(ResultEnum.CODE_SUCCESS.getCode(),ResultEnum.CODE_SUCCESS.getMessage());
        }
        if (dbUser == null && dbUser.getPhone() != null){
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        //todo 查询手机号是否有可能在app注册了，如果有同步数据
        DbUser user = dbUserService.selectDbUserByPhone(dbUser);
        String s;
        if (user == null || user.getPhone() != dbUser.getPhone()){//注册
            dbUser.setCreated(new Date());
            int i = dbUserService.insertDbUser(dbUser);
            s = PassDemo.gen(dbUser.getId());
            return R.data(s);
        }
        s = ZhaoMD5Utils.string2MD5(user.getSalt() + user.getId());
        redisUtils.set(CategoryType.USER_TOKEN_.name()+dbUser.getId(),s,RedisTimeConf.THERE_MONTH);
        return R.data(s);
    }


    /**
     * 在用户点击我的页面时触发，如果用户存在则返回数据，无则去注册
     * @param openId
     * @return
     */
    @GetMapping("smallLogin/{openId}")
    public R wxAward(@PathVariable String openId){
        if (StringUtils.isEmpty(openId)){
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        Map<String,Integer> data = dbUserService.userIsRegister(openId);
        if (data != null){
            return R.data(data);//动态关注粉丝
        }else {//注册
            return new R();
        }
    }

    /**
     * 我的动态
     * @return
     */
    @PostMapping("dynamic/{openId}/{currPage }/{pageSize}")
    public R getUserDynamic(@PathVariable String openId,@PathVariable Integer currPage,@PathVariable Integer pageSize){
        if (StringUtils.isEmpty(openId)){
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        DbUser dbUser = dbUserService.selectDbUserByOpenId(openId);
        if (dbUser == null){
            //此处不做返回，来到此处的皆为绕过前面接口直接来的
            return null;
        }
        List<DynamicDto> dynamic = dbUserService.getUserDynamic(dbUser, currPage, pageSize);
        return R.data(dynamic);
    }

    /**
     * 我的赞
     * @return
     */
    @GetMapping("giveLikeNum")
    public R getGiveLikeSum(HttpServletRequest request){
        String userId = request.getHeader(Constants.CURRENT_ID);
        Integer likeNum = dbGiveLikeService.selectUserGiveLikeNum(userId);
        return likeNum != null?new R():R.error();
    }

    /**
     * 我的相册
     * @param request
     * @param currPage
     * @param pageSize
     * @return list<string>
     */
    @GetMapping("photoAlbum")
    public R getPhotoAlbum(HttpServletRequest request,Integer currPage,Integer pageSize){
        String userId = request.getHeader(Constants.CURRENT_ID);
        currPage = pageSize-1 * pageSize;
        List<Long> dynamicIds = dbUserAndDynamicService.selectDbUserAndDynamicByUserId(Long.valueOf(userId));
        if (dynamicIds == null || dynamicIds.size() <= 0){
            return R.ok("您的相册为空！");
        }
        List<Map<String,String>> PhotoAlbum = dbUserDynamicService.selectImagesByDynamicId(dynamicIds,currPage,pageSize);

        return R.data(PhotoAlbum);
    }

}
