package com.young.youngsecurity.common.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/19
 */
@Component
public class AuthCacheServiceImpl {

    public static final String AUTH_OUTER_KEY = "auth_outer_key";

    private static final String AUTH_INNER_KEY_TEMPLATE = "auth_inner_key_%d";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static String authInnerKey(Long id) {
        return String.format(AUTH_INNER_KEY_TEMPLATE, id);
    }

    public MyUserDetails getUserDetail(Long userId) {
        return (MyUserDetails) this.redisTemplate.opsForHash().get(AuthCacheServiceImpl.AUTH_OUTER_KEY, authInnerKey(userId));
    }

    /**
     * 查询用户详情、权限信息，并存入redis（可以用于后续用户详情、权限信息等的缓存，同时还能控制用户登录状态的缓存，方便实现单用户在线、token刷新等逻辑）
     *
     * @param userDetails
     */
    public void cacheUserDetail(MyUserDetails userDetails) {
        this.redisTemplate.opsForHash().put(AuthCacheServiceImpl.AUTH_OUTER_KEY, authInnerKey(userDetails.getId()), userDetails);
    }

    /**
     * 删除用户登录信息
     *
     * @param userId
     */
    public void removeUserDetail(Long userId) {
        this.redisTemplate.opsForHash().delete(AuthCacheServiceImpl.AUTH_OUTER_KEY, authInnerKey(userId));
    }

}
