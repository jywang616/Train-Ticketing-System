package com.jyw.ticketsystem.ticket.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class NonStopQueryResp {

    @JsonSerialize(using= ToStringSerializer.class)
    private String traincode;
    private String start_station;
    private String end_station;
    private String start_time;
    private String end_time;
    private int start_NO;
    private int end_NO;
    private int duration;
    private int FirstClass;
    private int SecondClass;
    private int RW;
    private int YW;

    public String getTraincode() {
        return traincode;
    }

    public void setTraincode(String traincode) {
        this.traincode = traincode;
    }

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



    public int getStart_NO() {
        return start_NO;
    }

    public void setStart_NO(int start_NO) {
        this.start_NO = start_NO;
    }

    public int getEnd_NO() {
        return end_NO;
    }

    public void setEnd_NO(int end_NO) {
        this.end_NO = end_NO;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFirstClass() {
        return FirstClass;
    }

    public void setFirstClass(int firstClass) {
        FirstClass = firstClass;
    }

    public int getSecondClass() {
        return SecondClass;
    }

    public void setSecondClass(int secondClass) {
        SecondClass = secondClass;
    }

    public int getRW() {
        return RW;
    }

    public void setRW(int RW) {
        this.RW = RW;
    }

    public int getYW() {
        return YW;
    }

    public void setYW(int YW) {
        this.YW = YW;
    }
}

