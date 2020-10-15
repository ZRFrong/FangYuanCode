package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.PassDemo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.md5.ZhaoMD5Utils;
import com.ruoyi.common.utils.sms.CategoryType;
import com.ruoyi.common.utils.sms.PhoneUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.system.domain.DbUser;
import com.ruoyi.fangyuanapi.dto.DynamicDto;
import com.ruoyi.fangyuanapi.service.IDbGiveLikeService;
import com.ruoyi.fangyuanapi.service.IDbUserAndDynamicService;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;
import com.ruoyi.fangyuanapi.service.IDbUserService;
import com.ruoyi.system.feign.RemoteDeptService;
import com.ruoyi.system.feign.SendSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private SendSmsClient sendSmsClient;



    @PutMapping("appUpdatePassword")
    public R appUpdatePassword(String phone,String code,String password){
        if (StringUtils.isNotEmpty(phone)&& PhoneUtils.checkPhone(phone) && StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(password)) {
            sendSmsClient.checkCode(phone,code);
            return null;
        }
        return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
    }


    /**
     * app用户登陆
     * @param phone 手机号
     * @param password 密码
     * @param code 验证码
     * @return 用户登陆
     */
    @PostMapping("appLogin")
    public R appLogin(String phone, String password, String code) {
        DbUser dbUser = null;
        String s = redisUtils.get(CategoryType.PHONE_LOGIN_NUM_.name() + phone);
        Integer num = s == null ? 0: Integer.valueOf(s);
        if (5 < Integer.valueOf(s == null ? "0":s)){//登录频繁
            return R.error(ResultEnum.LOGIN_HOUR_ERROR.getCode(),ResultEnum.LOGIN_HOUR_ERROR.getMessage());
        }
        if ( phone!= null && PhoneUtils.checkPhone(phone)){
            DbUser user = new DbUser();
            user.setPhone(phone);
            dbUser = dbUserService.selectDbUserByPhone(user);
            redisUtils.set(CategoryType.PHONE_LOGIN_NUM_.name() + phone,1,RedisTimeConf.ONE_HOUR);
            if (dbUser == null){
                return R.error(ResultEnum.PHONE_NOT_REGISTER.getCode(),ResultEnum.PHONE_NOT_REGISTER.getMessage());
            }
        }else {
            return R.error(ResultEnum.PHONE_ERROR.getCode(),ResultEnum.PHONE_ERROR.getMessage());
        }

        if (dbUser!= null  && StringUtils.isNotEmpty(password) && StringUtils.isEmpty(code)){//密码登录
            String salt = dbUser.getSalt();
            if (ZhaoMD5Utils.string2MD5(password+salt).equals(dbUser.getPassword())){//登录成功
                return R.data(PassDemo.gen(dbUser.getId()));//返回token
            }else {
                redisUtils.set(CategoryType.PHONE_LOGIN_NUM_.name() + phone,num+1,RedisTimeConf.ONE_HOUR);
                return R.error(ResultEnum.PASSWORD_ERROE.getCode(),ResultEnum.PASSWORD_ERROE.getMessage());
            }
        }

        if (dbUser!= null  && StringUtils.isEmpty(password) && StringUtils.isNotEmpty(code)){//验证码登录
            R r = sendSmsClient.checkCode(phone, code);
            if ("200".equals(r.get("code")+"")){

                return R.data(PassDemo.gen(dbUser.getId()));
            }else {
                redisUtils.set(CategoryType.PHONE_LOGIN_NUM_.name() + phone,num+1,RedisTimeConf.ONE_HOUR);
                return r;
            }
        }

        return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
    }

    /**
     * app注册
     * @param phone 手机号
     * @param password 密码
     * @param passwordagain 确认密码
     * @param code 验证码
     * @return token
     */
    @PostMapping("appRegister")
    public R appRegister(String phone, String password, String passwordagain, String code) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordagain) || StringUtils.isEmpty(code)) {
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        if (!password.equals(passwordagain)) {//不做提示
            return R.error();
        }
        if(!StringUtils.checkPassword(password)){
            return R.error(ResultEnum.PASSWORD_NOT_RULE.getCode(),ResultEnum.PASSWORD_NOT_RULE.getMessage());
        }
        R r = sendSmsClient.checkCode(phone, code);
        if (!"200".equals(r.get("code")+"")) {
            return r;
        }
        DbUser user = new DbUser();
        user.setPhone(phone);
        DbUser dbUser = dbUserService.selectDbUserByPhone(user);

        String uuid = StringUtils.getUUID();
        String s = ZhaoMD5Utils.string2MD5(password + uuid);
        if (dbUser == null) {//未在方圆村其他地方注册过，新增用户
            user.setSalt(uuid);
            user.setPassword(s);
            user.setCreateTime(new Date());
            user.setUserFrom(1);
            user.setPhoneIsVerify(0);
            dbUserService.insertDbUser(user);
        } else {
            if (dbUser.getUserFrom() == 1) {
                return R.error("您已注册请前去登陆！");
            }
            if (dbUser.getUserFrom() == 0) {
                dbUser.setUpdateTime(new Date());//修改时间
                dbUser.setPassword(s);
                int i = dbUserService.updateDbUser(dbUser);
                return i > 0 ? new R() : R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage());
            }
        }
        return R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage());
    }


    /**
     * 小程序注册
     *
     * @param dbUser
     * @return R { 0 : SUCCESS}
     */
    @PostMapping("smallRegister")
    public R wxRegister(DbUser dbUser, String code) {
        if (dbUser == null || dbUser.getPhone() != null || code == null) {
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        R r = sendSmsClient.checkCode(dbUser.getPhone(), code);
        if (!"200".equals(r.get("code")+"")) {
            return r;
        }

        //todo 查询手机号是否有可能在app注册了，如果有同步数据
        DbUser user = dbUserService.selectDbUserByPhone(dbUser);
        String s;
        if (user == null || user.getPhone() != dbUser.getPhone()) {//注册
            dbUser.setCreated(new Date());
            dbUser.setUserFrom(0);
            dbUser.setPhoneIsVerify(0);
            int i = dbUserService.insertDbUser(dbUser);
            s = PassDemo.gen(dbUser.getId());
            return R.data(s);
        } else {
            user.setAvatar(dbUser.getAvatar());
            user.setNickname(dbUser.getNickname());
            user.setUpdateTime(new Date());
            dbUserService.updateDbUser(user);
            s = PassDemo.gen(user.getId());
        }
        redisUtils.set(CategoryType.USER_TOKEN_.name() + dbUser.getId(), s, RedisTimeConf.THERE_MONTH);
        return R.data(s);
    }


//    /**
//     * 小程序注册
//     * @param dbUser
//     * @return R { 0 : SUCCESS}
//     */
//    @PostMapping("smallRegister")
//    public R wxRegister(DbUser dbUser,String uuid){
//        String codeSuccess = redisUtils.get(CategoryType.USER_CODE_SUCCESS_ + dbUser.getPhone());
//        if (StringUtils.isEmpty(codeSuccess) && !ZhaoMD5Utils.convertMD5(uuid).equals(codeSuccess)){//判断通行证是否有效
//            redisUtils.delete(CategoryType.USER_CODE_SUCCESS_ + dbUser.getPhone());
//            return R.error(ResultEnum.CODE_SUCCESS.getCode(),ResultEnum.CODE_SUCCESS.getMessage());
//        }
//        if (dbUser == null && dbUser.getPhone() != null){
//            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
//        }
//        //todo 查询手机号是否有可能在app注册了，如果有同步数据
//        DbUser user = dbUserService.selectDbUserByPhone(dbUser);
//        String s;
//        if (user == null || user.getPhone() != dbUser.getPhone()){//注册
//            dbUser.setCreated(new Date());
//            int i = dbUserService.insertDbUser(dbUser);
//            s = PassDemo.gen(dbUser.getId());
//            return R.data(s);
//        }
//        s = ZhaoMD5Utils.string2MD5(user.getSalt() + user.getId());
//        redisUtils.set(CategoryType.USER_TOKEN_.name()+dbUser.getId(),s,RedisTimeConf.THERE_MONTH);
//        return R.data(s);
//    }


    /**
     * 在用户点击我的页面时触发，如果用户存在则返回数据，无则去注册
     *
     * @param openId
     * @return
     */
    @GetMapping("smallLogin/{openId}")
    public R wxAward(@PathVariable String openId) {
        if (StringUtils.isEmpty(openId)) {
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        Map<String, Integer> data = dbUserService.userIsRegister(openId);
        if (data != null) {
            return R.data(data);//动态关注粉丝
        } else {//注册
            return new R();
        }
    }

    /**
     * 我的动态
     *
     * @return
     */
    @PostMapping("dynamic/{openId}/{currPage }/{pageSize}")
    public R getUserDynamic(@PathVariable String openId, @PathVariable Integer currPage, @PathVariable Integer pageSize) {
        if (StringUtils.isEmpty(openId)) {
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        DbUser dbUser = dbUserService.selectDbUserByOpenId(openId);
        if (dbUser == null) {
            //此处不做返回，来到此处的皆为绕过前面接口直接来的
            return null;
        }
        List<DynamicDto> dynamic = dbUserService.getUserDynamic(dbUser, currPage, pageSize);
        return R.data(dynamic);
    }

    /**
     * 我的赞
     *
     * @return
     */
    @GetMapping("giveLikeNum")
    public R getGiveLikeSum(HttpServletRequest request) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        Integer likeNum = dbGiveLikeService.selectUserGiveLikeNum(userId);
        return likeNum != null ? new R() : R.error();
    }

    /**
     * 我的相册
     *
     * @param request
     * @param currPage
     * @param pageSize
     * @return list<string>
     */
    @GetMapping("photoAlbum")
    public R getPhotoAlbum(HttpServletRequest request, Integer currPage, Integer pageSize) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        currPage = pageSize - 1 * pageSize;
        List<Long> dynamicIds = dbUserAndDynamicService.selectDbUserAndDynamicByUserId(Long.valueOf(userId));
        if (dynamicIds == null || dynamicIds.size() <= 0) {
            return R.ok("您的相册为空！");
        }
        List<Map<String, String>> PhotoAlbum = dbUserDynamicService.selectImagesByDynamicId(dynamicIds, currPage, pageSize);

        return R.data(PhotoAlbum);
    }

    /**
     * 获取个人资料接口
     * @param request
     * @return
     */
    @GetMapping("getUserDate")
    public R getUserDate(HttpServletRequest request){
        String userId = request.getHeader(Constants.CURRENT_ID);
        Map<String,String> map = dbUserService.getUserData(Long.valueOf(userId));

        return map == null? R.error():R.data(map);
    }

    /**
     * 修改个人资料
     * @param request
     * @param dbUser
     * @return
     */
    @PutMapping("UpdateUserData")
    public R UpdateUserData(HttpServletRequest request,DbUser dbUser){
        String userId = request.getHeader(Constants.CURRENT_ID);
        if (dbUser != null){
            dbUser.setId(Long.valueOf(userId));
            int i = dbUserService.updateDbUser(dbUser);
            return i>0 ? new R():R.error(ResultEnum.SERVICE_BUSY.getCode(),ResultEnum.SERVICE_BUSY.getMessage());
        }
        return R.error();
    }

}
