package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 二维码对象 db_qr_code
 * 
 * @author zheng
 * @date 2020-09-30
 */
@ApiModel
public class DbQrCode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long qrCodeId;

    /** 心跳名称 */
    @Excel(name = "心跳名称")
    @ApiModelProperty(value = "心跳名称")
    private String heartbeatText;

    /** 首次绑定时间 */
    @Excel(name = "首次绑定时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "首次绑定时间")
    private Date firstTimeBinding;

    /** 管理员用户id */
    @Excel(name = "管理员用户id")
    @ApiModelProperty(value = "管理员用户id")
    private Long adminUserId;

    /** 设备id */
    @Excel(name = "设备id")
    @ApiModelProperty(value = "设备id")
    private Long equipmentId;

    /** 二维码链接地址 */
    @Excel(name = "二维码链接地址")
    @ApiModelProperty(value = "二维码链接地址")
    private String qrCodeUrl;

    /** 二维码图片地址 */
    @Excel(name = "二维码图片地址")
    @ApiModelProperty(value = "二维码图片地址")
    private String qrCodePic;

    public void setQrCodeId(Long qrCodeId) 
    {
        this.qrCodeId = qrCodeId;
    }

    public Long getQrCodeId() 
    {
        return qrCodeId;
    }
    public void setHeartbeatText(String heartbeatText) 
    {
        this.heartbeatText = heartbeatText;
    }

    public String getHeartbeatText() 
    {
        return heartbeatText;
    }
    public void setFirstTimeBinding(Date firstTimeBinding) 
    {
        this.firstTimeBinding = firstTimeBinding;
    }

    public Date getFirstTimeBinding() 
    {
        return firstTimeBinding;
    }
    public void setAdminUserId(Long adminUserId) 
    {
        this.adminUserId = adminUserId;
    }

    public Long getAdminUserId() 
    {
        return adminUserId;
    }
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setQrCodeUrl(String qrCodeUrl) 
    {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getQrCodeUrl() 
    {
        return qrCodeUrl;
    }
    public void setQrCodePic(String qrCodePic) 
    {
        this.qrCodePic = qrCodePic;
    }

    public String getQrCodePic() 
    {
        return qrCodePic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("qrCodeId", getQrCodeId())
            .append("heartbeatText", getHeartbeatText())
            .append("createTime", getCreateTime())
            .append("firstTimeBinding", getFirstTimeBinding())
            .append("adminUserId", getAdminUserId())
            .append("equipmentId", getEquipmentId())
            .append("qrCodeUrl", getQrCodeUrl())
            .append("qrCodePic", getQrCodePic())
            .toString();
    }
}
