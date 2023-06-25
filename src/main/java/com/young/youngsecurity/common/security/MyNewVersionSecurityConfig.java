package com.young.youngsecurity.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @description: 新版配置
 * 新版中：
 * 1.WebSecurityConfigurerAdapter类已过期，只需要再配置类上打注解@EnableWebSecurity。
 * 2.不需要通过方法重写的方式暴露AuthenticationManager对象，可以从AuthenticationConfiguration对象中拿，然后再暴露。
 * 3.SecurityFilterChain等配置，也不再通过重写父类方法实现，而是通过bean注入的方式实现的。
 * 4.PasswordEncoder、自定义UserDetailsService的实现类对象，也不需要重写方法显式的传递给AuthenticationManagerBuilder。
 * @author: Yang JianXiong
 * @since: 2023/6/18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyNewVersionSecurityConfig {

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private MyJwtAuthenticationFilter myJwtAuthenticationFilter;

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

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
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //1.因为时前后端分离项目，天生能防御csrf攻击（由于security的csrf防御机制是签发和校验csrf-token，如果不关闭，还需要考虑这个）
                .csrf().disable()
                //2.因为是前后端分离项目，不需要用session来管理securityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //3.对于登录接口，必须允许匿名访问
                .authorizeRequests().antMatchers("/authentication/login/**").anonymous()
                .and()
                //4.对于jwt刷新接口，必须允许匿名访问
                .authorizeRequests().antMatchers("/authentication/refreshJwt/**").anonymous()
                //5.除了上述放行的接口，其余所有接口都必须认证
                .anyRequest().authenticated()
                .and()
                //6.将自定义认证过滤器添加到security的认证过滤器链中
                .addFilterBefore(this.myJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //7.配置认证和授权相关的异常处理器
                .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint).accessDeniedHandler(this.accessDeniedHandler)
                .and()
                //8.允许跨域访问
                .cors()
                .and().build();
    }

}
