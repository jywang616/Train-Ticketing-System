package com.jyw.ticketsystem.batch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("business")
public interface BusinessFeign {
    @GetMapping("/hello")
    String hello();
}
