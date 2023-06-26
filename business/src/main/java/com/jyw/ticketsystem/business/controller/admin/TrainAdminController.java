package com.jyw.ticketsystem.business.controller.admin;


import com.jyw.ticketsystem.business.req.TrainQueryReq;
import com.jyw.ticketsystem.business.req.TrainSaveReq;
import com.jyw.ticketsystem.business.resp.TrainQueryResp;
import com.jyw.ticketsystem.business.service.TrainSeatService;
import com.jyw.ticketsystem.business.service.TrainService;
import com.jyw.ticketsystem.common.resp.CommonResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainAdminController {
    @Resource
    private TrainService trainService;
    @Resource
    private TrainSeatService trainSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainSaveReq req){
        trainService.save(req);
        return new CommonResp<>();
    }
    @GetMapping ("/query-list")
    public CommonResp<PageResp<TrainQueryResp>> queryList(@Valid TrainQueryReq req){

        PageResp<TrainQueryResp> list=trainService.queryList(req);
        return new CommonResp<>(list);
    }
    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryList() {
        List<TrainQueryResp> list = trainService.queryAll();
        return new CommonResp<>(list);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        trainService.delete(id);
        return new CommonResp<>();
    }
    @GetMapping("/gen-seat/{trainCode}")
    public CommonResp<Object> genSeat(@PathVariable String trainCode) {
        trainSeatService.genTrainSeat(trainCode);
        return new CommonResp<>();
    }

}
