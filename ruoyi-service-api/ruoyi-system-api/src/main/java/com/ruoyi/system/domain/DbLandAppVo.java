package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description: 新版app土地列表实体类
 * @Author zheng
 * @Date 2021/4/9 10:58
 * @Version 1.0
 */
@Data
@ApiModel
public class DbLandAppVo {


    @ApiModelProperty("温度，湿度，空气，二氧化碳")
    private DbTcpType dbTcpType;
    @ApiModelProperty("功能集")
    private List<Function> functions;


    @Data
    public static class Function {
        @ApiModelProperty("设备名称")
        private String equipmentName;

        @ApiModelProperty("操作集")
        private List<OperatePojo> operatePojos;
    }

}
