package com.jyw.ticketsystem.member.controller;

import com.jyw.ticketsystem.common.context.LoginMemberContext;
import com.jyw.ticketsystem.common.resp.CommonResp;
import com.jyw.ticketsystem.member.req.PassengerQueryReq;
import com.jyw.ticketsystem.member.req.PassengerSaveReq;
import com.jyw.ticketsystem.member.resp.PassengerQueryResp;
import com.jyw.ticketsystem.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Resource
    private PassengerService passengerService;
    
    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req){
        passengerService.save(req);
        return new CommonResp<>();
    }
    @GetMapping ("/query-list")
    public CommonResp<List<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req){
        req.setMemberId(LoginMemberContext.getId());
        List<PassengerQueryResp> list=passengerService.queryList(req);
        return new CommonResp<>(list);
    }
}
