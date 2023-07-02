package com.jyw.ticketsystem.ticket.mapper;

import com.jyw.ticketsystem.ticket.domain.CityStation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//增删改查
public interface CityStationMapper {
    //查返城市里面的对应站点
    //@Query(value="select station_name from city_station where city_station.city_name = ?",nativeQuery = true)
    public List<String> getstation(String city);

    public List<CityStation> getmapper();

    public List<String> getCity();
    public String matchCity(String station);

    public Integer getCityNum();

    public default Map<String,String> getmap(){
        List<CityStation>map=getmapper();
        Map<String,String>station_city=new HashMap<String,String>();
        for(CityStation cs:map){
            station_city.put(cs.getStation_name(), cs.getCity_name());
        }
        return station_city;
    }


}
