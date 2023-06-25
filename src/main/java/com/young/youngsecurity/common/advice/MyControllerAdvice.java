package com.young.youngsecurity.common.advice;

import com.young.youngsecurity.common.exception.MyException;
import com.young.youngsecurity.common.meta.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/25
 */
@ControllerAdvice
public class MyControllerAdvice {

    @ResponseBody // 不可或缺
    @ResponseStatus(HttpStatus.OK) //自定义浏览器返回状态码
    @ExceptionHandler(value = MyException.class)
    public Result<Void> handleMyException(MyException exception) {
        return Result.ko(exception.getCode(), exception.getMessage());
    }

}
