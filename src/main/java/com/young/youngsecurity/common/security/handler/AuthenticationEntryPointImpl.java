package com.young.youngsecurity.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.young.youngsecurity.common.meta.Result;
import com.young.youngsecurity.common.util.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 缺乏认证-处理器
 * @author: Yang JianXiong
 * @since: 2023/6/20
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result<Void> result = new Result<Void>().setCode(HttpStatus.UNAUTHORIZED.value()).setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        WebUtil.renderString(response, this.objectMapper.writeValueAsString(result));
    }

}
