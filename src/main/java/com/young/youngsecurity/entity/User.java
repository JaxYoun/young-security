package com.young.youngsecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_user")
public class User extends Model<User> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @JsonIgnore
    private String password;

    private Integer enabled;

}
