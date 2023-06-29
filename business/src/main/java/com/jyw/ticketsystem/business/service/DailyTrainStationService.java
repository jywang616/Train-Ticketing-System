package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.business.domain.DailyTrainStation;
import com.jyw.ticketsystem.business.domain.DailyTrainStationExample;
import com.jyw.ticketsystem.business.mapper.DailyTrainStationMapper;
import com.jyw.ticketsystem.business.req.DailyTrainStationQueryReq;
import com.jyw.ticketsystem.business.req.DailyTrainStationSaveReq;
import com.jyw.ticketsystem.business.resp.DailyTrainStationQueryResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyTrainStationService {

    @Resource
    private DailyTrainStationMapper dailyTrainStationMapper;
    public void save(DailyTrainStationSaveReq req){
        DateTime now=DateTime.now();
        DailyTrainStation dailyTrainStation=BeanUtil.copyProperties(req,DailyTrainStation.class);
        if(ObjectUtil.isNull(dailyTrainStation.getId())) {
            dailyTrainStation.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainStation.setCreateTime(now);
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.insert(dailyTrainStation);
        }
        else{
            dailyTrainStation.setCreateTime(now);
            dailyTrainStationMapper.updateByPrimaryKey(dailyTrainStation);
        }
    }
    public PageResp<DailyTrainStationQueryResp> queryList(DailyTrainStationQueryReq req){
        DailyTrainStationExample dailyTrainStationExample=new DailyTrainStationExample();
        dailyTrainStationExample.setOrderByClause("date desc, train_code asc, `index` asc");
        DailyTrainStationExample.Criteria criteria=dailyTrainStationExample.createCriteria();

        if (ObjUtil.isNotNull(req.getDate())) {
            criteria.andDateEqualTo(req.getDate());
            }
        if (ObjUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
            }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainStation> dailyTrainStationList=dailyTrainStationMapper.selectByExample((dailyTrainStationExample));

        PageInfo<DailyTrainStation> pageInfo= new PageInfo<>(dailyTrainStationList);

        List<DailyTrainStationQueryResp> list= BeanUtil.copyToList(dailyTrainStationList,DailyTrainStationQueryResp.class);
        PageResp<DailyTrainStationQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        dailyTrainStationMapper.deleteByPrimaryKey(id);
    }
}