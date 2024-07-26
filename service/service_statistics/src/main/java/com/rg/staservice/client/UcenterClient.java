package com.rg.staservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2024/7/25 18:41
 */
@FeignClient("service-ucenter")
@Component
public interface UcenterClient {
    @GetMapping("/ucenterservice/member/registerCount/{day}")
    Integer registerCount(@PathVariable("day") String day);
}
