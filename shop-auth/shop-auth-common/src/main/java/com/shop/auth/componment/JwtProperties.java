package com.shop.auth.componment;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Jwt的配置属性
 *
 * @author Peng
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * JWT存储的请求头
     */
    private String tokenHeader = "Authorization";
    /**
     * JWT加解密使用的密钥
     */
    private String secret = "Shop";
    /**
     * JWT的超期限时间(60*60*24) 31536000
     */
    private Integer expiration = 43200;
    /**
     * JWT负载中拿到开头
     */
    private String tokenStartHead = "Shop-";

    private String cookieName = "SHOP_TOKEN";
}


