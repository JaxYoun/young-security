package com.young.youngsecurity.controller;

import com.young.youngsecurity.service.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/authentication")
public class AuthenticationController {

    @Resource
    private AuthenticationServiceImpl authenticationService;

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<String> login(@PathVariable String username, @PathVariable String password) {
        return ResponseEntity.ok(this.authenticationService.login(username, password));
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout() {
        return ResponseEntity.ok(this.authenticationService.logout());
    }

}
