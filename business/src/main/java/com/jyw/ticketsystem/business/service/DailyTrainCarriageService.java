package com.jyw.ticketsystem.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.ticketsystem.business.domain.DailyTrainCarriage;
import com.jyw.ticketsystem.business.domain.DailyTrainCarriageExample;
import com.jyw.ticketsystem.business.enums.SeatColEnum;
import com.jyw.ticketsystem.business.mapper.DailyTrainCarriageMapper;
import com.jyw.ticketsystem.business.req.DailyTrainCarriageQueryReq;
import com.jyw.ticketsystem.business.req.DailyTrainCarriageSaveReq;
import com.jyw.ticketsystem.business.resp.DailyTrainCarriageQueryResp;
import com.jyw.ticketsystem.common.resp.PageResp;
import com.jyw.ticketsystem.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyTrainCarriageService {

    @Resource
    private DailyTrainCarriageMapper dailyTrainCarriageMapper;
    public void save(DailyTrainCarriageSaveReq req){
        DateTime now=DateTime.now();
        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(req.getSeatType());
        req.setColCount(seatColEnums.size());
        req.setSeatCount(req.getColCount() * req.getRowCount());

        DailyTrainCarriage dailyTrainCarriage=BeanUtil.copyProperties(req,DailyTrainCarriage.class);
        if(ObjectUtil.isNull(dailyTrainCarriage.getId())) {
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        }
        else{
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriageMapper.updateByPrimaryKey(dailyTrainCarriage);
        }
    }
    public PageResp<DailyTrainCarriageQueryResp> queryList(DailyTrainCarriageQueryReq req){
        DailyTrainCarriageExample dailyTrainCarriageExample=new DailyTrainCarriageExample();
        dailyTrainCarriageExample.setOrderByClause("date desc, train_code asc, `index` asc");
        DailyTrainCarriageExample.Criteria criteria=dailyTrainCarriageExample.createCriteria();

        if (ObjUtil.isNotNull(req.getDate())) {
            criteria.andDateEqualTo(req.getDate());
            }
        if (ObjUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
            }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainCarriage> dailyTrainCarriageList=dailyTrainCarriageMapper.selectByExample((dailyTrainCarriageExample));

        PageInfo<DailyTrainCarriage> pageInfo= new PageInfo<>(dailyTrainCarriageList);

        List<DailyTrainCarriageQueryResp> list= BeanUtil.copyToList(dailyTrainCarriageList,DailyTrainCarriageQueryResp.class);
        PageResp<DailyTrainCarriageQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id){
        dailyTrainCarriageMapper.deleteByPrimaryKey(id);
    }
}