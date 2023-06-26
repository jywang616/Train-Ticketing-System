package com.jyw.ticketsystem.ticket.controller;

import com.jyw.ticketsystem.common.resp.CommonResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.ticket.Service.TicketService;
import com.jyw.ticketsystem.ticket.req.TicketQueryReq;
import com.jyw.ticketsystem.ticket.resp.TicketQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Resource
    private TicketService ticketService;


    @GetMapping ("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq req){
        PageResp<TicketQueryResp> list=ticketService.queryList(req);
        return new CommonResp<>(list);
    }
    

}
