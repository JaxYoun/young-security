package com.young.youngsecurity.controller;

import com.young.youngsecurity.entity.User;
import com.young.youngsecurity.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('sayHello')")
    // @PreAuthorize("@myExpression.hasPermission('sayHello')")  //自定义表达式处理器
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("hi: " + authentication.getName());
    }

    @GetMapping("/getByName/{username}")
    public ResponseEntity<User> getByName(@PathVariable String username) {
        return ResponseEntity.ok(this.userService.getUserByName(username));
    }

    @GetMapping("/add/{username}/{password}")
    public ResponseEntity<Boolean> add(@PathVariable String username, @PathVariable String password) {
        User user = new User().setName(username).setPassword(this.passwordEncoder.encode(password)).setEnabled(1);
        return ResponseEntity.ok(this.userService.save(user));
    }

}
