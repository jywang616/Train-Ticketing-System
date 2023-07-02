package com.jyw.ticketsystem.ticket.mapper;

import com.jyw.ticketsystem.ticket.domain.TrainInfo;
import com.jyw.ticketsystem.ticket.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//增删改查
public interface CarriageMapper {
    //查返城市里面的对应站点
    //@Query(value="select station_name from city_station where city_station.city_name = ?",nativeQuery = true)
//    public List<String> getstation(@Param("city") String city_name);
    static final Logger LOG = LoggerFactory.getLogger(TicketService.class);
    public List<Integer> getSeat(String code,int start_NO,int end_NO,int seat);

    public default int[] getTicketLeft(TrainInfo info){
        int[] ticket=new int[]{100000,100000,100000,100000};
        for(int i=0;i<4;++i){
            List<Integer> seats=getSeat(info.getTraincode(), info.getStart_NO(), info.getEnd_NO(), i+1);

            if(seats.get(0) ==null){return new int[]{0,0,0,0};}
            //LOG.info(seats.toString());
            for(Integer seat:seats){
                if(seat<ticket[i])
                    ticket[i]=seat;
            }
        }

        return ticket;
    }


}
