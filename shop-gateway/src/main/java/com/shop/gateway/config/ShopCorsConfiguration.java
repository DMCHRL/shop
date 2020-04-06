package com.shop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ShopCorsConfiguration {

    @Bean
    public CorsFilter corsFilter(){
        //初始化cors对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); //任何域名
        //corsConfiguration.setAllowCredentials(true); //允许携带cookie
        corsConfiguration.addAllowedMethod("*"); //允许所有请求方法
        corsConfiguration.addAllowedHeader("*"); //允许携带任何头信息

        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsFilter(corsConfigurationSource);
    }
}
