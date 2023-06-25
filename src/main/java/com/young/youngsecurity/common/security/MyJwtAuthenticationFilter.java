package com.young.youngsecurity.common.security;

import com.young.youngsecurity.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/19
 */
@Slf4j
@Component
public class MyJwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private AuthCacheServiceImpl authCacheService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        //1.从request获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            logger.error("The token is null.");
            //假如未传token，仍然需要走完过滤器链，让后续过滤器做出完整的反应
            filterChain.doFilter(request, response);
            //走过完整的过滤器链后，直接返回，因为无token，所以不需要后续步骤
            return;
        }

        //2.解析token
        Claims claims = JwtUtil.getClaims(token);
        if (Objects.isNull(claims)) {
            logger.error("The claims is null.");
            throw new InternalAuthenticationServiceException("Authenticate failed!");
        }
        Long userId = claims.get(AuthCacheServiceImpl.SUBJECT_KEY, Long.class);
        if (Objects.isNull(userId)) {
            logger.error("The userId is null.");
            throw new InternalAuthenticationServiceException("Authenticate failed!");
        }

        //3.从redis获取用户详情
        MyUserDetails myUserDetails = this.authCacheService.getUserDetail(userId);
        if (Objects.isNull(myUserDetails)) {
            logger.error("The myUserDetails is null.");
            throw new InternalAuthenticationServiceException("Authenticate failed!");
        }

        //4.将用户详情存入securityContext
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities()));

        //5.放行
        filterChain.doFilter(request, response);
    }

}
