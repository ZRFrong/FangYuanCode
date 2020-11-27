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

    /** 生产地点 */
    @Excel(name = "生产地点")
    @ApiModelProperty(value = "生产地点")
    private String equipmentProductionSite;


    /** 产品型号 */
    @Excel(name = "产品型号")
    @ApiModelProperty(value = "生产批次")

    private String equipmentProductionBatch;

    /** 产品型号 */
    @Excel(name = "生产日期")
    @ApiModelProperty(value = "产品型号")
    private String equipmentProductionDate;

    /** 产品型号 */
    @Excel(name = "产品型号")
    @ApiModelProperty(value = "产品型号")
    private String equipmentModel;

    @Override
    public String toString() {
        return "DbQrCode{" +
                "qrCodeId=" + qrCodeId +
                ", heartbeatText='" + heartbeatText + '\'' +
                ", firstTimeBinding=" + firstTimeBinding +
                ", adminUserId=" + adminUserId +
                ", equipmentId=" + equipmentId +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", qrCodePic='" + qrCodePic + '\'' +
                ", equipmentProductionSite='" + equipmentProductionSite + '\'' +
                ", equipmentProductionBatch='" + equipmentProductionBatch + '\'' +
                ", equipmentProductionDate='" + equipmentProductionDate + '\'' +
                ", equipmentModel='" + equipmentModel + '\'' +
                '}';
    }

    public String getEquipmentProductionSite() {
        return equipmentProductionSite;
    }

    public void setEquipmentProductionSite(String equipmentProductionSite) {
        this.equipmentProductionSite = equipmentProductionSite;
    }

    public String getEquipmentProductionBatch() {
        return equipmentProductionBatch;
    }

    public void setEquipmentProductionBatch(String equipmentProductionBatch) {
        this.equipmentProductionBatch = equipmentProductionBatch;
    }

    public String getEquipmentProductionDate() {
        return equipmentProductionDate;
    }

    public void setEquipmentProductionDate(String equipmentProductionDate) {
        this.equipmentProductionDate = equipmentProductionDate;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }




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

}
