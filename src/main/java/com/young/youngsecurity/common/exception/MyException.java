package com.young.youngsecurity.common.exception;

import lombok.Data;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/25
 */
@Data
public class MyException extends RuntimeException {

    private int code;

    private String message;

    public MyException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
