package com.spring0w0.myblog.common.exception;

import com.spring0w0.myblog.constants.ResponseCode;
import lombok.Getter;

/**
 * 认证异常（用于 JWT 验证失败场景）
 * @author Spring0w0
 */
@Getter
public class UnauthorizedException extends RuntimeException {

    private final Integer code;

    public UnauthorizedException(String message) {
        super(message);
        this.code = ResponseCode.UNAUTHORIZED.getCode();
    }
}