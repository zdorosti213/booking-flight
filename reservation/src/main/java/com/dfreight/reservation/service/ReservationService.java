package com.dfreight.reservation.service;

import com.dfreight.flightticket.TicketDto;

import java.util.List;

public interface ReservationService {
    List<TicketDto> getAllFlights(String source, String destination);

    List<TicketDto> filterBySourceAndDestination(List<TicketDto> ticketList, String source, String destination);
}
