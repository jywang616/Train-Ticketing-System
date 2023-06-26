package com.jyw.ticketsystem.business.controller.admin;


import com.jyw.ticketsystem.common.resp.CommonResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.business.req.TrainCarriageQueryReq;
import com.jyw.ticketsystem.business.req.TrainCarriageSaveReq;
import com.jyw.ticketsystem.business.resp.TrainCarriageQueryResp;
import com.jyw.ticketsystem.business.service.TrainCarriageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-carriage")
public class TrainCarriageAdminController {
    @Resource
    private TrainCarriageService trainCarriageService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainCarriageSaveReq req){
        trainCarriageService.save(req);
        return new CommonResp<>();
    }
    @GetMapping ("/query-list")
    public CommonResp<PageResp<TrainCarriageQueryResp>> queryList(@Valid TrainCarriageQueryReq req){

        PageResp<TrainCarriageQueryResp> list=trainCarriageService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        trainCarriageService.delete(id);
        return new CommonResp<>();
    }

}
