package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.business.domain.Station;
import com.jyw.ticketsystem.business.domain.StationExample;
import com.jyw.ticketsystem.business.mapper.StationMapper;
import com.jyw.ticketsystem.business.req.StationQueryReq;
import com.jyw.ticketsystem.business.req.StationSaveReq;
import com.jyw.ticketsystem.business.resp.StationQueryResp;
import com.jyw.ticketsystem.common.exception.BusinessException;
import com.jyw.ticketsystem.common.exception.BusinessExceptionEnum;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Resource
    private StationMapper stationMapper;
    public void save(StationSaveReq req){
        DateTime now=DateTime.now();
        Station station=BeanUtil.copyProperties(req,Station.class);
        if(ObjectUtil.isNull(station.getId())) {
            //保存之前，校验唯一键是否存在
            StationExample stationExample = new StationExample();
            stationExample.createCriteria().andNameEqualTo(req.getName());
            List<Station> list = stationMapper.selectByExample(stationExample);
            if (CollUtil.isNotEmpty(list)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_STATION_NAME_UNIQUE_ERROR);
                }
            station.setId(SnowUtil.getSnowflakeNextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            stationMapper.insert(station);
        }
        else{
            station.setCreateTime(now);
            stationMapper.updateByPrimaryKey(station);
        }
    }
    public PageResp<StationQueryResp> queryList(StationQueryReq req){
        StationExample stationExample=new StationExample();
        stationExample.setOrderByClause("id desc");
        StationExample.Criteria criteria=stationExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Station> stationList=stationMapper.selectByExample((stationExample));

        PageInfo<Station> pageInfo= new PageInfo<>(stationList);

        List<StationQueryResp> list= BeanUtil.copyToList(stationList,StationQueryResp.class);
        PageResp<StationQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        stationMapper.deleteByPrimaryKey(id);
    }

    public List<StationQueryResp> queryAll() {
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("name_pinyin asc");
        List<Station> stationList = stationMapper.selectByExample(stationExample);
        return BeanUtil.copyToList(stationList, StationQueryResp.class);
    }
}