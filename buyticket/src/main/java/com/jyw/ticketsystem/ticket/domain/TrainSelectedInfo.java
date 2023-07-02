package com.jyw.ticketsystem.ticket.domain;

import java.util.Date;

//TrainSelectedInfo(start_city,start_date,end_city,end_date)
public class TrainSelectedInfo {
    private String start_city;
    private String end_city;
    private Date start_date;
    private Date end_date;

    public String getStart_city() {
        return start_city;
    }

    public void setStart_city(String start_city) {
        this.start_city = start_city;
    }

    public String getEnd_city() {
        return end_city;
    }

    public void setEnd_city(String end_city) {
        this.end_city = end_city;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public TrainSelectedInfo(String start_city, String end_city, Date start_date, Date end_date) {
        this.start_city = start_city;
        this.end_city = end_city;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
