package com.rg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2024/7/29 14:11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
            SpringApplication.run(ApiGatewayApplication.class, args);
    }
}