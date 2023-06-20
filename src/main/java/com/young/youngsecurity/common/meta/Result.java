package com.young.youngsecurity.common.meta;

import lombok.AllArgsConstructor;
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

}
