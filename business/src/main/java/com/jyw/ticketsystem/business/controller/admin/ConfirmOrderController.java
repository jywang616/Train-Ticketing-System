package com.jyw.ticketsystem.business.controller.admin;


import com.jyw.ticketsystem.common.resp.CommonResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.business.req.ConfirmOrderQueryReq;
import com.jyw.ticketsystem.business.req.ConfirmOrderSaveReq;
import com.jyw.ticketsystem.business.resp.ConfirmOrderQueryResp;
import com.jyw.ticketsystem.business.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/confirm-order")
public class ConfirmOrderController {
    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody ConfirmOrderSaveReq req){
        confirmOrderService.save(req);
        return new CommonResp<>();
    }
    @GetMapping ("/query-list")
    public CommonResp<PageResp<ConfirmOrderQueryResp>> queryList(@Valid ConfirmOrderQueryReq req){

        PageResp<ConfirmOrderQueryResp> list=confirmOrderService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        confirmOrderService.delete(id);
        return new CommonResp<>();
    }

}
