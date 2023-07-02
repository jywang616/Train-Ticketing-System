package com.jyw.ticketsystem.ticket.req;

import com.jyw.ticketsystem.common.req.PageReq;

public class QueryReq extends PageReq {


    private String start_city;

    private String end_city;

    //@NotBlank(message = "出发日期不能为空")
    private String start_date;


    private String end_date;

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "NonStopQueryReq{" +
                "start_city='" + start_city + '\'' +
                ", end_city='" + end_city + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}'+ super.toString();
    }
}
