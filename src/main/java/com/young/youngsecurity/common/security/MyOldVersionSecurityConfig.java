package com.young.youngsecurity.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description: 旧版配置
 * @author: Yang JianXiong
 * @since: 2023/6/18
 */
@Deprecated
//@Configuration
public class MyOldVersionSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 向IOC容器注入AuthenticationManager对象，
     * 达到全局暴露该对象的目的
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 因为时前后端分离项目，需要把csrf防御功能关闭
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 因为是前后端分离项目，不需要用session来管理securityContext
                .and()
                .authorizeRequests().antMatchers("/authentication/login/**").anonymous() // 对于登录接口，允许匿名访问
                .antMatchers("").hasRole("")  //集中配置式权限控制（小项目可以用，还是更推荐注解式）
                .anyRequest().authenticated();  //除了上述放行的接口，其余所有接口都必须认证
    }
}
