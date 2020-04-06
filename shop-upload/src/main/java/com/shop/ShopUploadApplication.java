package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShopUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopUploadApplication.class,args);
    }
}
