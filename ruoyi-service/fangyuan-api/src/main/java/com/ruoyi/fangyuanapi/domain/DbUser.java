package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
public class DbUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
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

    /** 0:小程序 1：App */
    @Excel(name = "0:小程序 1：App")
    @ApiModelProperty(value = "0:小程序 1：App")
    private Integer userType;

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

    /** token / openId */
    @Excel(name = "token / openId")
    @ApiModelProperty(value = "token / openId")
    private String token;

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

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getSalt()
    {
        return salt;
    }
    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getCreated()
    {
        return created;
    }
    public void setUserType(Integer userType)
    {
        this.userType = userType;
    }

    public Integer getUserType()
    {
        return userType;
    }
    public void setGender(Integer gender)
    {
        this.gender = gender;
    }

    public Integer getGender()
    {
        return gender;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setLaterLoginTime(Date laterLoginTime)
    {
        this.laterLoginTime = laterLoginTime;
    }

    public Date getLaterLoginTime()
    {
        return laterLoginTime;
    }
    public void setLaterLoginIp(String laterLoginIp)
    {
        this.laterLoginIp = laterLoginIp;
    }

    public String getLaterLoginIp()
    {
        return laterLoginIp;
    }
    public void setRemarkText(String remarkText)
    {
        this.remarkText = remarkText;
    }

    public String getRemarkText()
    {
        return remarkText;
    }
    public void setUserFrom(Integer userFrom)
    {
        this.userFrom = userFrom;
    }

    public Integer getUserFrom()
    {
        return userFrom;
    }
    public void setToken(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }
    public void setIsBanned(Integer isBanned)
    {
        this.isBanned = isBanned;
    }

    public Integer getIsBanned()
    {
        return isBanned;
    }
    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Integer getAge()
    {
        return age;
    }
    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public String getSignature()
    {
        return signature;
    }
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Date getBirthday()
    {
        return birthday;
    }
    public void setPhoneIsVerify(Integer phoneIsVerify)
    {
        this.phoneIsVerify = phoneIsVerify;
    }

    public Integer getPhoneIsVerify()
    {
        return phoneIsVerify;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userName", getUserName())
                .append("password", getPassword())
                .append("phone", getPhone())
                .append("salt", getSalt())
                .append("created", getCreated())
                .append("userType", getUserType())
                .append("gender", getGender())
                .append("avatar", getAvatar())
                .append("laterLoginTime", getLaterLoginTime())
                .append("laterLoginIp", getLaterLoginIp())
                .append("updateTime", getUpdateTime())
                .append("remarkText", getRemarkText())
                .append("userFrom", getUserFrom())
                .append("token", getToken())
                .append("nickname", getNickname())
                .append("isBanned", getIsBanned())
                .append("age", getAge())
                .append("signature", getSignature())
                .append("birthday", getBirthday())
                .append("phoneIsVerify", getPhoneIsVerify())
                .toString();
    }
}