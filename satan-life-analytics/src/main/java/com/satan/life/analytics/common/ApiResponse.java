package com.satan.life.analytics.common;

import java.io.Serializable;

/**
 * API统一响应封装类
 */
public class ApiResponse<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 响应状态码
    private int code;
    
    // 响应消息
    private String message;
    
    // 响应数据
    private T data;
    
    // 构造方法私有化
    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    // 成功响应
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }
    
    // 成功响应（无数据）
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(200, "success", null);
    }
    
    // 失败响应
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
    
    // 失败响应（默认状态码）
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(500, message, null);
    }
    
    // 获取状态码
    public int getCode() {
        return code;
    }
    
    // 设置状态码
    public void setCode(int code) {
        this.code = code;
    }
    
    // 获取消息
    public String getMessage() {
        return message;
    }
    
    // 设置消息
    public void setMessage(String message) {
        this.message = message;
    }
    
    // 获取数据
    public T getData() {
        return data;
    }
    
    // 设置数据
    public void setData(T data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}