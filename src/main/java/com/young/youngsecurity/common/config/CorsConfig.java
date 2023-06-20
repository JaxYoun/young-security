package com.young.youngsecurity.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: SpringBoot的跨域配置
 * @author: Yang JianXiong
 * @since: 2023/6/20
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //1.设置允许跨域的路径
                .allowedOriginPatterns("*") //2.设置允许跨域请求的域名
                .allowCredentials(true) //3.是否允许kookie
                .allowedMethods("GET", "POST", "DELETE", "PUT") //4.设置允许的请求方式
                .allowedHeaders("*") // 5.设置允许的header属性
                .maxAge(3600); // 6.设置跨域允许时间
    }
}
