package com.young.youngsecurity.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.young.youngsecurity.common.meta.Result;
import com.young.youngsecurity.common.util.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 缺乏授权-处理器
 * @author: Yang JianXiong
 * @since: 2023/6/20
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result<Void> result = new Result<Void>().setCode(HttpStatus.FORBIDDEN.value()).setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
        WebUtil.renderString(response, this.objectMapper.writeValueAsString(result));
    }
}
