package com.rg.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2024/7/25 17:16
 */
@SpringBootApplication
@MapperScan("com.rg.staservice.mapper")
@ComponentScan("com.rg")
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
