package com.jyw.ticketsystem.member.controller;

import com.jyw.ticketsystem.member.req.PassengerSaveReq;
import com.jyw.ticketsystem.member.service.PassengerService;
import com.jyw.ticketsystem.common.context.LoginMemberContext;
import com.jyw.ticketsystem.common.resp.CommonResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.member.req.PassengerQueryReq;
import com.jyw.ticketsystem.member.resp.PassengerQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req){
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> list=passengerService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        passengerService.delete(id);
        return new CommonResp<>();
    }

}
