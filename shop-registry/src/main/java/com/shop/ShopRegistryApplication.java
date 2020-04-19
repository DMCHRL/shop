package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 已移除，改用nacos
 */
@SpringBootApplication
//@EnableEurekaServer
public class ShopRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopRegistryApplication.class,args);
    }
}
