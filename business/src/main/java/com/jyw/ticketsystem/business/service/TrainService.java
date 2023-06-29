package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.business.domain.Train;
import com.jyw.ticketsystem.business.domain.TrainExample;
import com.jyw.ticketsystem.business.mapper.TrainMapper;
import com.jyw.ticketsystem.business.req.TrainQueryReq;
import com.jyw.ticketsystem.business.req.TrainSaveReq;
import com.jyw.ticketsystem.business.resp.TrainQueryResp;
import com.jyw.ticketsystem.common.exception.BusinessException;
import com.jyw.ticketsystem.common.exception.BusinessExceptionEnum;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    @Resource
    private TrainMapper trainMapper;
    public void save(TrainSaveReq req){
        DateTime now=DateTime.now();
        Train train=BeanUtil.copyProperties(req,Train.class);
        if(ObjectUtil.isNull(train.getId())) {
            // 保存之前，先校验唯一键是否存在
            Train trainDB = selectByUnique(req.getCode());
            if (ObjectUtil.isNotEmpty(trainDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CODE_UNIQUE_ERROR);
            }

            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        }
        else{
            train.setCreateTime(now);
            trainMapper.updateByPrimaryKey(train);
        }
    }
    private Train selectByUnique(String code) {
        TrainExample trainExample = new TrainExample();
        trainExample.createCriteria()
                .andCodeEqualTo(code);
        List<Train> list = trainMapper.selectByExample(trainExample);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }
    public PageResp<TrainQueryResp> queryList(TrainQueryReq req){
        TrainExample trainExample=new TrainExample();
        trainExample.setOrderByClause("code asc");
        TrainExample.Criteria criteria=trainExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Train> trainList=trainMapper.selectByExample((trainExample));

        PageInfo<Train> pageInfo= new PageInfo<>(trainList);

        List<TrainQueryResp> list= BeanUtil.copyToList(trainList,TrainQueryResp.class);
        PageResp<TrainQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public List<TrainQueryResp> queryAll() {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("code asc");
        List<Train> trainList = trainMapper.selectByExample(trainExample);
        return BeanUtil.copyToList(trainList, TrainQueryResp.class);
    }
    public void delete(Long id){
        trainMapper.deleteByPrimaryKey(id);
    }
}