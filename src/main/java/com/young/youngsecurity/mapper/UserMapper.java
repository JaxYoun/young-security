package com.young.youngsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.young.youngsecurity.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
