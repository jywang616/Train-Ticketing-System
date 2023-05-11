package com.jyw.ticketsystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TsetController {
    @GetMapping("/hello")
    public String hello(){
        return "傻逼idea222";
    }

}
