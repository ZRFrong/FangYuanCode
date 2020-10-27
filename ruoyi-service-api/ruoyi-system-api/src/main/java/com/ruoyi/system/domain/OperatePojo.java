package com.ruoyi.system.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/*
 *设备具体操作项类
 * */
@Data
@ApiModel
public class OperatePojo {
    public OperatePojo(String checkCode, String checkName, List<OperateSp> spList) {
        this.checkCode = checkCode;
        this.checkName = checkName;
        this.spList = spList;
    }

    public OperatePojo() {
    }

    @ApiModelProperty(value = "分类标识（卷帘：1；通风：2；浇水：3；补光：4；）")
    private String checkCode;


    @ApiModelProperty(value = "分类名称（用户自定义）")
    private  String checkName;


    @ApiModelProperty(value = "具体按钮的操作项目")
    private List<OperateSp> spList;



    @Data
    @ApiModel
    public static class OperateSp {
        public OperateSp(String handleName, String handleCode) {
            this.handleName = handleName;
            this.handleCode = handleCode;
        }

        public OperateSp() {
        }

        @ApiModelProperty(value = "操作名称;{固定为开始(start)，开始暂停（start_stop），结束（down），结束暂停（down_stop）}")
        private String handleName;


        @ApiModelProperty(value = "操作代码")
        private  String handleCode;



    }


}
