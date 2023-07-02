package com.jyw.ticketsystem.ticket.resp;

import com.jyw.ticketsystem.ticket.domain.TrainInfo;

import java.util.List;

public class StopQueryResp {

    private int id;
    private String start_station;
    private String end_station;
    private String start_time;
    private String end_time;
    private List<TrainInfo> infos;
    private int type;//dijkstra:0 暴力：1


    public String getStart_station() {
        return start_station;
    }

    public void setStart_station(String start_station) {
        this.start_station = start_station;
    }

    public String getEnd_station() {
        return end_station;
    }

    public void setEnd_station(String end_station) {
        this.end_station = end_station;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public List<TrainInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<TrainInfo> infos) {
        this.infos = infos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

