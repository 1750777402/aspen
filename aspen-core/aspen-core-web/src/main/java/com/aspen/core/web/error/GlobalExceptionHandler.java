package com.aspen.core.web.error;

import com.aspen.core.foundation.exception.BizException;
import com.aspen.core.foundation.utils.AspenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AspenResponse error(Exception e) {
        // 通用异常结果
        log.error("", e);
        return AspenResponse.error();
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(BizException.class )
    @ResponseBody
    public AspenResponse bizError(BizException e) {
        // 通用异常结果
        return AspenResponse.error(e.getMessage());
    }

}