package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.PassDemo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.aes.TokenUtils;
import com.ruoyi.common.utils.md5.ZhaoMD5Utils;
import com.ruoyi.common.utils.sms.CategoryType;
import com.ruoyi.common.utils.sms.PhoneUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.conf.TokenConf;
import com.ruoyi.fangyuanapi.service.*;
import com.ruoyi.system.domain.DbUser;
import com.ruoyi.fangyuanapi.dto.DynamicDto;
import com.ruoyi.system.domain.DbUserLogin;
import com.ruoyi.system.feign.RemoteDeptService;
import com.ruoyi.system.feign.SendSmsClient;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wxUser")
public class DbUserController extends BaseController {


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

    @Autowired
    private TokenConf tokenConf;

    @Autowired
    private IDbUserLoginService dbUserLoginService;

    /**
     * 退出登陆
     * @return
     */
    @GetMapping("exitLogin")
    public R exitLogin(){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
//        DbUserLogin login = dbUserLoginService.selectDbUserLoginByUserId(Long.valueOf(userId));
//        if (login == null){
//            return null;
//        }
//        /* 修改离线状态 */
//        login.setStatus(1);
//        int i = dbUserLoginService.updateDbUserLogin(login);
        int i = 1;
        redisUtils.delete(RedisKeyConf.REFRESH_TOKEN_.name()+userId);
        return i>0 ? R.data("您已退出登陆！") : R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage()) ;
    }

    /**
     * 修改密码
     * @param phone
     * @param code
     * @param password
     * @return
     */
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
        String token = redisUtils.get(RedisKeyConf.REFRESH_TOKEN_.name() + dbUser.getId());
        if (token != null){
            return R.error(ResultEnum.LOGIN_ERROR.getCode(),ResultEnum.LOGIN_ERROR.getMessage());
        }
        if (dbUser!= null  && StringUtils.isNotEmpty(password) && StringUtils.isEmpty(code)){
            //密码登录
            String salt = dbUser.getSalt();
            if (ZhaoMD5Utils.string2MD5(password+salt).equals(dbUser.getPassword())){
                //登录成功
                Map<String, String> map = resultTokens(dbUser.getId(), "XIAOSI", tokenConf.getAccessTokenKey(),tokenConf.getRefreshTokenKey());
                /* 返回token 并且记录 */
                redisUtils.set(RedisKeyConf.REFRESH_TOKEN_.name()+dbUser.getId(),map.get("refreshToken"),RedisTimeConf.ONE_MONTH);
                //TODO
                return R.data(map);
            }else {
                redisUtils.set(CategoryType.PHONE_LOGIN_NUM_.name() + phone,num+1,RedisTimeConf.ONE_HOUR);
                return R.error(ResultEnum.PASSWORD_ERROE.getCode(),ResultEnum.PASSWORD_ERROE.getMessage());
            }
        }

        if (dbUser!= null  && StringUtils.isEmpty(password) && StringUtils.isNotEmpty(code)){//验证码登录
            R r = sendSmsClient.checkCode(phone, code);
            if ("200".equals(r.get("code")+"")){
                Map<String, String> map = resultTokens(dbUser.getId(), "XIAOSI", tokenConf.getAccessTokenKey(),tokenConf.getRefreshTokenKey());
                redisUtils.set(RedisKeyConf.REFRESH_TOKEN_.name()+dbUser.getId(),map.get("refreshToken"),RedisTimeConf.ONE_MONTH);
                return R.data(map);
            }else {
                redisUtils.set(CategoryType.PHONE_LOGIN_NUM_.name() + phone,num+1,RedisTimeConf.ONE_HOUR);
                return r;
            }
        }

        return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
    }

    @PostMapping("refreshToken")
    public R refreshToken(){
        String accessToken = getRequest().getHeader(Constants.TOKEN);
        if (StringUtils.isEmpty(accessToken)){
            return null;
        }
        Map<String, Object> map = TokenUtils.verifyToken(accessToken, tokenConf.getRefreshTokenKey());
        if (map == null){
            return R.error(ResultEnum.REFRESH_TOKEN_LOSE.getCode(),ResultEnum.REFRESH_TOKEN_LOSE.getMessage());
        }
        String id = map.get("id") + "";
        Map<String, String> tokens = resultTokens(Long.valueOf(id), "XIAOSI", tokenConf.getAccessTokenKey(),tokenConf.getRefreshTokenKey());
        return R.data(tokens);
    }


    /**
     * app注册
     * @param phone 手机号
     * @param password 密码
     * @param passwordAgain 确认密码
     * @param code 验证码
     * @return token
     */
    @PostMapping("appRegister")
    public R appRegister(String phone, String password, String passwordAgain, String code) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordAgain) || StringUtils.isEmpty(code)) {
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        if (!password.equals(passwordAgain)) {//不做提示
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
        if (dbUser == null) {
            //未在方圆村其他地方注册过，新增用户
            user.setSalt(uuid);
            user.setPassword(s);
            user.setCreateTime(new Date());
            user.setUserFrom(1);
            user.setPhoneIsVerify(0);
            user.setNickname(phone);
            int i = dbUserService.insertDbUser(user);
            return i>0 ? R.data("注册成功，请前去登陆吧！") : R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage()) ;
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

    /**
     * 在用户点击我的页面时触发，如果用户存在则返回数据，无则去注册
     *
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
     *
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
     *
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

    private void insertLoginStatus(DbUserLogin login,Long id ,Integer userFrom){
        HttpServletRequest request = getRequest();
        if (login != null){
            login.setStatus(0);
            int i = dbUserLoginService.updateDbUserLogin(login);
            return;
        }
        login = new DbUserLogin();
        login.setLoginId(id);
        login.setLoginFrom(userFrom);
        login.setLoginTime(new Date());
        login.setLocationInfo(IpUtils.getIpAddr(request));
        dbUserLoginService.insertDbUserLogin(login);
    }

    private Map<String ,String> resultTokens(Long id,String publisher,String accessTokenKey,String refreshTokenKey){
        HashMap<String, String> map = new HashMap<>();
        Long expireTime = System.currentTimeMillis()+(1000*60*60*24*60);
        String accessToken = TokenUtils.getToken(id, expireTime, publisher, "accessToken", accessTokenKey);
        map.put("accessToken",accessToken);
        Long refreshTime = System.currentTimeMillis()+(1000*60*60*30);
        String refreshToken = TokenUtils.getToken(id, refreshTime, publisher, "refreshToken", refreshTokenKey);
        map.put("refreshToken",refreshToken);
        return map;
    }

    public static void main(String[] args){
        System.out.println();
    }
}
