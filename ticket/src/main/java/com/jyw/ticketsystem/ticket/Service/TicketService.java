package com.jyw.ticketsystem.ticket.Service;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.ticket.domain.Ticket;
import com.jyw.ticketsystem.ticket.mapper.TicketMapper;
import com.jyw.ticketsystem.ticket.req.TicketQueryReq;
import com.jyw.ticketsystem.ticket.resp.TicketQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Resource
    private TicketMapper ticketMapper;
    
    public PageResp<TicketQueryResp> queryList(TicketQueryReq req){
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ticket> ticketList=ticketMapper.selectAll();
        System.out.println(ticketList);
        PageInfo<Ticket> pageInfo= new PageInfo<>(ticketList);

        List<TicketQueryResp> list= BeanUtil.copyToList(ticketList,TicketQueryResp.class);
        PageResp<TicketQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }


}