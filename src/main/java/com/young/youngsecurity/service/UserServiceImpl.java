package com.young.youngsecurity.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.young.youngsecurity.entity.User;
import com.young.youngsecurity.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> {

    public User getUserByName(String name) {
        return this.baseMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getName, name).last(" LIMIT 1"));
    }

}
