package com.satan.life.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应结果类
 */
@Data
@NoArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 其他数据
     */
    private Map<String, Object> otherData = new HashMap<>();

    /**
     * 构造器
     */
    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.otherData = new HashMap<>();
    }

    /**
     * 全参构造器
     */
    public R(Integer code, String message, T data, Map<String, Object> otherData) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.otherData = otherData;
    }

    /**
     * 返回成功结果
     */
    public static <T> R<T> success(T data) {
        return new R<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 返回成功结果
     */
    public static <T> R<T> success(String message, T data) {
        return new R<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 返回成功结果
     */
    public static <T> R<T> success() {
        return new R<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 返回失败结果
     */
    public static <T> R<T> error(Integer code, String message) {
        return new R<T>(code, message, null);
    }

    /**
     * 返回失败结果
     */
    public static <T> R<T> error(ResultCode resultCode) {
        return new R<T>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    /**
     * 添加其他数据
     */
    public R<T> put(String key, Object value) {
        this.otherData.put(key, value);
        return this;
    }
}