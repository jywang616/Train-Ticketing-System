package com.jyw.ticketsystem.ticket.domain;

import org.joda.time.DateTime;



public class Station {

    private String traincode; //多了code
    private int station_NO; //id
    private String name;
    private String name_pinyin;
    private String start_time;//该站到达时间

    private String out_time;//该站发车时间
    private int duration;//空隙
    private DateTime create_time;
    private DateTime update_time;

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTraincode() {
        return traincode;
    }

    public void setTraincode(String traincode) {
        this.traincode = traincode;
    }

    public int getStation_NO() {
        return station_NO;
    }

    public void setStation_NO(int station_NO) {
        this.station_NO = station_NO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_pinyin() {
        return name_pinyin;
    }

    public void setName_pinyin(String name_pinyin) {
        this.name_pinyin = name_pinyin;
    }

    public DateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(DateTime create_time) {
        this.create_time = create_time;
    }

    public DateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(DateTime update_time) {
        this.update_time = update_time;
    }


}
