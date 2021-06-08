package com.ruoyi.system.domain.tcp;

import com.ruoyi.system.domain.DbOperationVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: modbus协议处理后的指令信息实体类
 * @Author zheng
 * @Date 2021/5/20 17:34
 * @Version 1.0
 */
@Data
@ApiModel
@Accessors(chain = true)
public class DbOperationByteOrderVo extends DbOperationVo {


    private byte[] byteOrder;
}
