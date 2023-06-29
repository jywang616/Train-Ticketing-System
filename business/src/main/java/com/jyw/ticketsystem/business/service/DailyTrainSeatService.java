package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.business.domain.DailyTrainSeat;
import com.jyw.ticketsystem.business.domain.DailyTrainSeatExample;
import com.jyw.ticketsystem.business.mapper.DailyTrainSeatMapper;
import com.jyw.ticketsystem.business.req.DailyTrainSeatQueryReq;
import com.jyw.ticketsystem.business.req.DailyTrainSeatSaveReq;
import com.jyw.ticketsystem.business.resp.DailyTrainSeatQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyTrainSeatService {

    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;
    public void save(DailyTrainSeatSaveReq req){
        DateTime now=DateTime.now();
        DailyTrainSeat dailyTrainSeat=BeanUtil.copyProperties(req,DailyTrainSeat.class);
        if(ObjectUtil.isNull(dailyTrainSeat.getId())) {
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }
        else{
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeatMapper.updateByPrimaryKey(dailyTrainSeat);
        }
    }
    public PageResp<DailyTrainSeatQueryResp> queryList(DailyTrainSeatQueryReq req){
        DailyTrainSeatExample dailyTrainSeatExample=new DailyTrainSeatExample();
        dailyTrainSeatExample.setOrderByClause("train_code asc, carriage_index asc, carriage_seat_index asc");
        DailyTrainSeatExample.Criteria criteria=dailyTrainSeatExample.createCriteria();
        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainSeat> dailyTrainSeatList=dailyTrainSeatMapper.selectByExample((dailyTrainSeatExample));

        PageInfo<DailyTrainSeat> pageInfo= new PageInfo<>(dailyTrainSeatList);

        List<DailyTrainSeatQueryResp> list= BeanUtil.copyToList(dailyTrainSeatList,DailyTrainSeatQueryResp.class);
        PageResp<DailyTrainSeatQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }
}