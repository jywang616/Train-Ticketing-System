package com.jyw.ticketsystem.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TsetController {
    @GetMapping("/hello")
    public String hello(){
        return "球球不要error！";
    }

}
