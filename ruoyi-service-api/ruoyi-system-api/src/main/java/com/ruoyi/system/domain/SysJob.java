package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 定时任务调度对象 sys_job
 * 
 * @author zheng
 * @date 2020-09-22
 */
@ApiModel
public class SysJob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long jobId;

    /** 任务名称 */
    private String jobName;

    /** 任务组名 */
    private String jobGroup;

    /** 任务方法 */
    @Excel(name = "任务方法")
    @ApiModelProperty(value = "任务方法")
    private String methodName;

    /** 方法参数 */
    @Excel(name = "方法参数")
    @ApiModelProperty(value = "方法参数")
    private String methodParams;

    /** cron执行表达式 */
    @Excel(name = "cron执行表达式")
    @ApiModelProperty(value = "cron执行表达式")
    private String cronExpression;

    /** 计划执行错误策略（1立即执行 2执行一次 3放弃执行） */
    @Excel(name = "计划执行错误策略", readConverterExp = "1=立即执行,2=执行一次,3=放弃执行")
    @ApiModelProperty(value = "计划执行错误策略")
    private String misfirePolicy;

    /** 是否并发执行（0允许 1禁止） */
    @Excel(name = "是否并发执行", readConverterExp = "0=允许,1=禁止")
    @ApiModelProperty(value = "是否并发执行")
    private String concurrent;

    /** 状态（0正常 1暂停） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=暂停")
    @ApiModelProperty(value = "状态")
    private String status;



    public void setJobId(Long jobId) 
    {
        this.jobId = jobId;
    }

    public Long getJobId() 
    {
        return jobId;
    }
    public void setJobName(String jobName) 
    {
        this.jobName = jobName;
    }

    public String getJobName() 
    {
        return jobName;
    }
    public void setJobGroup(String jobGroup) 
    {
        this.jobGroup = jobGroup;
    }

    public String getJobGroup() 
    {
        return jobGroup;
    }
    public void setMethodName(String methodName) 
    {
        this.methodName = methodName;
    }

    public String getMethodName() 
    {
        return methodName;
    }
    public void setMethodParams(String methodParams) 
    {
        this.methodParams = methodParams;
    }

    public String getMethodParams() 
    {
        return methodParams;
    }
    public void setCronExpression(String cronExpression) 
    {
        this.cronExpression = cronExpression;
    }

    public String getCronExpression() 
    {
        return cronExpression;
    }
    public void setMisfirePolicy(String misfirePolicy) 
    {
        this.misfirePolicy = misfirePolicy;
    }

    public String getMisfirePolicy() 
    {
        return misfirePolicy;
    }
    public void setConcurrent(String concurrent) 
    {
        this.concurrent = concurrent;
    }

    public String getConcurrent() 
    {
        return concurrent;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("jobId", getJobId())
            .append("jobName", getJobName())
            .append("jobGroup", getJobGroup())
            .append("methodName", getMethodName())
            .append("methodParams", getMethodParams())
            .append("cronExpression", getCronExpression())
            .append("misfirePolicy", getMisfirePolicy())
            .append("concurrent", getConcurrent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
