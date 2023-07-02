package com.jyw.ticketsystem.ticket.domain;

public class Graph {
    private String start_city;
    private String end_city;
    private int duration;
    private String start_date;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Graph(String start_city, String end_city, int duration, String start_date) {
        this.start_city = start_city;
        this.end_city = end_city;
        this.duration = duration;
        this.start_date = start_date;
    }

    public Graph(String start_city, String end_city, int duration) {
        this.start_city = start_city;
        this.end_city = end_city;
        this.duration = duration;
    }
}
