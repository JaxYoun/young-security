package com.young.youngsecurity.controller;

import com.young.youngsecurity.common.meta.Result;
import com.young.youngsecurity.service.AuthenticationServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

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
    public Result<Map<String, String>> login(@PathVariable String username, @PathVariable String password) {
        return Result.ok(this.authenticationService.login(username, password));
    }

    @GetMapping("/refreshJwt/{additionJwt}")
    public Result<Map<String, String>> refreshJwt(@PathVariable String additionJwt) {
        Map<String, String> body = this.authenticationService.refreshJwt(additionJwt);
        if (Objects.nonNull(body)) {
            return Result.ok(body);
        } else {
            return Result.ko(402, "Token invalid, login required!");
        }
    }

    @GetMapping("/logout")
    public Result<Boolean> logout() {
        return Result.ok(this.authenticationService.logout());
    }

}
