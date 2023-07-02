package com.jyw.ticketsystem.ticket.mapper;

import com.jyw.ticketsystem.ticket.domain.Train;

import java.util.List;

//增删改查
public interface TrainMapper {

    public List<Train> getdailytrain(String date);
    // List<Train> daily_trains=TrainMapper.getdailytrain(date);
}
