package com.young.youngsecurity.controller;

import com.young.youngsecurity.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/19
 */
@SpringBootTest
class UserControllerTest {

    @Resource
    private UserController userController;

    @Test
    public void getByName() {
        ResponseEntity<User> user = this.userController.getByName("admin");
        System.err.println(user.getBody());
    }

    /**
     * 添加用户
     */
    @Test
    public void addUser() {
        this.userController.add("admin", "111111");
    }

}