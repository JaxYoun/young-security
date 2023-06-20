package com.young.youngsecurity.common.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义权限检验器
 * @author: Yang JianXiong
 * @since: 2023/6/20
 */
@Component("myExpression")
public class MyExpressionRoot {

    public boolean hasPermission(String require) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserDetails.getAuthoritySet().contains(require);
    }

}
