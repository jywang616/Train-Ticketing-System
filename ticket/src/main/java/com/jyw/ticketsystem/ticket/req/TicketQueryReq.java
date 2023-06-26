package com.jyw.ticketsystem.ticket.req;

import com.jyw.ticketsystem.common.req.PageReq;

public class TicketQueryReq extends PageReq {
    private Long Id;
    public Long getId() {
        return Id;
    }

    public void setId(Long ticketId) {
        this.Id = ticketId;
    }
@Override
public String toString() {
return "PassengerQueryReq{" +
"} " + super.toString();
}
}
