package com.spring0w0.myblog.constants;

import lombok.Getter;

/**
 * 响应状态码枚举
 * @author Spring0w0
 */
@Getter
public enum ResponseCode {
    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
