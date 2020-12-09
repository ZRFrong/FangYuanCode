package com.ruoyi.fangyuanapi.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.config.RedisTimeConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.HttpUtil;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.aes.TokenUtils;
import com.ruoyi.common.utils.md5.ZhaoMD5Utils;
import com.ruoyi.common.utils.sms.CategoryType;
import com.ruoyi.common.utils.sms.PhoneUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.conf.TokenConf;
import com.ruoyi.fangyuanapi.conf.WxSmallConf;
import com.ruoyi.fangyuanapi.service.*;
import com.ruoyi.system.domain.DbUser;
import com.ruoyi.fangyuanapi.dto.DynamicDto;
import com.ruoyi.system.domain.DbUserLogin;
import com.ruoyi.system.feign.SendSmsClient;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("wxUser")
public class DbUserController extends BaseController {

//
//    @Autowired
//    private RemoteDeptService remoteDeptService;

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

    @Autowired
    private WxSmallConf wxSmallConf;

    /**
     * 退出登陆
     *
     * @return
     */
    @GetMapping("exitLogin")
    @ApiOperation(value = "退出登陆接口！",notes = "退出登陆",httpMethod = "GET")
    public R exitLogin() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
//        DbUserLogin login = dbUserLoginService.selectDbUserLoginByUserId(Long.valueOf(userId));
//        if (login == null){
//            return null;
//        }
//        /* 修改离线状态 */
//        login.setStatus(1);
//        int i = dbUserLoginService.updateDbUserLogin(login);
        int i = 1;
        redisUtils.delete(RedisKeyConf.REFRESH_TOKEN_.name() + userId);
        return i > 0 ? R.data("您已退出登陆！") : R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage());
    }

    /**
     * 修改密码
     *
     * @param phone
     * @param code
     * @param password
     * @return
     */
    @PutMapping("appUpdatePassword")
    @ApiOperation(value = "修改密码接口",notes = "修改密码",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "手机号",required = true),
            @ApiImplicitParam(name = "code",value = "验证码",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true),
            @ApiImplicitParam(name = "passwordAgain",value = "确认密码",required = true)
    })
    public R appUpdatePassword(String phone, String code, String password,String passwordAgain) {
        if (StringUtils.isNotEmpty(phone) && PhoneUtils.checkPhone(phone) && StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(passwordAgain)) {
            if (!StringUtils.checkPassword(password)){
                return R.error(ResultEnum.PASSWORD_NOT_RULE.getCode(),ResultEnum.PASSWORD_NOT_RULE.getMessage());
            }
            if (!password.equals(passwordAgain)){
                return R.error();
            }
            R r = sendSmsClient.checkCode(phone, code);
            if ("200".equals(r.get("code"))){
                String uuid = StringUtils.getUUID();
                String s = ZhaoMD5Utils.string2MD5(password + uuid);
                int i =  dbUserService.updateUserPassword(phone,s,uuid);
                if (i>0){
                    DbUser user = new DbUser();
                    user.setPhone(phone);
                    dbUserService.selectDbUserByPhone(user);
                    redisUtils.delete(RedisKeyConf.ACCESS_TOKEN_.name() + user.getId());
                    return  R.ok("修改密码成功,请重新登陆!");
                }
                return R.error();
            }
            return r;
        }
        return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
    }


    /**
     * app用户登陆
     *
     * @param phone    手机号
     * @param password 密码
     * @param code     验证码
     * @return 用户登陆
     */
    @PostMapping("appLogin")
    @ApiOperation(value = "APP用户登录接口",notes = "密码和code 二选一",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "手机号",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = false),
            @ApiImplicitParam(name = "code",value = "验证码",required = false)
    })
    public R appLogin(String phone, String password, String code) {
        DbUser dbUser = null;
        String s = redisUtils.get(CategoryType.PHONE_LOGIN_NUM_.name() + phone);
        Integer num = s == null ? 0 : Integer.valueOf(s);
        if (5 < Integer.valueOf(s == null ? "0" : s)) {//登录频繁
            return R.error(ResultEnum.LOGIN_HOUR_ERROR.getCode(), ResultEnum.LOGIN_HOUR_ERROR.getMessage());
        }
        if (phone != null && PhoneUtils.checkPhone(phone)) {
            DbUser user = new DbUser();
            user.setPhone(phone);
            dbUser = dbUserService.selectDbUserByPhone(user);
            redisUtils.set(CategoryType.PHONE_LOGIN_NUM_.name() + phone, 1, RedisTimeConf.ONE_HOUR);
            if (dbUser == null) {
                return R.error(ResultEnum.PHONE_NOT_REGISTER.getCode(), ResultEnum.PHONE_NOT_REGISTER.getMessage());
            }
        } else {
            return R.error(ResultEnum.PHONE_ERROR.getCode(), ResultEnum.PHONE_ERROR.getMessage());
        }
        String token = redisUtils.get(RedisKeyConf.REFRESH_TOKEN_.name() + dbUser.getId());

        if (dbUser != null && StringUtils.isNotEmpty(password) && StringUtils.isEmpty(code)) {
            //密码登录
            String salt = dbUser.getSalt();
            if (StringUtils.isEmpty(dbUser.getPassword())){
                return R.error(ResultEnum.PASSWORD_IS_NULL.getCode(),ResultEnum.PASSWORD_IS_NULL.getMessage());
            }
            if (ZhaoMD5Utils.string2MD5(password + salt).equals(dbUser.getPassword())) {
                if (StringUtils.isNotEmpty(token)){
                    redisUtils.delete(RedisKeyConf.ACCESS_TOKEN_.name() + dbUser.getId());
                }
                //登录成功
                token = getToken(dbUser.getId(), tokenConf.getAccessTokenKey(), System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 1000),null);
                /* 返回token 并且记录 */
                redisUtils.set(RedisKeyConf.ACCESS_TOKEN_.name() + dbUser.getId(), token);
                return R.data(token);
            } else {
                redisUtils.set(CategoryType.PHONE_LOGIN_NUM_.name() + phone, num + 1, RedisTimeConf.ONE_HOUR);
                return R.error(ResultEnum.PASSWORD_ERROE.getCode(), ResultEnum.PASSWORD_ERROE.getMessage());
            }
        }

        if (dbUser != null && StringUtils.isEmpty(password) && StringUtils.isNotEmpty(code)) {//验证码登录
            R r = sendSmsClient.checkCode(phone, code);
            if ("200".equals(r.get("code") + "")) {
                if (StringUtils.isNotEmpty(token)){
                    redisUtils.delete(RedisKeyConf.ACCESS_TOKEN_.name() + dbUser.getId());
                }
                token = getToken(dbUser.getId(), tokenConf.getAccessTokenKey(), System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 1000),null);
                redisUtils.set(RedisKeyConf.ACCESS_TOKEN_.name() + dbUser.getId(),token);
                return R.data(token);
            } else {
                redisUtils.set(CategoryType.PHONE_LOGIN_NUM_.name() + phone, num + 1, RedisTimeConf.ONE_HOUR);
                return r;
            }
        }

        return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
    }

    /**
     * @return
     */
    @PostMapping("refreshToken")
    @ApiOperation(value = "刷新token接口",notes = "刷新token",httpMethod = "POST")
    public R refreshToken() {
        String accessToken = getRequest().getHeader(Constants.TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }
        Map<String, Object> map = TokenUtils.verifyToken(accessToken, tokenConf.getRefreshTokenKey());
        if (map == null) {
            return R.error(ResultEnum.REFRESH_TOKEN_LOSE.getCode(), ResultEnum.REFRESH_TOKEN_LOSE.getMessage());
        }
        String id = map.get("id") + "";
        Map<String, String> tokens = resultTokens(Long.valueOf(id), "XIAOSI", tokenConf.getAccessTokenKey(), tokenConf.getRefreshTokenKey());
        redisUtils.set(RedisKeyConf.REFRESH_TOKEN_.name() + id, map.get("refreshToken"), 60 * 60 * 24 * 30 * 2L);
        return R.data(tokens);
    }


    /**
     * app注册
     *
     * @param phone         手机号
     * @param password      密码
     * @param passwordAgain 确认密码
     * @param code          验证码
     * @return token
     */
    @PostMapping("appRegister")
    @ApiOperation(value = "APP注册接口",notes = "APP注册",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "手机号",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true),
            @ApiImplicitParam(name = "passwordAgain",value = "确认密码",required = true),
            @ApiImplicitParam(name = "code",value = "验证码",required = true),
    })
    public R appRegister(String phone, String password, String passwordAgain, String code) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordAgain) || StringUtils.isEmpty(code)) {
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(), ResultEnum.PARAMETERS_ERROR.getMessage());
        }
        if (!password.equals(passwordAgain)) {//不做提示
            return R.error();
        }
        if (!StringUtils.checkPassword(password)) {
            return R.error(ResultEnum.PASSWORD_NOT_RULE.getCode(), ResultEnum.PASSWORD_NOT_RULE.getMessage());
        }
        R r = sendSmsClient.checkCode(phone, code);
        if (!"200".equals(r.get("code") + "")) {
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
            return i > 0 ? R.data("注册成功，请前去登陆吧！") : R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage());
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
     * @param phone
     * @return R { 0 : SUCCESS}
     */
    @PostMapping("smallRegister")
    @ApiOperation(value = "小程序注册",notes = "小程序注册",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "电话号",required = true),
            @ApiImplicitParam(name = "code",value = "验证码",required = true),
            @ApiImplicitParam(name = "openId",value = "openId",required = true),
            @ApiImplicitParam(name = "nickname",value = "昵称",required = false),
            @ApiImplicitParam(name = "avatar",value = "头像",required = false)
    })
    public R wxRegister(String phone,String code,String openId,String nickname,String avatar) {
        R r = sendSmsClient.checkCode(phone, code);
        //R r = new R();
        if ("200".equals(r.get("code")+"") ){
            DbUser dbUser= dbUserService.selectDbUserByPhoneAndOpenId(phone,openId);

            if (dbUser == null){
                //already
                dbUser = dbUserService.wxRegister(phone,openId,nickname,avatar);
            }
            if (dbUser != null){
                return R.data(getToken(dbUser.getId(), tokenConf.getAccessTokenKey(),System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 1000),0));
            }
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
        }else {
            return r;
        }
    }


    /**
     * 获取openId
     * @param code
     * @return
     */
    @PostMapping("getOpenId")
    @ApiOperation(value = "获取openId接口",notes = "前端传入code，后端换取openid，服务端返回自己的token",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "由微信颁发的临时凭证code，有效期五分钟", required = true)
    })
    public R getOpenId(String code){
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("appid",wxSmallConf.getAppid());
            map.put("secret",wxSmallConf.getSecret());
            map.put("js_code",code);
            map.put("grant_type",wxSmallConf.getGrant_type());
            HttpResponse response = HttpUtil.doGet(wxSmallConf.getHost(), wxSmallConf.getPath(), HttpMethod.GET.name(), null, map);
            System.out.println(response.toString());
            String string = EntityUtils.toString(response.getEntity());
            Map result = JSONUtil.toBean(string, Map.class);
            String openid;
            try {
                openid = result.get("openid").toString();
            }catch (Exception e){
                openid =null;
            }
            Map<String, String> stringMap = new HashMap<>();
            if (StringUtils.isNotEmpty(openid)){
                DbUser user = dbUserService.selectDbUserByOpenId(openid);
                if (user != null){
                    stringMap.put("token",getToken(user.getId(), tokenConf.getAccessTokenKey(),System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 1000),0));
                    return  R.data(stringMap) ;
                }
                stringMap.put("openId",openid);
                return  R.data(stringMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    private String getToken(Long id, String key, Long time, Integer type){
        HashMap<Object, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", id);
        tokenMap.put("expireTime", time);
        tokenMap.put("topic", 0);
        if (type != null){
            tokenMap.put("type",0);
        }
        return TokenUtils.encrypt(JSON.toJSONString(tokenMap), key);
    }

    /**
     * 在用户点击我的页面时触发，如果用户存在则返回数据，无则去注册
     *
     * @return
     */
    @GetMapping("userInfo")
    @ApiOperation(value = "我的页面接口",notes = "我的页面",httpMethod = "GET")
    public R getUserInfo() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbUser dbUser = dbUserService.selectDbUserById(Long.valueOf(userId));
        HashMap<String, Object> map = new HashMap<>();
        map.put("nickname",dbUser.getNickname());
        map.put("avatar",dbUser.getAvatar());
        map.put("dynamicNum",dbUser.getDynamicNum() );
        map.put("attentionNum",dbUser.getAttentionNum());
        map.put("replyAttentionUserNum",dbUser.getReplyAttentionUserNum());
        return R.data(map);
    }

    /**
     * 我的动态
     *
     * @return
     */
    @GetMapping("dynamic/{currPage }")
    @ApiOperation(value = "我的动态接口",notes = "我的动态",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currPage",value = "页码，默认:1",required = true)
    })
    public R getUserDynamic(@PathVariable Integer currPage) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbUser dbUser = dbUserService.selectDbUserById(Long.valueOf(userId));
        if (dbUser == null) {
            //此处不做返回，来到此处的皆为绕过前面接口直接来的
            return null;
        }
        currPage = currPage < 0 ? 0:(currPage -1) * PageConf.pageSize;
        List<DynamicDto> dynamic = dbUserService.getUserDynamic(dbUser, currPage, PageConf.pageSize);
        return R.data(dynamic);
    }

    /**
     * 我的赞
     *
     * @return
     */
    @GetMapping("giveLikeNum")
    @ApiOperation(value = "我的赞接口",notes = "我的赞",httpMethod = "GET")
    public R getGiveLikeSum() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        Integer likeNum = dbGiveLikeService.selectUserGiveLikeNum(userId);
        return likeNum != null ? new R() : R.error();
    }

    /**
     * 我的相册
     *
     * @param currPage
     * @return list<string>
     */
    @GetMapping("photoAlbum/{currPage}")
    @ApiOperation(value = "个人相册",notes = "个人相册",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currPage",value = "页码，默认:1",required = true)
    })
    public R getPhotoAlbum(@PathVariable Integer currPage) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        currPage = currPage < 0 ? 0:(currPage -1) * PageConf.pageSize;
        List<Long> dynamicIds = dbUserAndDynamicService.selectDbUserAndDynamicByUserId(Long.valueOf(userId));
        if (dynamicIds == null || dynamicIds.size() <= 0) {
            return R.ok("您的相册为空！");
        }
        List<Map<String, String>> PhotoAlbum = dbUserDynamicService.selectImagesByDynamicId(dynamicIds, currPage,PageConf.pageSize);
        return R.data(PhotoAlbum);
    }

    /**
     * 获取个人资料接口
     *
     * @param request
     * @return
     */
    @GetMapping("getUserData")
    @ApiOperation(value = "个人资料接口",notes = "个人资料页面",httpMethod = "GET")
    public R getUserDate(HttpServletRequest request) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        Map<String, String> map = dbUserService.getUserData(Long.valueOf(userId));
        return map == null ? R.error() : R.data(map);
    }

    /**
     * 修改个人资料
     *
     * @param request
     * @param dbUser
     * @return
     */
    @PutMapping("UpdateUserData")
    @ApiOperation(value = "修改个人资料",notes = "修改个人资料接口", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "duUser",value = "user对象中的部分属性",required = true)
    })
    public R UpdateUserData(HttpServletRequest request, DbUser dbUser) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        if (dbUser != null) {
            dbUser.setId(Long.valueOf(userId));
            int i = dbUserService.updateDbUser(dbUser);
            return i > 0 ? new R() : R.error(ResultEnum.SERVICE_BUSY.getCode(), ResultEnum.SERVICE_BUSY.getMessage());
        }
        return R.error();
    }

    private void insertLoginStatus(DbUserLogin login, Long id, Integer userFrom) {
        HttpServletRequest request = getRequest();
        if (login != null) {
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

    private Map<String, String> resultTokens(Long id, String publisher, String accessTokenKey, String refreshTokenKey) {
        HashMap<String, String> map = new HashMap<>();
        Long expireTime = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 60);
        String accessToken = TokenUtils.getToken(id, expireTime, publisher, "accessToken", accessTokenKey);
        map.put("accessToken", accessToken);
        Long refreshTime = System.currentTimeMillis() + (1000 * 60 * 60 * 30);
        String refreshToken = TokenUtils.getToken(id, refreshTime, publisher, "refreshToken", refreshTokenKey);
        map.put("refreshToken", refreshToken);
        return map;
    }

    public static void main(String[] args) {
        System.out.println("3446DEFDFE916E14E7ACB44CC7D25E70B8A1E005AD64D2703AB1D78C9B8D0B7B2A9D829E9EA2E567740EB56CE1175A96B8C3C46804CE41607C954857B597F0C31ADFBDA85FE5530F7D97601A436586EF".length());
    }
}
