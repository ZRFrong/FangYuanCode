package com.ruoyi.system.domain;

import lombok.*;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 前台用户对象 db_user
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Getter
    @Setter
    private Long id;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    /** 用户密码 */
    @Excel(name = "用户密码")
    @ApiModelProperty(value = "用户密码")
    private String password;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    @ApiModelProperty(value = "用户手机号")
    private String phone;

    /** 盐（uuid） */
    @Excel(name = "盐", readConverterExp = "u=uid")
    @ApiModelProperty(value = "盐")
    private String salt;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date created;

//    /** 0:小程序 1：App */
//    @Excel(name = "0:小程序 1：App")
//    @ApiModelProperty(value = "0:小程序 1：App")
//    private Integer userType;

    /** 0：男 1：女 2：保密 */
    @Excel(name = "0：男 1：女 2：保密")
    @ApiModelProperty(value = "0：男 1：女 2：保密")
    private Integer gender;

    /** 头像路径 */
    @Excel(name = "头像路径")
    @ApiModelProperty(value = "头像路径")
    private String avatar;

    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "最后登录时间")
    private Date laterLoginTime;

    /** 最后登录ip */
    @Excel(name = "最后登录ip")
    @ApiModelProperty(value = "最后登录ip")
    private String laterLoginIp;

    /** 备注信息 */
    @Excel(name = "备注信息")
    @ApiModelProperty(value = "备注信息")
    private String remarkText;

    /** 0: 小程序 1：App */
    @Excel(name = "0: 小程序 1：App")
    @ApiModelProperty(value = "0: 小程序 1：App")
    private Integer userFrom;

    /** token*/
    @Excel(name = "token ")
    @ApiModelProperty(value = "token")
    private String token;

    /** openId */
    @Excel(name = "openId")
    @ApiModelProperty(value = "openId")
    private String openId;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /** 是否封禁 0：否 1：是 */
    @Excel(name = "是否封禁 0：否 1：是")
    @ApiModelProperty(value = "是否封禁 0：否 1：是")
    private Integer isBanned;

    /** 年龄 */
    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /** 签名：限制30个字符 */
    @Excel(name = "签名：限制30个字符")
    @ApiModelProperty(value = "签名：限制30个字符")
    private String signature;

    /** 生日 */
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /** 手机号是否验证：0：是 1：否 */
    @Excel(name = "手机号是否验证：0：是 1：否")
    @ApiModelProperty(value = "手机号是否验证：0：是 1：否")
    private Integer phoneIsVerify;

    @Excel(name = "动态数量")
    @ApiModelProperty(value = "用户发布的动态数量")
    private Integer dynamicNum;

    @Excel(name = "关注")
    @ApiModelProperty(value = "用户关注其他用户的数量")
    private Integer attentionNum;

    @Excel(name = "粉丝数量")
    @ApiModelProperty(value = "该用户被其他用户关注的次数  OR  粉丝数量")
    private Integer replyAttentionUserNum;


}