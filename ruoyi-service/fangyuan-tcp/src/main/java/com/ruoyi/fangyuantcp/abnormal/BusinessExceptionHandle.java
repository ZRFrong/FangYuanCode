package com.ruoyi.fangyuantcp.abnormal;

import com.ruoyi.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandle {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 掉线异常处理
     */
    @ExceptionHandler({DropsExceptions.class})
    public R dropsExceptions(DropsExceptions e)
    {
        logger.error(e.getMessage(), e);
        return R.error(e.getMessage()+"设备掉线异常");
    }

    /**
     * 设备故障处理
     */
    @ExceptionHandler({FaultExceptions.class})
    public R faultExceptions(FaultExceptions e)
    {
        logger.error(e.getMessage(), e);
        return R.error(e.getMessage()+"设备故障异常");
    }

    /**
     * 设备操作处理
     */
    @ExceptionHandler({OperationExceptions.class})
    public R operationExceptions(OperationExceptions e)
    {
        logger.error(e.getMessage(), e);
        return R.error(e.getMessage()+"设备操作异常");
    }






}
