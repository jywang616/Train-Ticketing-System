package com.jyw.ticketsystem.ticket.domain;

public class Ticket {
    private Long id;
    private String city_name;
    private String station;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ticket{");
        sb.append("id=").append(id);
        sb.append(", city_name='").append(city_name).append('\'');
        sb.append(", station='").append(station).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
