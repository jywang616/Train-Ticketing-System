package com.jyw.ticketsystem.member.controller;

import com.jyw.ticketsystem.member.req.MemberRegisterReq;
import com.jyw.ticketsystem.member.service.MemberService;
import com.jyw.ticketsystem.common.resp.CommonResp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;
    @GetMapping("/count")
    public CommonResp<Integer> count(){
        int count= memberService.count();
        CommonResp<Integer> commonResp = new CommonResp<>();
        commonResp.setContent(count);
        return commonResp;
    }
    @PostMapping("/register")
    public CommonResp<Long> register(MemberRegisterReq req){
        long register= memberService.register(req);
        /*CommonResp<Long> commonResp = new CommonResp<>();
        commonResp.setContent(register);
        return commonResp;*/
        return new CommonResp<>(register);
    }

}
