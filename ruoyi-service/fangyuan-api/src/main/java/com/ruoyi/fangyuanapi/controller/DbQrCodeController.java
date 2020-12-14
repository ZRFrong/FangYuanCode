package com.ruoyi.fangyuanapi.controller;

import cn.hutool.db.Db;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbQrCode;
import com.ruoyi.system.domain.DbQrCodeVo;
import com.ruoyi.system.feign.SendSmsClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.service.IDbQrCodeService;

import java.util.List;

/**
 * 二维码 提供者
 *
 * @author zheng
 * @date 2020-09-30
 */
@RestController
@Api("二维码")
@RequestMapping("qrcode")
public class DbQrCodeController extends BaseController {

    @Autowired
    private IDbQrCodeService dbQrCodeService;

    @Autowired
    private SendSmsClient sendSmsClient;

    @Autowired
    private IDbLandService dbLandService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{qrCodeId}")
    public DbQrCode get(@PathVariable("qrCodeId") Long qrCodeId) {
        return dbQrCodeService.selectDbQrCodeById(qrCodeId);

    }

    /**
     * 查询二维码列表
     */
    @GetMapping("list")
    public R list(DbQrCode dbQrCode) {
        startPage();
        return result(dbQrCodeService.selectDbQrCodeList(dbQrCode));
    }


    /**
     * 新增保存二维码
     */
    @PostMapping("save")
    public R addSave(@RequestBody DbQrCode dbQrCode) {
        return toAjax(dbQrCodeService.insertDbQrCode(dbQrCode));
    }

    /**
     * 修改保存二维码
     */
    @PostMapping("update")
    public R editSave(@RequestBody DbQrCode dbQrCode) {
        return toAjax(dbQrCodeService.updateDbQrCode(dbQrCode));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(dbQrCodeService.deleteDbQrCodeByIds(ids));
    }


    /*
     * 生成二维码   指定网页，内部选择土地，
     * */
    @PostMapping("qrCodeGenerate")
    @ApiOperation(value = "生成二维码", notes = "生成二维码")
    public R qrCodeGenerate(@ApiParam(name = "DbEquipment", value = "传入json格式", required = true) DbQrCode dbQrCode) throws Exception {
        /*
         * 指定网址     拼接一个参数（设备id）
         * */
        String s = dbQrCodeService.qrCodeGenerate(dbQrCode);
        if (StringUtils.isEmpty(s)) {
            return R.error("生成失败");
        } else {
            return R.data(s);
        }
    }


    /*
     *二维码扫码进入页面后调用接口
     * */
    @GetMapping("qrCodeGenerate")
    @ApiOperation(value = "扫码进入页面调用", notes = "扫码进入页面调用")
    public R qrCodeInfo(@ApiParam(name = "token", value = "登录信息唯一码", required = true) String token, @ApiParam(name = "qrCodeId", value = "二维码id", required = true) String qrCodeId) throws Exception {
        /*
         * 需要回写   二维码表+ 设备信息
         * */
        DbQrCodeVo dbQrCodeVo = dbQrCodeService.qrCodeInfo(token, qrCodeId);
        if (dbQrCodeVo == null) {
            return R.error("token识别错误,用户未找到");
        }
        return R.data(dbQrCodeVo);
    }

    /*
     *点击选择操作集
     * */

    @PostMapping("banDingEquipment")
    @ApiOperation(value = "绑定设备接口",notes = "绑定设备接口",httpMethod = "POST" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dbLandId",value = "土地id",required = true),
            @ApiImplicitParam(name = "dbEquipmentId",value = "设备id",required = true),
            @ApiImplicitParam(name = "handleText",value = "操作集 json",required = true),
            @ApiImplicitParam(name = "phone",value = "手机号",required = true),
            @ApiImplicitParam(name = "code",value = "验证码",required = true)
    })
    public R banDingEquipment(@RequestParam Long dbLandId,@RequestParam Long dbEquipmentId,@RequestParam String handleText,@RequestParam String phone,@RequestParam String code){
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        R r = sendSmsClient.checkCode(phone, code);
        code = r.get("code")+"";
        if ("200".equals(code)){
            List<DbLand> dbLands = dbLandService.selectDbLandListByUserId(Long.valueOf(userId));
            if (dbLands != null && dbLands.size()>0){
                for (DbLand land : dbLands) {
                    if (land.getEquipmentIds().contains(dbEquipmentId+"")){
                        return R.error("您已经绑定过该设备了！");
                    }
                }
            }
            boolean b = dbQrCodeService.banDingEquipment(dbLandId, dbEquipmentId, Long.valueOf(userId), handleText);
            return b?R.ok("绑定成功！"):R.error();
        }
        return null;
    }

}
