package com.jyw.ticketsystem.ticket.domain;


import org.joda.time.DateTime;

public class Carriage {
    //整个火车每个车厢的座位余量情况

    private String traincode; //多了code
    private int station_NO;//从x-1到x站
    private int carriage_index;//X车厢 index
    private int seat_type;//车厢类型
    private int seat_count;//该车厢座位余量
    private int row_count;
    private int col_count;
    private DateTime create_time;
    private DateTime update_time;

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

    public int getCarriage_index() {
        return carriage_index;
    }

    public void setCarriage_index(int carriage_index) {
        this.carriage_index = carriage_index;
    }

    public int getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(int seat_type) {
        this.seat_type = seat_type;
    }

    public int getSeat_count() {
        return seat_count;
    }

    public void setSeat_count(int seat_count) {
        this.seat_count = seat_count;
    }

    public int getRow_count() {
        return row_count;
    }

    public void setRow_count(int row_count) {
        this.row_count = row_count;
    }

    public int getCol_count() {
        return col_count;
    }

    public void setCol_count(int col_count) {
        this.col_count = col_count;
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

    @Override
    public String toString() {
        return "carriage{" +
                "traincode=" + traincode +
                ", station_NO=" + station_NO +
                ", carriage_index=" + carriage_index +
                ", seat_type=" + seat_type +
                ", seat_count=" + seat_count +
                ", row_count=" + row_count +
                ", col_count=" + col_count +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
