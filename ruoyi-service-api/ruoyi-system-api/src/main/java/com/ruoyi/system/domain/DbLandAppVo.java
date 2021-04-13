package com.ruoyi.system.domain;

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
public class DbLandAppVo {


    private DbTcpType dbTcpType;

    private List<Function> functions;


    @Data
    public static class Function {

        private String equipmentName;

        private List<OperatePojo> operatePojos;
    }

}
