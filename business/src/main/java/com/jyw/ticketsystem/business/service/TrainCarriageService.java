package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.business.domain.TrainCarriage;
import com.jyw.ticketsystem.business.domain.TrainCarriageExample;
import com.jyw.ticketsystem.business.mapper.TrainCarriageMapper;
import com.jyw.ticketsystem.business.req.TrainCarriageQueryReq;
import com.jyw.ticketsystem.business.req.TrainCarriageSaveReq;
import com.jyw.ticketsystem.business.resp.TrainCarriageQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainCarriageService {

    @Resource
    private TrainCarriageMapper trainCarriageMapper;
    public void save(TrainCarriageSaveReq req){
        DateTime now=DateTime.now();
        TrainCarriage trainCarriage=BeanUtil.copyProperties(req,TrainCarriage.class);
        if(ObjectUtil.isNull(trainCarriage.getId())) {
            trainCarriage.setId(SnowUtil.getSnowflakeNextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.insert(trainCarriage);
        }
        else{
            trainCarriage.setCreateTime(now);
            trainCarriageMapper.updateByPrimaryKey(trainCarriage);
        }
    }
    public PageResp<TrainCarriageQueryResp> queryList(TrainCarriageQueryReq req){
        TrainCarriageExample trainCarriageExample=new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("id desc");
        TrainCarriageExample.Criteria criteria=trainCarriageExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainCarriage> trainCarriageList=trainCarriageMapper.selectByExample((trainCarriageExample));

        PageInfo<TrainCarriage> pageInfo= new PageInfo<>(trainCarriageList);

        List<TrainCarriageQueryResp> list= BeanUtil.copyToList(trainCarriageList,TrainCarriageQueryResp.class);
        PageResp<TrainCarriageQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        trainCarriageMapper.deleteByPrimaryKey(id);
    }
}