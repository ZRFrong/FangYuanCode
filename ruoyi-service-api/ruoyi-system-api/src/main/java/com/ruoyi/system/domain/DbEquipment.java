package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.util.List;


/**
 * 设备对象 db_equipment
 *
 * @author zheng
 * @date 2020-09-30
 */


@ApiModel
public class DbEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public DbEquipment(Integer isPause,Integer isFault) {
        this.isPause = isPause;
        this.isFault = isFault;
    }

    /** 主键 */

    private Long equipmentId;


    /*
    *
    * */

    private List<OperatePojo> pojos;

    public DbEquipment() {
    }

    public List<OperatePojo> getPojos() {
        return pojos;
    }

    public void setPojos(List<OperatePojo> pojos) {
        this.pojos = pojos;
    }

    public DbEquipment(Long equipmentId, List<OperatePojo> pojos, String heartbeatText, Date allottedTime, Integer isPause, String pauseState, String equipmentName, Integer isFault, Long equipmentTemplateId, Integer equipmentNo, String handlerText, Integer isOnline, String qrCodePic) {
        this.equipmentId = equipmentId;
        this.pojos = pojos;
        this.heartbeatText = heartbeatText;
        this.allottedTime = allottedTime;
        this.isPause = isPause;
        this.pauseState = pauseState;
        this.equipmentName = equipmentName;
        this.isFault = isFault;
        this.equipmentTemplateId = equipmentTemplateId;
        this.equipmentNo = equipmentNo;
        this.handlerText = handlerText;
        this.isOnline = isOnline;
        this.qrCodePic = qrCodePic;
    }

    /** 心跳名称 */

    @Excel(name = "心跳名称")
    @ApiModelProperty(value = "心跳名称")
    private String heartbeatText;

    /** 使用期限（何时到期） */
    @Excel(name = "使用期限", readConverterExp = "何=时到期")
    @ApiModelProperty(value = "使用期限")
    private Date allottedTime;

    /** 是否取消服务 */
    @Excel(name = "是否取消服务")
    @ApiModelProperty(value = "是否取消服务")
    private Integer isPause;

    /** 取消说明 */
    @Excel(name = "取消说明")
    @ApiModelProperty(value = "取消说明")
    private String pauseState;

    public DbEquipment(Long equipmentId, String heartbeatText, Date allottedTime, Integer isPause, String pauseState, String equipmentName, Integer isFault, Long equipmentTemplateId, Integer equipmentNo, String handlerText, Integer isOnline, String qrCodePic) {

        this.equipmentId = equipmentId;
        this.heartbeatText = heartbeatText;
        this.allottedTime = allottedTime;
        this.isPause = isPause;
        this.pauseState = pauseState;
        this.equipmentName = equipmentName;
        this.isFault = isFault;
        this.equipmentTemplateId = equipmentTemplateId;
        this.equipmentNo = equipmentNo;
        this.handlerText = handlerText;
        this.isOnline = isOnline;
        this.qrCodePic = qrCodePic;
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    /** 设备昵称自定义 */
    @Excel(name = "设备昵称自定义")
    @ApiModelProperty(value = "设备昵称自定义")
    private String equipmentName;

    /** 是否发生故障 */
    @Excel(name = "是否发生故障")
    @ApiModelProperty(value = "是否发生故障")
    private Integer isFault;

    /** 设备模板id */
    @Excel(name = "设备模板id")
    @ApiModelProperty(value = "设备模板id")
    private Long equipmentTemplateId;

    /** 设备号（两位） */
    @Excel(name = "设备号", readConverterExp = "两=位")
    @ApiModelProperty(value = "设备号")
    private Integer equipmentNo;

    /** 操作集（若用户没修改为模板默认） */
    @Excel(name = "操作集", readConverterExp = "若=用户没修改为模板默认")
    @ApiModelProperty(value = "操作集")
    private String handlerText;

    /** 是否在线（0在线，1不在线） */
    @Excel(name = "是否在线", readConverterExp = "0=在线，1不在线")
    @ApiModelProperty(value = "是否在线")
    private Integer isOnline;

    /** 二维码图片 */
    @Excel(name = "二维码图片")
    @ApiModelProperty(value = "二维码图片")
    private String qrCodePic;



    public void setEquipmentId(Long equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId()
    {
        return equipmentId;
    }
    public void setHeartbeatText(String heartbeatText)
    {
        this.heartbeatText = heartbeatText;
    }

    public String getHeartbeatText()
    {
        return heartbeatText;
    }
    public void setAllottedTime(Date allottedTime)
    {
        this.allottedTime = allottedTime;
    }

    public Date getAllottedTime()
    {
        return allottedTime;
    }
    public void setIsPause(Integer isPause)
    {
        this.isPause = isPause;
    }

    public Integer getIsPause()
    {
        return isPause;
    }
    public void setPauseState(String pauseState)
    {
        this.pauseState = pauseState;
    }

    public String getPauseState()
    {
        return pauseState;
    }
    public void setIsFault(Integer isFault)
    {
        this.isFault = isFault;
    }

    public Integer getIsFault()
    {
        return isFault;
    }
    public void setEquipmentTemplateId(Long equipmentTemplateId)
    {
        this.equipmentTemplateId = equipmentTemplateId;
    }

    public Long getEquipmentTemplateId()
    {
        return equipmentTemplateId;
    }
    public void setEquipmentNo(Integer equipmentNo)
    {

        this.equipmentNo = equipmentNo;
    }

    public Integer getEquipmentNo()
    {
        return equipmentNo;
    }
    public String getEquipmentNoString()
    {
        if (equipmentNo!=null){
            if (equipmentNo<10){
                return "0"+equipmentNo;
            }
            else {

                return equipmentNo+"";
            }
        }
        return equipmentNo+"";

    }
    public void setHandlerText(String handlerText)
    {
        this.handlerText = handlerText;
    }

    public String getHandlerText()
    {
        return handlerText;
    }
    public void setIsOnline(Integer isOnline)
    {
        this.isOnline = isOnline;
    }

    public Integer getIsOnline()
    {
        return isOnline;
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
                .append("equipmentId", getEquipmentId())
                .append("heartbeatText", getHeartbeatText())
                .append("allottedTime", getAllottedTime())
                .append("isPause", getIsPause())
                .append("pauseState", getPauseState())
                .append("isFault", getIsFault())
                .append("equipmentTemplateId", getEquipmentTemplateId())
                .append("equipmentNo", getEquipmentNo())
                .append("handlerText", getHandlerText())
                .append("isOnline", getIsOnline())
                .append("qrCodePic", getQrCodePic())
                .toString();
    }
}