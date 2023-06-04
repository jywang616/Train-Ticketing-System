package com.jyw.ticketsystem.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.member.domain.Passenger;
import com.jyw.ticketsystem.member.mapper.PassengerMapper;
import com.jyw.ticketsystem.member.req.PassengerSaveReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;
    public void save(PassengerSaveReq req){
        DateTime now=DateTime.now();
        Passenger passenger=BeanUtil.copyProperties(req,Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }
}
