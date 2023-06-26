package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.business.domain.TrainSeat;
import com.jyw.ticketsystem.business.domain.TrainSeatExample;
import com.jyw.ticketsystem.business.mapper.TrainSeatMapper;
import com.jyw.ticketsystem.business.req.TrainSeatQueryReq;
import com.jyw.ticketsystem.business.req.TrainSeatSaveReq;
import com.jyw.ticketsystem.business.resp.TrainSeatQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainSeatService {

    @Resource
    private TrainSeatMapper trainSeatMapper;
    public void save(TrainSeatSaveReq req){
        DateTime now=DateTime.now();
        TrainSeat trainSeat=BeanUtil.copyProperties(req,TrainSeat.class);
        if(ObjectUtil.isNull(trainSeat.getId())) {
            trainSeat.setId(SnowUtil.getSnowflakeNextId());
            trainSeat.setCreateTime(now);
            trainSeat.setUpdateTime(now);
            trainSeatMapper.insert(trainSeat);
        }
        else{
            trainSeat.setCreateTime(now);
            trainSeatMapper.updateByPrimaryKey(trainSeat);
        }
    }
    public PageResp<TrainSeatQueryResp> queryList(TrainSeatQueryReq req){
        TrainSeatExample trainSeatExample=new TrainSeatExample();
        trainSeatExample.setOrderByClause("id desc");
        TrainSeatExample.Criteria criteria=trainSeatExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainSeat> trainSeatList=trainSeatMapper.selectByExample((trainSeatExample));

        PageInfo<TrainSeat> pageInfo= new PageInfo<>(trainSeatList);

        List<TrainSeatQueryResp> list= BeanUtil.copyToList(trainSeatList,TrainSeatQueryResp.class);
        PageResp<TrainSeatQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        trainSeatMapper.deleteByPrimaryKey(id);
    }
}