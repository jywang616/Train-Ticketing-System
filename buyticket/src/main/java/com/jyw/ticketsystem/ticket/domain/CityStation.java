package com.jyw.ticketsystem.ticket.domain;


//@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class CityStation {


    private String city_name;

    private String station_name;


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }
}
