package com.jyw.ticketsystem.ticket.mapper;

import com.jyw.ticketsystem.ticket.domain.Ticket;

import java.util.List;

public interface TicketMapper {
    List<Ticket> selectAll();
}