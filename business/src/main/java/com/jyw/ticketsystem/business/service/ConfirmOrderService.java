package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.business.domain.ConfirmOrder;
import com.jyw.ticketsystem.business.domain.ConfirmOrderExample;
import com.jyw.ticketsystem.business.mapper.ConfirmOrderMapper;
import com.jyw.ticketsystem.business.req.ConfirmOrderQueryReq;
import com.jyw.ticketsystem.business.req.ConfirmOrderSaveReq;
import com.jyw.ticketsystem.business.resp.ConfirmOrderQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfirmOrderService {

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    public void save(ConfirmOrderSaveReq req){
        DateTime now=DateTime.now();
        ConfirmOrder confirmOrder=BeanUtil.copyProperties(req,ConfirmOrder.class);
        if(ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.insert(confirmOrder);
        }
        else{
            confirmOrder.setCreateTime(now);
            confirmOrderMapper.updateByPrimaryKey(confirmOrder);
        }
    }
    public PageResp<ConfirmOrderQueryResp> queryList(ConfirmOrderQueryReq req){
        ConfirmOrderExample confirmOrderExample=new ConfirmOrderExample();
        confirmOrderExample.setOrderByClause("id desc");
        ConfirmOrderExample.Criteria criteria=confirmOrderExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<ConfirmOrder> confirmOrderList=confirmOrderMapper.selectByExample((confirmOrderExample));

        PageInfo<ConfirmOrder> pageInfo= new PageInfo<>(confirmOrderList);

        List<ConfirmOrderQueryResp> list= BeanUtil.copyToList(confirmOrderList,ConfirmOrderQueryResp.class);
        PageResp<ConfirmOrderQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        confirmOrderMapper.deleteByPrimaryKey(id);
    }
}