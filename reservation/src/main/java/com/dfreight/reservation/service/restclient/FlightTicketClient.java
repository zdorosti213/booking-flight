package com.dfreight.reservation.service.restclient;

import com.dfreight.flightticket.TicketDto;

import java.util.List;

public interface FlightTicketClient {
    List<TicketDto> getTicketListJson();
}