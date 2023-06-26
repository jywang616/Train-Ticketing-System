package com.jyw.ticketsystem.business.controller.admin;


import com.jyw.ticketsystem.common.resp.CommonResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.business.req.TrainSeatQueryReq;
import com.jyw.ticketsystem.business.req.TrainSeatSaveReq;
import com.jyw.ticketsystem.business.resp.TrainSeatQueryResp;
import com.jyw.ticketsystem.business.service.TrainSeatService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-seat")
public class TrainSeatAdminController {
    @Resource
    private TrainSeatService trainSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainSeatSaveReq req){
        trainSeatService.save(req);
        return new CommonResp<>();
    }
    @GetMapping ("/query-list")
    public CommonResp<PageResp<TrainSeatQueryResp>> queryList(@Valid TrainSeatQueryReq req){

        PageResp<TrainSeatQueryResp> list=trainSeatService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        trainSeatService.delete(id);
        return new CommonResp<>();
    }

}
