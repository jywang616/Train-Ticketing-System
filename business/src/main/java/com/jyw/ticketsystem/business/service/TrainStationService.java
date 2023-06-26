package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.business.domain.TrainStation;
import com.jyw.ticketsystem.business.domain.TrainStationExample;
import com.jyw.ticketsystem.business.mapper.TrainStationMapper;
import com.jyw.ticketsystem.business.req.TrainStationQueryReq;
import com.jyw.ticketsystem.business.req.TrainStationSaveReq;
import com.jyw.ticketsystem.business.resp.TrainStationQueryResp;
import com.jyw.ticketsystem.common.exception.BusinessException;
import com.jyw.ticketsystem.common.exception.BusinessExceptionEnum;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
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
            TrainStation trainStationDB = selectByUnique(req.getTrainCode(), req.getIndex());
            if (ObjectUtil.isNotEmpty(trainStationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR);
            }

            trainStationDB = selectByUnique(req.getTrainCode(), req.getName());
            if (ObjectUtil.isNotEmpty(trainStationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR);
            }
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
    private TrainStation selectByUnique(String trainCode, Integer index) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria()
                .andTrainCodeEqualTo(trainCode)
                .andIndexEqualTo(index);
        List<TrainStation> list = trainStationMapper.selectByExample(trainStationExample);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    private TrainStation selectByUnique(String trainCode, String name) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria()
                .andTrainCodeEqualTo(trainCode)
                .andNameEqualTo(name);
        List<TrainStation> list = trainStationMapper.selectByExample(trainStationExample);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }
    public PageResp<TrainStationQueryResp> queryList(TrainStationQueryReq req){
        TrainStationExample trainStationExample=new TrainStationExample();
        trainStationExample.setOrderByClause("train_code asc, `index` asc");
        TrainStationExample.Criteria criteria=trainStationExample.createCriteria();
        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
        criteria.andTrainCodeEqualTo(req.getTrainCode());
        }

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