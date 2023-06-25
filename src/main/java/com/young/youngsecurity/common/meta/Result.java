package com.young.youngsecurity.common.meta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> Result<T> ok(T data) {
        return new Result<T>().setCode(200).setMessage("ok").setData(data);
    }

    public static <T> Result<T> ko(int code, String message) {
        return new Result<T>().setCode(code).setMessage(message);
    }

}
