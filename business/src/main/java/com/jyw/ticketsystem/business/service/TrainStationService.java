package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.business.domain.TrainStation;
import com.jyw.ticketsystem.business.domain.TrainStationExample;
import com.jyw.ticketsystem.business.mapper.TrainStationMapper;
import com.jyw.ticketsystem.business.req.TrainStationQueryReq;
import com.jyw.ticketsystem.business.req.TrainStationSaveReq;
import com.jyw.ticketsystem.business.resp.TrainStationQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainStationService {

    @Resource
    private TrainStationMapper trainStationMapper;
    public void save(TrainStationSaveReq req){
        DateTime now=DateTime.now();
        TrainStation trainStation=BeanUtil.copyProperties(req,TrainStation.class);
        if(ObjectUtil.isNull(trainStation.getId())) {
            trainStation.setId(SnowUtil.getSnowflakeNextId());
            trainStation.setCreateTime(now);
            trainStation.setUpdateTime(now);
            trainStationMapper.insert(trainStation);
        }
        else{
            trainStation.setCreateTime(now);
            trainStationMapper.updateByPrimaryKey(trainStation);
        }
    }
    public PageResp<TrainStationQueryResp> queryList(TrainStationQueryReq req){
        TrainStationExample trainStationExample=new TrainStationExample();
        trainStationExample.setOrderByClause("id desc");
        TrainStationExample.Criteria criteria=trainStationExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainStation> trainStationList=trainStationMapper.selectByExample((trainStationExample));

        PageInfo<TrainStation> pageInfo= new PageInfo<>(trainStationList);

        List<TrainStationQueryResp> list= BeanUtil.copyToList(trainStationList,TrainStationQueryResp.class);
        PageResp<TrainStationQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        trainStationMapper.deleteByPrimaryKey(id);
    }
}