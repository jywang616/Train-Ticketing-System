package com.jyw.ticketsystem.ticket.controller;

import com.jyw.ticketsystem.common.resp.CommonResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.ticket.domain.Station;
import com.jyw.ticketsystem.ticket.req.QueryReq;
import com.jyw.ticketsystem.ticket.req.TicketSaveReq;
import com.jyw.ticketsystem.ticket.resp.NonStopQueryResp;
import com.jyw.ticketsystem.ticket.resp.StopQueryResp;
import com.jyw.ticketsystem.ticket.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class TicketController {
    @Resource
    private TicketService ticketService;

    @GetMapping("/NonStopQuery")
    public CommonResp<PageResp<NonStopQueryResp>> NonStopQuery(@Valid QueryReq req) throws ParseException {
        PageResp<NonStopQueryResp> list= ticketService.NonStopTrainList(req);
        return new CommonResp<>(list);
    }
    @GetMapping("/StopQuery")
    public CommonResp<PageResp<StopQueryResp>> StopQuery(@Valid QueryReq req) throws ParseException {
        PageResp<StopQueryResp> list=ticketService.StopTrainList(req);
        return new CommonResp<>(list);
    }

    @GetMapping("/StopQuery_Bru")
    public CommonResp<PageResp<StopQueryResp>> StopQuery1(@Valid QueryReq req) throws ParseException {
        PageResp<StopQueryResp> list=ticketService.BruteForceTrainList(req);
        return new CommonResp<>(list);
    }

    @GetMapping("/StopQuery_Dis")
    public CommonResp<PageResp<StopQueryResp>> StopQuery2(@Valid QueryReq req) throws ParseException {
        PageResp<StopQueryResp> list=ticketService.DijkstraTrainList(req);
        return new CommonResp<>(list);
    }

    @GetMapping("/getCity")
    public CommonResp<List<String>>count(){
        List<String> cities=ticketService.getCity();
        return new CommonResp<>(cities);
    }

    @GetMapping("getTrainStationInfo")
    public CommonResp<List<Station>>getTrainStation(@RequestParam("traincode") String traincode){
        return new CommonResp<>(ticketService.getTrainStationInfo(traincode));
    }

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TicketSaveReq req){
        ticketService.save(req);
        return new CommonResp<>();
    }

}
