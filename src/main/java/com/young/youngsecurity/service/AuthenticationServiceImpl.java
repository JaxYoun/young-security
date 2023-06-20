package com.young.youngsecurity.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.young.youngsecurity.common.security.AuthCacheServiceImpl;
import com.young.youngsecurity.common.security.MyUserDetails;
import com.young.youngsecurity.common.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Optional;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/19
 */
@Service
public class AuthenticationServiceImpl {

    @Resource
    private AuthCacheServiceImpl authCacheService;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = this.authenticationManager.authenticate(authentication);
        return Optional.ofNullable(authenticate)
                .map(it -> {
                    //1.查询用户详情、权限信息，并存入redis（可以用于后续用户详情、权限信息等的缓存，同时还能控制用户登录状态的缓存，方便实现单用户在线、token刷新等逻辑）
                    MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
                    //2.设置权限
                    myUserDetails.setAuthoritySet(Sets.newHashSet("sayHello"));
                    this.authCacheService.cacheUserDetail(myUserDetails);
                    //3.根据用户信息生成jwt
                    return this.generateToken(it);
                }).orElseThrow(() -> new InternalAuthenticationServiceException("Authenticate failed!"));
    }

    /**
     * 根据用户信息生成jwt
     *
     * @param authenticate
     * @return
     */
    private String generateToken(Authentication authenticate) {
        MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put(AuthCacheServiceImpl.SUBJECT_KEY, myUserDetails.getId());
        return JwtUtil.generateJwt(map);
    }

    /**
     * 登出
     *
     * @return
     */
    public Boolean logout() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.authCacheService.removeUserDetail(myUserDetails.getId());
        return Boolean.TRUE;
    }
}
