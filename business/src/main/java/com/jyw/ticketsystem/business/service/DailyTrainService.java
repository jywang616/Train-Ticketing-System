package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import com.jyw.ticketsystem.business.domain.DailyTrain;
import com.jyw.ticketsystem.business.domain.DailyTrainExample;
import com.jyw.ticketsystem.business.mapper.DailyTrainMapper;
import com.jyw.ticketsystem.business.req.DailyTrainQueryReq;
import com.jyw.ticketsystem.business.req.DailyTrainSaveReq;
import com.jyw.ticketsystem.business.resp.DailyTrainQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyTrainService {

    @Resource
    private DailyTrainMapper dailyTrainMapper;
    public void save(DailyTrainSaveReq req){
        DateTime now=DateTime.now();
        DailyTrain dailyTrain=BeanUtil.copyProperties(req,DailyTrain.class);
        if(ObjectUtil.isNull(dailyTrain.getId())) {
            dailyTrain.setId(SnowUtil.getSnowflakeNextId());
            dailyTrain.setCreateTime(now);
            dailyTrain.setUpdateTime(now);
            dailyTrainMapper.insert(dailyTrain);
        }
        else{
            dailyTrain.setCreateTime(now);
            dailyTrainMapper.updateByPrimaryKey(dailyTrain);
        }
    }
    public PageResp<DailyTrainQueryResp> queryList(DailyTrainQueryReq req){
        DailyTrainExample dailyTrainExample=new DailyTrainExample();
        dailyTrainExample.setOrderByClause("date desc, code asc");
        DailyTrainExample.Criteria criteria=dailyTrainExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getDate())) {
            criteria.andDateEqualTo(req.getDate());
            }
        if (ObjectUtil.isNotEmpty(req.getCode())) {
            criteria.andCodeEqualTo(req.getCode());
            }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrain> dailyTrainList=dailyTrainMapper.selectByExample((dailyTrainExample));

        PageInfo<DailyTrain> pageInfo= new PageInfo<>(dailyTrainList);

        List<DailyTrainQueryResp> list= BeanUtil.copyToList(dailyTrainList,DailyTrainQueryResp.class);
        PageResp<DailyTrainQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        dailyTrainMapper.deleteByPrimaryKey(id);
    }
}