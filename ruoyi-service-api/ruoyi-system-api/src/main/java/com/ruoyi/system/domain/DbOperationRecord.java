package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 用户操作记录对象 db_operation_record
 * 
 * @author zheng
 * @date 2020-10-16
 */
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DbOperationRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long dbUserId;

    /** 是否完成 */
    @Excel(name = "是否完成")
    @ApiModelProperty(value = "是否完成")
    private Integer isComplete;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "操作时间")
    private Date operationTime;

    /** 操作项 */
    @Excel(name = "操作项")
    @ApiModelProperty(value = "操作项")
    private String operationText;

    /** 操作对象 */
    @Excel(name = "操作对象")
    @ApiModelProperty(value = "操作对象")
    private String operationObject;

    /** 操作对象id集合 */
    @Excel(name = "操作对象id集合")
    @ApiModelProperty(value = "操作对象id集合")
    private String operationObjectId;

    /** 操作对象id属性（地块，地区，设备） */
    @Excel(name = "操作对象id属性", readConverterExp = "地=块，地区，设备")
    @ApiModelProperty(value = "操作对象id属性")
    private Integer operationObjectType;

    /** 操作来源（小程序，app，网页端） */
    @Excel(name = "操作来源", readConverterExp = "小=程序，app，网页端")
    @ApiModelProperty(value = "操作来源")
    private Integer operationSource;

    /** 大棚id */
    @Excel(name = "大棚id")
    @ApiModelProperty(value = "大棚id")
    private Long landId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dbUserId", getDbUserId())
            .append("isComplete", getIsComplete())
            .append("operationTime", getOperationTime())
            .append("operationText", getOperationText())
            .append("operationObject", getOperationObject())
            .append("operationObjectId", getOperationObjectId())
            .append("operationObjectType", getOperationObjectType())
            .append("operationSource", getOperationSource())
            .toString();
    }
}
