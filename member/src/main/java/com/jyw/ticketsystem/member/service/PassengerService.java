package com.jyw.ticketsystem.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.common.context.LoginMemberContext;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.member.domain.Passenger;
import com.jyw.ticketsystem.member.domain.PassengerExample;
import com.jyw.ticketsystem.member.mapper.PassengerMapper;
import com.jyw.ticketsystem.member.req.PassengerQueryReq;
import com.jyw.ticketsystem.member.req.PassengerSaveReq;
import com.jyw.ticketsystem.member.resp.PassengerQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;
    public void save(PassengerSaveReq req){
        DateTime now=DateTime.now();
        Passenger passenger=BeanUtil.copyProperties(req,Passenger.class);
        if(ObjectUtil.isNull(passenger.getId())) {
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        }
        else{
            passenger.setCreateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }
    }
    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req){
        PassengerExample passengerExample=new PassengerExample();
        passengerExample.setOrderByClause("id desc");
        PassengerExample.Criteria criteria=passengerExample.createCriteria();
        if(ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengerList=passengerMapper.selectByExample((passengerExample));

        PageInfo<Passenger> pageInfo= new PageInfo<>(passengerList);

        List<PassengerQueryResp> list= BeanUtil.copyToList(passengerList,PassengerQueryResp.class);
        PageResp<PassengerQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        passengerMapper.deleteByPrimaryKey(id);
    }
}
