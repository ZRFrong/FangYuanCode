package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备模板对象 db_equipment_temp
 *
 * @author zheng
 * @date 2020-09-25
 */
@ApiModel
public class DbEquipmentTemp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备模板表 */
    private Long equipmentTemId;

    /** 模板名称 */
    @Excel(name = "模板名称")
    @ApiModelProperty(value = "模板名称")
    private String tempName;

    /** 操作集（默认） */
    @Excel(name = "操作集", readConverterExp = "默=认")
    @ApiModelProperty(value = "操作集")
    private String handleText;

    /** plc版本 */
    @Excel(name = "plc版本")
    @ApiModelProperty(value = "plc版本")
    private String plcVersion;

    /** 操作项id */
    @Excel(name = "操作项id")
    @ApiModelProperty(value = "操作项id")
    private String tempPaper;

    /** 描述 */
    @Excel(name = "描述")
    @ApiModelProperty(value = "描述")
    private String describeText;

    public void setEquipmentTemId(Long equipmentTemId)
    {
        this.equipmentTemId = equipmentTemId;
    }

    public Long getEquipmentTemId()
    {
        return equipmentTemId;
    }
    public void setTempName(String tempName)
    {
        this.tempName = tempName;
    }

    public String getTempName()
    {
        return tempName;
    }
    public void setHandleText(String handleText)
    {
        this.handleText = handleText;
    }

    public String getHandleText()
    {
        return handleText;
    }
    public void setPlcVersion(String plcVersion)
    {
        this.plcVersion = plcVersion;
    }

    public String getPlcVersion()
    {
        return plcVersion;
    }
    public void setTempPaper(String tempPaper)
    {
        this.tempPaper = tempPaper;
    }

    public String getTempPaper()
    {
        return tempPaper;
    }
    public void setDescribeText(String describeText)
    {
        this.describeText = describeText;
    }

    public String getDescribeText()
    {
        return describeText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("equipmentTemId", getEquipmentTemId())
                .append("tempName", getTempName())
                .append("createTime", getCreateTime())
                .append("handleText", getHandleText())
                .append("createBy", getCreateBy())
                .append("plcVersion", getPlcVersion())
                .append("tempPaper", getTempPaper())
                .append("describeText", getDescribeText())
                .toString();
    }
}