package com.ruoyi.common.exception;

import com.ruoyi.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * @author zmr
 * @author lucas
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public R handleException(HttpRequestMethodNotSupportedException e)
    {
        logger.error(e.getMessage(), e);
        return R.error(HttpStatus.METHOD_NOT_ALLOWED.value(),"不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler({NullPointerException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public R nullException(NullPointerException e)
    {
        logger.error(e.getMessage(), e);
        return R.error(ExceptionEnum.BODY_NOT_MATCH.getResultCode(),ExceptionEnum.BODY_NOT_MATCH.getResultMsg());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R notFount(RuntimeException e)
    {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
        {
            throw e;
        }
        logger.error("运行时异常:", e);
        return R.error(ExceptionEnum.INTERNAL_SERVER_ERROR.getResultCode(),ExceptionEnum.INTERNAL_SERVER_ERROR.getResultMsg());
    }

    /**
     * 拦截所有异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Throwable throwable) {
        logger.error("系统异常！", throwable);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value() , "系统异常！");
    }
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RuoyiException.class)
    public R handleWindException(RuoyiException e)
    {
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理业务自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e)
    {
        return R.error(e.getMessage());
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e)
    {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) throws Exception
    {
        logger.error(e.getMessage(), e);
        return R.error("服务器错误，请联系管理员");
    }
/*
* 业务异常
* */


    /**
     * 捕获并处理未授权异常
     *
     * @param e 授权异常
     * @return 统一封装的结果类, 含有代码code和提示信息msg
     */
    @ExceptionHandler(UnauthorizedException.class)
    public R handle401(UnauthorizedException e)
    {
        return R.error(401, e.getMessage());
    }

    // 验证码错误
    @ExceptionHandler(ValidateCodeException.class)
    public R handleCaptcha(ValidateCodeException e)
    {
        return R.error(e.getMessage());
    }
}