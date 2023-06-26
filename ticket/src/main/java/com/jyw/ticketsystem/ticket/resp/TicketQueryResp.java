package com.jyw.ticketsystem.ticket.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class TicketQueryResp {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String city_name;
    private String station;


}

