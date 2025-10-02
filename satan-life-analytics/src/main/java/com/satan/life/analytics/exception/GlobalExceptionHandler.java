package com.satan.life.analytics.exception;

import com.satan.life.analytics.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e, HttpServletRequest request) {
        logger.error("系统异常: {}", request.getRequestURI(), e);
        ApiResponse<Object> response = ApiResponse.fail(500, "服务器内部错误，请稍后重试");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e) {
        logger.warn("业务异常: {}", e.getMessage());
        ApiResponse<Object> response = ApiResponse.fail(e.getCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("参数校验异常: {}", e.getMessage());
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.fail(400, message);
    }
    
    /**
     * 处理日期时间解析异常
     */
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleDateTimeParseException(DateTimeParseException e) {
        logger.warn("日期时间解析异常: {}", e.getMessage());
        return ApiResponse.fail(400, "日期时间格式错误，请使用yyyy-MM-dd格式");
    }
    
    /**
     * 处理数据库异常
     */
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> handleSQLException(SQLException e, HttpServletRequest request) {
        logger.error("数据库异常: {}", request.getRequestURI(), e);
        ApiResponse<Object> response = ApiResponse.fail(500, "数据库操作异常，请稍后重试");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        logger.error("空指针异常: {}", request.getRequestURI(), e);
        ApiResponse<Object> response = ApiResponse.fail(500, "服务器内部错误，请稍后重试");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}