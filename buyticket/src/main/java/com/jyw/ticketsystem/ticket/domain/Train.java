package com.jyw.ticketsystem.ticket.domain;


import org.joda.time.DateTime;

public class Train {
    //无ID

    private String traincode; //code
    private String start;
    private String start_pinyin;
    private String end;
    private String end_pinyin;

    private String start_time;
    private String end_time;

    private DateTime create_time;
    private DateTime update_time;

    public String getTraincode() {
        return traincode;
    }

    public void setTraincode(String traincode) {
        this.traincode = traincode;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart_pinyin() {
        return start_pinyin;
    }

    public void setStart_pinyin(String start_pinyin) {
        this.start_pinyin = start_pinyin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd_pinyin() {
        return end_pinyin;
    }

    public void setEnd_pinyin(String end_pinyin) {
        this.end_pinyin = end_pinyin;
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
