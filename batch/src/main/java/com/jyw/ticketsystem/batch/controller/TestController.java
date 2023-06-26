package com.jyw.ticketsystem.batch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "Batch模块也不要error！";
    }

}
