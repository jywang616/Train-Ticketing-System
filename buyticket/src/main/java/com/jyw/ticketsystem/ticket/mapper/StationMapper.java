package com.jyw.ticketsystem.ticket.mapper;

import com.jyw.ticketsystem.ticket.domain.Station;
import com.jyw.ticketsystem.ticket.domain.TrainInfo;
import com.jyw.ticketsystem.ticket.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//增删改查
public interface StationMapper {
    static final Logger LOG = LoggerFactory.getLogger(TicketService.class);
    //在时间区间内的列车信息
    public TrainInfo findtraininfo(String name1, String name2, String sd, String en);
    public List<Station> getAllstation(String traincode);
    public default List<TrainInfo> gettraininfo(List<String> start_stations, List<String> end_stations, String start_date, String end_date){
        //不会有重复traincode
        List<TrainInfo> infos = new ArrayList<>();
        Map<String,Integer>map=new HashMap<String,Integer>();
        for(String start_station:start_stations){
            for(String end_station:end_stations){
//                LOG.info(start_station);
//                LOG.info(end_station);
//                LOG.info(start_date);
//                LOG.info(end_date);
                TrainInfo info=findtraininfo(start_station,end_station,start_date, end_date);
//                LOG.info(String.valueOf(info));
                //TrainInfo info=new TrainInfo(123,"00","11", "2023-06-21","2023-06-22",1,2);
                if(info==null){}
                else{
                    if(map.containsKey(info.getTraincode())){}
                    else{
                        map.put(info.getTraincode(),1);
                        infos.add(info);
                    }
                }
            }
        }
//        LOG.info(String.valueOf(infos));
        return infos;
    }
    //比上一班晚20min后最早出发的列车集
    public TrainInfo findnexttrain(String name1, String name2, String sd, String en);
    public default List<TrainInfo> getnexttrain(String start_station,List<String>end_stations,String start_time,String end_date){
        //获得城市之间的列车，看顺序是否合适，如时间少于2h，则将最近的列车加入其中
        List<TrainInfo> next_infos=new ArrayList<>();
        Map<String,Integer> map=new HashMap<String,Integer>();
        for(String end_station:end_stations){
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date date = null;
            try {
                date = df.parse(start_time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date next_time=new Date(date.getTime()+1200000);//加20min
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            LOG.info("startsta"+start_station);
            LOG.info("xx"+end_date);
            //找到最近的一辆列车
            TrainInfo info=findnexttrain(start_station,end_station,sdf.format(next_time),end_date);
            LOG.info("train"+info);
            if(info!=null) {
                if (map.containsKey(info.getTraincode())) {
                } else {
                    map.put(info.getTraincode(), 1);
                    next_infos.add(info);
                }
            }
        }
        return  next_infos;
    }
    public String getStationName(String traincode,int no);

    public List<Station> getTrainStationInfo(String traincode);

}
