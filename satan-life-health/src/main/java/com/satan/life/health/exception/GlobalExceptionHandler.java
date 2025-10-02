package com.satan.life.health.exception;

import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleBusinessException(BusinessException e) {
        logger.error("业务异常: {}", e.getMessage(), e);
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理请求参数错误 - 表单验证
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleBindException(BindException e) {
        logger.error("请求参数错误: {}", e.getMessage(), e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "请求参数错误";
        return R.error(ResultCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 处理请求参数错误 - @RequestBody参数验证
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("请求参数错误: {}", e.getMessage(), e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "请求参数错误";
        return R.error(ResultCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 处理请求参数缺失
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("请求参数缺失: {}", e.getMessage(), e);
        return R.error(ResultCode.PARAM_ERROR.getCode(), "请求参数缺失: " + e.getParameterName());
    }

    /**
     * 处理请求参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error("请求参数类型不匹配: {}", e.getMessage(), e);
        return R.error(ResultCode.PARAM_ERROR.getCode(), "参数类型不匹配: " + e.getName());
    }

    /**
     * 处理请求体解析错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("请求体解析错误: {}", e.getMessage(), e);
        return R.error(ResultCode.PARAM_ERROR.getCode(), "请求体格式错误");
    }

    /**
     * 处理请求方法不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("请求方法不支持: {}", e.getMessage(), e);
        return R.error(HttpStatus.METHOD_NOT_ALLOWED.value(), "请求方法不支持");
    }

    /**
     * 处理资源不存在
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        logger.error("资源不存在: {}", e.getMessage(), e);
        return R.error(ResultCode.NOT_FOUND.getCode(), "请求的资源不存在");
    }

    /**
     * 处理数据库异常
     */
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleSQLException(SQLException e) {
        logger.error("数据库异常: {}", e.getMessage(), e);
        return R.error(ResultCode.DATABASE_ERROR.getCode(), "数据库操作异常");
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleNullPointerException(NullPointerException e) {
        logger.error("空指针异常: {}", e.getMessage(), e);
        return R.error(ResultCode.ERROR.getCode(), "系统内部错误");
    }

    /**
     * 处理所有其他异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleException(Exception e, HttpServletRequest request) {
        logger.error("系统异常: {}, 请求路径: {}", e.getMessage(), request.getRequestURI(), e);
        return R.error(ResultCode.ERROR.getCode(), "系统内部错误");
    }
}