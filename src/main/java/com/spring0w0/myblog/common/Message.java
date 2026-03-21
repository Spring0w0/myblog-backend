package com.spring0w0.myblog.common;

import com.spring0w0.myblog.constants.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Spring0w0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message<T> {
    private Integer code;
    private String msg;
    private T data;

    // --- 成功响应的静态快捷方法 ---

    public static <T> Message<T> success(T data) {
        return Message.<T>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> Message<T> success(T data, String msg) {
        return Message.<T>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(msg)
                .data(data)
                .build();
    }

    // --- 失败响应的静态快捷方法 ---

    public static <T> Message<T> error(String msg) {
        return Message.<T>builder()
                .code(ResponseCode.ERROR.getCode())
                .msg(msg)
                .build(); // data 默认为 null
    }

    public static <T> Message<T> error(ResponseCode code, String msg) {
        return Message.<T>builder()
                .code(code.getCode())
                .msg(msg)
                .build();
    }

    public static <T> Message<T> error(Integer code, String msg) {
        return Message.<T>builder()
                .code(code)
                .msg(msg)
                .build();
    }
}
