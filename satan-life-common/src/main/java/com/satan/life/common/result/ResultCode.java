package com.satan.life.common.result;

import lombok.Getter;

/**
 * 响应状态码枚举类
 */
@Getter
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    ERROR(500, "操作失败"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 请求参数错误
     */
    PARAM_ERROR(400, "请求参数错误"),

    /**
     * 业务异常
     */
    BUSINESS_ERROR(501, "业务异常"),

    /**
     * 系统繁忙
     */
    SYSTEM_BUSY(503, "系统繁忙，请稍后再试"),

    /**
     * 数据库异常
     */
    DATABASE_ERROR(502, "数据库异常");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}