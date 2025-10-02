package com.satan.life.asset.exception;

import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理请求参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleValidationException(MethodArgumentNotValidException e) {
        log.error("参数校验异常: {}", e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return R.error(ResultCode.PARAM_ERROR.getCode(), "参数校验失败").put("errors", errors);
    }

    /**
     * 处理缺失请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<?> handleMissingParamsException(MissingServletRequestParameterException e) {
        log.error("缺失请求参数: {}", e.getMessage(), e);
        String message = String.format("缺少必需的参数: %s", e.getParameterName());
        return R.error(ResultCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法不支持: {}", e.getMessage(), e);
        String message = String.format("不支持 %s 请求方法", e.getMethod());
        return R.error(HttpStatus.METHOD_NOT_ALLOWED.value(), message);
    }

    /**
     * 处理404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<?> handleNotFoundException(NoHandlerFoundException e) {
        log.error("请求路径不存在: {}", e.getMessage(), e);
        return R.error(HttpStatus.NOT_FOUND.value(), "请求路径不存在");
    }

    /**
     * 处理其他所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return R.error(ResultCode.ERROR.getCode(), "系统内部错误，请稍后重试");
    }
}