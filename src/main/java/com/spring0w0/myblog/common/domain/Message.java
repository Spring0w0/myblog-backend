package com.spring0w0.myblog.common.domain;

import cn.hutool.core.collection.CollUtil;
import com.spring0w0.myblog.constants.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;

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

    // 如果 data 为 null，则默认为空集合
    @Builder.Default
    private T data = (T) new HashMap<String, Object>();

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

    public static <T> Message<T> success(String msg) {
        return Message.<T>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(msg)
                .build();
    }


    // --- 失败响应的静态快捷方法 ---

    public static <T> Message<T> error(String msg) {
        return Message.<T>builder()
                .code(ResponseCode.ERROR.getCode())
                .msg(msg)
                .build();
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
